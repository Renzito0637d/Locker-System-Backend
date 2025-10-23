package com.lockersystem_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lockersystem_backend.Entity.Locker;
import com.lockersystem_backend.Model.CreateLockerRequest;
import com.lockersystem_backend.Model.UpdateLockerRequest;
import com.lockersystem_backend.Service.Interfaces.LockerService;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @GetMapping("/")
    public List<Locker> getAll() {
        return lockerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locker> getById(@PathVariable Long id) {
        Optional<Locker> l = lockerService.findById(id);
        return l.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Locker> create(@RequestBody CreateLockerRequest dto) {
        Locker created = lockerService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locker> update(@PathVariable Long id, @RequestBody UpdateLockerRequest dto) {
        var updated = lockerService.update(id, dto);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (lockerService.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        lockerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
