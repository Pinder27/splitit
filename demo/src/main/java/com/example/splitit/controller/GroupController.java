package com.example.splitit.controller;

import com.example.splitit.model.Group;
import com.example.splitit.model.User;
import com.example.splitit.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/creategroup")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.createGroup(group.getName(),group.getCreatedBy()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.getGroupById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<User>> getGroupMembers(@PathVariable Long groupId) {
        List<User> members = groupService.getGroupMembers(groupId);
        if (members.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(members);
    }

    // Endpoint to get all groups of a user
    @GetMapping("/user/{userId}")
    public List<Group> getUserGroups(@PathVariable Long userId) {
        return groupService.getGroupsByUserId(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group groupDetails) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to add a member with a specific role to a group
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<Group> addMemberToGroup(@PathVariable Long groupId,
                                                  @RequestParam Long userId,
                                                  @RequestParam String role) {
        return ResponseEntity.ok(groupService.addMemberToGroup(groupId, userId, role));
    }

    // Endpoint to join a group with a join token and default role
    @PostMapping("/join")
    public ResponseEntity<Group> joinGroupWithToken(@RequestParam String token,
                                                    @RequestParam Long userId,
                                                    @RequestParam(defaultValue = "member") String defaultRole) {
        return ResponseEntity.ok(groupService.joinGroupWithToken(token, userId, defaultRole));
    }
}
