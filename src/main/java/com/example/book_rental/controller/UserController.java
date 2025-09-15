package com.example.book_rental.controller;

import com.example.book_rental.dto.GeneralAPIResponse;
import com.example.book_rental.dto.UserRegisterRequest;
import com.example.book_rental.entity.Borrower;
import com.example.book_rental.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<GeneralAPIResponse> register(@RequestBody UserRegisterRequest request){
        userService.registerUser(request.fullName(), request.email());
        GeneralAPIResponse<Borrower> response = new GeneralAPIResponse<>(
                "Success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<GeneralAPIResponse<List<Borrower>>> getAllUsers() {
        List<Borrower> listUser =  userService.getAllUsers();
        GeneralAPIResponse<List<Borrower>> response = new GeneralAPIResponse<List<Borrower>>(
                "Success", listUser
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/detail")
    public GeneralAPIResponse<Borrower> getUser(@RequestParam Long userId) {
        Borrower borrower = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new GeneralAPIResponse<>("Success", borrower);
    }
}
