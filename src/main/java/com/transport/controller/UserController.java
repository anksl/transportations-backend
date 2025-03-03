package com.transport.controller;

import com.transport.api.dto.user.RegistrationUserDto;
import com.transport.api.dto.user.UpdateUserDto;
import com.transport.api.dto.user.UserDto;
import com.transport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void signUp(@Validated @RequestBody RegistrationUserDto user) {
        userService.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/find")
    public ResponseEntity<UserDto> findByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserDto> updateUser(@PathVariable(value = "id") Long id, @Validated @RequestBody UpdateUserDto newUser) {
        return ResponseEntity.ok(userService.updateUser(id, newUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}