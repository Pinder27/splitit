package com.example.splitit.repo;

import com.example.splitit.model.Group;
import com.example.splitit.model.GroupMember;
import com.example.splitit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // Method to find a GroupMember by Group and User
    Optional<GroupMember> findByGroupAndUser(Group group, User user);
}
