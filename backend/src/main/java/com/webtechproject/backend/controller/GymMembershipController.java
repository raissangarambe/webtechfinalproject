package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.GymMembership;
import com.webtechproject.backend.service.GymMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class GymMembershipController {

    @Autowired
    private GymMembershipService gymMembershipService;

    @PostMapping("/{userId}")
    public ResponseEntity<GymMembership> createMembership(@PathVariable Long userId, @RequestBody GymMembership membership) {
        return ResponseEntity.ok(gymMembershipService.createMembership(userId, membership));
    }

    @GetMapping
    public ResponseEntity<List<GymMembership>> getAllMemberships() {
        return ResponseEntity.ok(gymMembershipService.getAllMemberships());
    }

    @GetMapping("/{membershipId}")
    public ResponseEntity<GymMembership> getMembershipById(@PathVariable Long membershipId) {
        return ResponseEntity.ok(gymMembershipService.getMembershipById(membershipId));
    }

    @PutMapping("/{membershipId}")
    public ResponseEntity<GymMembership> updateMembership(@PathVariable Long membershipId, @RequestBody GymMembership membership) {
        return ResponseEntity.ok(gymMembershipService.updateMembership(membershipId, membership));
    }

    @DeleteMapping("/{membershipId}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long membershipId) {
        gymMembershipService.deleteMembership(membershipId);
        return ResponseEntity.noContent().build();
    }
}
