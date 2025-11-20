package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lockersystem_backend.Config.CookieUtils;
import com.lockersystem_backend.Config.JwtService;
import com.lockersystem_backend.Model.AuthResponse;
import com.lockersystem_backend.Model.AuthenticationRequest;
import com.lockersystem_backend.Model.RegisterRequest;
import com.lockersystem_backend.Service.Interfaces.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    // Configurables por entorno
    @Value("${app.cookies.secure:true}")
    private boolean cookieSecure;

    @Value("${app.cookies.domain:}")
    private String cookieDomain;

    @Value("${app.cookies.samesite:None}")
    private String cookieSameSite;

    @Value("${security.jwt.access-exp-seconds:900}")
    private long accessExp;

    @Value("${security.jwt.refresh-exp-seconds:604800}")
    private long refreshExp;

    public AuthController(AuthenticationManager authManager, JwtService jwtService,
            UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

   @PostMapping("/login")
public ResponseEntity<AuthResponse> login(@RequestBody AuthenticationRequest req) {
    // 1. Autenticación normal
    Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));

    UserDetails user = (UserDetails) auth.getPrincipal();
    
    // 2. Generar JWT
    String jwt = jwtService.generateAccessToken(user);

    boolean isCliente = user.getAuthorities().stream()
            .anyMatch(a -> "ROLE_CLIENTE".equals(a.getAuthority()));

    // 3. Crear cookie EXACTO como ya usabas
    ResponseCookie cookie = isCliente
            ? CookieUtils.accessCookie(jwt, cookieSecure, emptyToNull(cookieDomain), cookieSameSite, accessExp)
            : CookieUtils.accessCookie(jwt, cookieSecure, emptyToNull(cookieDomain), cookieSameSite, 0);

    // 4. *** DEVOLVER EL TOKEN EN EL BODY PARA SWAGGER ***
    AuthResponse responseBody = new AuthResponse(jwt);

    // 5. Enviar cookie + token en body
    return ResponseEntity.ok()
            .header("Set-Cookie", cookie.toString())
            .body(responseBody);
}


    public record MeResponse(Long id, String userName, String apellido, String email, List<String> roles) {
    }

    @GetMapping("/me")
    public MeResponse me(Authentication auth) {
        Object principal = auth.getPrincipal();

        String userName = (principal instanceof UserDetails ud)
                ? ud.getUsername()
                : principal.toString();

        Long id = tryInvoke(principal, "getId", Long.class); // si tu User tiene getId()
        String apellido = tryInvoke(principal, "getApellido", String.class); // si tu User tiene getNickname()

        String email = tryInvoke(principal, "getEmail", String.class);

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // p.ej. "ROLE_ADMIN"
                .toList();

        return new MeResponse(id, userName, apellido, email, roles);
    }

    @SuppressWarnings("unchecked")
    private static <T> T tryInvoke(Object obj, String method, Class<T> type) {
        try {
            return (T) obj.getClass().getMethod(method).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    // Refresca el token leyendo la cookie ACCESS_TOKEN y reemitiendo otra
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken) {
        if (refreshToken == null)
            return ResponseEntity.status(401).body(Map.of("message", "no refresh"));
        if (!"refresh".equals(jwtService.getType(refreshToken)))
            return ResponseEntity.status(401).body(Map.of("message", "bad type"));
        if (jwtService.isExpired(refreshToken))
            return ResponseEntity.status(401).body(Map.of("message", "expired"));

        String username = jwtService.getUsername(refreshToken);
        var user = userDetailsService.loadUserByUsername(username);

        // Solo permitir si sigue siendo CLIENTE
        boolean isCliente = user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_CLIENTE".equals(a.getAuthority()));
        if (!isCliente)
            return ResponseEntity.status(403).body(Map.of("message", "forbidden"));

        String newAccess = jwtService.generateAccessToken(user);
        ResponseCookie newAccessCookie = CookieUtils.accessCookie(newAccess, cookieSecure, cookieDomain,
                cookieSameSite, accessExp);
        return ResponseEntity.ok().header("Set-Cookie", newAccessCookie.toString())
                .body(Map.of("message", "refreshed"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        var delAccess = CookieUtils.delete("ACCESS_TOKEN", cookieSecure, cookieDomain, cookieSameSite);
        var delRefresh = CookieUtils.delete("REFRESH_TOKEN", cookieSecure, cookieDomain, cookieSameSite);
        return ResponseEntity.ok()
                .header("Set-Cookie", delAccess.toString())
                .header("Set-Cookie", delRefresh.toString())
                .body(Map.of("message", "logged out"));
    }

    // Helpers
    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    @PostMapping("/registerEstudiante")
    public ResponseEntity<AuthResponse> registerEstudiante(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok().body(userService.registerEstudiante(request));
    }

}
