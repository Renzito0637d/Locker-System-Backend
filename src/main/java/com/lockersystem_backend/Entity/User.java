package com.lockersystem_backend.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lockersystem_backend.Entity.Enum.RoleName;
import com.lockersystem_backend.Model.RegisterRequest;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String userName;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String apellido;

    @Email
    @Column(nullable = false, length = 160, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @ElementCollection(targetClass = RoleName.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint(name = "uk_user_role", columnNames = {
            "user_id", "role" }))
    @Column(name = "role", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private Set<RoleName> roles = new HashSet<>();

    @Column(nullable = false)
    private Boolean active = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .filter(Objects::nonNull)
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect(Collectors.toList());
    }

    // Este método devuelve el nombre de usuario del usuario.
    @Override
    public String getUsername() {
        return this.userName;
    }

    // Este método devuelve la contraseña del usuario.
    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    // Los siguientes métodos devuelven valores predeterminados para las propiedades
    // de seguridad del usuario.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Este método indica si la cuenta del usuario está bloqueada o no.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Este método indica si las credenciales del usuario han expirado o no.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Este método indica si la cuenta del usuario está habilitada o no.
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.active);
    }

    public User() {
    }

    public User(Long id, String userName, String nombre, String apellido, String email, String passwordHash) {
        this.id = id;
        this.userName = userName;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static class Builder {
        private Long id;
        private String userName;
        private String nombre;
        private String apellido;
        private String email;
        private String passwordHash;
        private Set<RoleName> roles = new HashSet<>();
        private Boolean active = true;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder apellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder roles(Set<RoleName> roles) {
            this.roles = roles;
            return this;
        }

        public Builder addRole(RoleName role) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            }
            this.roles.add(role);
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        // Método final para construir el objeto User
        public User build() {
            User user = new User();
            user.setId(this.id);
            user.setUserName(this.userName);
            user.setNombre(this.nombre);
            user.setApellido(this.apellido);
            user.setEmail(this.email);
            user.setPasswordHash(this.passwordHash);
            user.setActive(this.active);
            user.setRoles(this.roles);
            return user;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static User from(RegisterRequest r, String encodedPassword, RoleName role) {
        return User.builder()
                .nombre(r.getNombre())
                .apellido(r.getApellido())
                .email(r.getEmail())
                .passwordHash(encodedPassword)
                .active(true)
                .addRole(role)
                .build();
    }

}
