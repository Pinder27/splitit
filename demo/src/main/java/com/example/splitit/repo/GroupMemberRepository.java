package com.example.splitit.repo;

import com.example.splitit.model.Group;
import com.example.splitit.model.GroupMember;
import com.example.splitit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    // Method to find a GroupMember by Group and User
    Optional<GroupMember> findByGroupAndUser(Group group, User user);

    @Query("SELECT gm.group FROM GroupMember gm WHERE gm.user.id = :userId")
    List<Group> findGroupsByUserId(Long userId);

    @Query("SELECT gm.user FROM GroupMember gm WHERE gm.group.id = :groupId")
    List<User> findMembersByGroupId(Long groupId);
}
