package com.example.splitit.service;

import com.example.splitit.model.Group;
import com.example.splitit.model.GroupMember;
import com.example.splitit.model.User;
import com.example.splitit.repo.GroupMemberRepository;
import com.example.splitit.repo.GroupRepository;
import com.example.splitit.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository; // Assuming UserRepository exists for fetching users

    @Transactional
    public Group createGroup(String name, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Group group = new Group();
        group.setName(name);
        group.setCreatedBy(creatorId);  // Directly store the user ID

        groupRepository.save(group);

        // Optionally add the creator as a group member with the "admin" role

        GroupMember creatorMember = new GroupMember();
        creatorMember.setGroup(group);
        creatorMember.setUser(creator);
        creatorMember.setRole("admin");
        groupMemberRepository.save(creatorMember);

        return group;
    }

    public List<User> getGroupMembers(Long groupId) {
        return groupMemberRepository.findMembersByGroupId(groupId);
    }

    public List<Group> getGroupsByUserId(Long userId) {
        return groupMemberRepository.findGroupsByUserId(userId);
    }

    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Group updateGroup(Long id, Group groupDetails) {
        return groupRepository.findById(id).map(group -> {
            group.setName(groupDetails.getName());
            return groupRepository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    public Group addMemberToGroup(Long groupId, Long userId, String role) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already a member of the group
        Optional<GroupMember> existingMember = groupMemberRepository.findByGroupAndUser(group, user);
        if (existingMember.isPresent()) {
            throw new RuntimeException("User is already a member of this group");
        }

        // Create and save a new GroupMember entry with the specified role
        GroupMember newMember = new GroupMember();
        newMember.setGroup(group);
        newMember.setUser(user);
        newMember.setRole(role);

        groupMemberRepository.save(newMember);

        return group;
    }
    @Transactional
    public Group joinGroupWithToken(String token, Long userId, String defaultRole) {
        Group group = groupRepository.findByJoinToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already a member
        Optional<GroupMember> existingMember = groupMemberRepository.findByGroupAndUser(group, user);
        if (existingMember.isPresent()) {
            return group; // User is already a member
        }

        // Add new member with the default role
        GroupMember newMember = new GroupMember();
        newMember.setGroup(group);
        newMember.setUser(user);
        newMember.setRole(defaultRole); // e.g., "member" by default

        groupMemberRepository.save(newMember);

        return group;
    }


}
