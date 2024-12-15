package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.User;
import com.webtechproject.backend.service.OTPService;
import com.webtechproject.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {

            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        Map<String, Object> response = new HashMap<>();

        if (existingUser == null || !userService.checkPassword(existingUser, user.getPassword())) {
            response.put("message", "Invalid credentials!");
            return ResponseEntity.status(401).body(response);
        }

        response.put("message", "Login successful!");
        response.put("role", existingUser.getRole().toString());//Add role to the response
        response.put("userId", existingUser.getUid().toString()); // Add User_id to the response
        return ResponseEntity.ok(response);
    }


    // Endpoint to update password
    @PutMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestParam String email, @RequestParam String newPassword) {
        Map<String, String> response = new HashMap<>();
        try {
            User user = userService.findUserByEmail(email); // Find the user by email
            userService.updatePassword(user, newPassword); // Update the password
            response.put("message", "Password updated successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Autowired
    private OTPService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = userService.findUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        String otp = otpService.generateOTP(email);
        otpService.sendOTPEmail(email, otp);

        return ResponseEntity.ok(Map.of("message", "OTP sent to email!"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (otpService.validateOTP(email, otp)) {
            return ResponseEntity.ok(Map.of("message", "OTP verified successfully!", "role", userService.findUserByEmail(email).getRole()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP!");
        }
    }

}
