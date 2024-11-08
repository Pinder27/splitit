package com.example.splitit.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Data // Generates getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(unique = true, nullable = false)
    private String joinToken = UUID.randomUUID().toString();

    @Column(name = "created_by", nullable = false)
    private Long createdBy; // The user who created this user (nullable)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupMember> members = new HashSet<>();


}
