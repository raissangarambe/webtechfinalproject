package com.webtechproject.backend.service;

import com.webtechproject.backend.model.GymMembership;
import com.webtechproject.backend.model.User;
import com.webtechproject.backend.repository.GymMembershipRepository;
import com.webtechproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymMembershipService {

    @Autowired
    private GymMembershipRepository gymMembershipRepository;

    @Autowired
    private UserRepository userRepository;

    public GymMembership createMembership(Long userId, GymMembership membership) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        membership.setUser(userOptional.get());
        return gymMembershipRepository.save(membership);
    }

    public List<GymMembership> getAllMemberships() {
        return gymMembershipRepository.findAll();
    }

    public GymMembership getMembershipById(Long membershipId) {
        return gymMembershipRepository.findById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found with ID: " + membershipId));
    }

    public GymMembership updateMembership(Long membershipId, GymMembership updatedMembership) {
        GymMembership existingMembership = gymMembershipRepository.findById(membershipId)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found with ID: " + membershipId));

        existingMembership.setStartDate(updatedMembership.getStartDate());
        existingMembership.setEndDate(updatedMembership.getEndDate());
        existingMembership.setType(updatedMembership.getType());
        existingMembership.setPrice(updatedMembership.getPrice());
        return gymMembershipRepository.save(existingMembership);
    }

    public void deleteMembership(Long membershipId) {
        if (!gymMembershipRepository.existsById(membershipId)) {
            throw new IllegalArgumentException("Membership not found with ID: " + membershipId);
        }
        gymMembershipRepository.deleteById(membershipId);
    }
}
