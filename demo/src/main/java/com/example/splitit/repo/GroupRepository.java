package com.example.splitit.repo;

import com.example.splitit.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    // Method to find a group by join token
    Optional<Group> findByJoinToken(String joinToken);
}
