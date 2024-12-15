package com.webtechproject.backend.repository;

import com.webtechproject.backend.model.GymMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymMembershipRepository extends JpaRepository<GymMembership, Long> {
}
