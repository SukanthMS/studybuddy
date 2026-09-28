package com.example.studybuddy.repository;

import com.example.studybuddy.entity.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership,Integer> {

    List<GroupMembership> findByGroupId(Integer groupId);

    List<GroupMembership> findByStudentId(Integer studentId);

    boolean existsByGroupIdAndStudentId(Integer groupId,Integer studentId);

    long countByGroupId(Integer groupId);

    void deleteByGroupIdAndStudentId(Integer groupId,Integer studentId);
}