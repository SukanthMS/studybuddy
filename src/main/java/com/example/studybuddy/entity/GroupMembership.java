package com.example.studybuddy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="group_memberships")
public class GroupMembership {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private Integer groupId;
    private Integer studentId;
    private LocalDateTime joinedAt;

    public GroupMembership() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId=groupId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId=studentId;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt=joinedAt;
    }
}