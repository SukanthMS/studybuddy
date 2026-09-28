package com.example.studybuddy.service;

import com.example.studybuddy.entity.GroupMembership;
import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.repository.GroupMembershipRepository;
import com.example.studybuddy.repository.StudyGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupMembershipService {

    private final GroupMembershipRepository membershipRepository;
    private final StudyGroupRepository studyGroupRepository;

    public GroupMembershipService(GroupMembershipRepository membershipRepository,
                                  StudyGroupRepository studyGroupRepository) {
        this.membershipRepository=membershipRepository;
        this.studyGroupRepository=studyGroupRepository;
    }

    public GroupMembership joinGroup(Integer groupId,Integer studentId) {

        StudyGroup group=studyGroupRepository.findById(groupId).orElse(null);

        if(group==null){
            throw new RuntimeException("GROUP_NOT_FOUND");
        }

        if(membershipRepository.existsByGroupIdAndStudentId(groupId,studentId)){
            throw new RuntimeException("ALREADY_JOINED");
        }

        long currentMembers=membershipRepository.countByGroupId(groupId);

        if(currentMembers>=group.getMaxMembers()){
            throw new RuntimeException("GROUP_FULL");
        }

        GroupMembership membership=new GroupMembership();
        membership.setGroupId(groupId);
        membership.setStudentId(studentId);
        membership.setJoinedAt(LocalDateTime.now());

        return membershipRepository.save(membership);
    }

    public List<GroupMembership> getMembersByGroup(Integer groupId) {
        return membershipRepository.findByGroupId(groupId);
    }

    public List<GroupMembership> getGroupsByStudent(Integer studentId) {
        return membershipRepository.findByStudentId(studentId);
    }

    @Transactional
    public void leaveGroup(Integer groupId,Integer studentId) {
        membershipRepository.deleteByGroupIdAndStudentId(groupId,studentId);
    }

    @Transactional
    public void removeMember(Integer groupId,Integer studentId,Integer creatorId) {

        StudyGroup group=studyGroupRepository.findById(groupId).orElse(null);

        if(group==null){
            throw new RuntimeException("GROUP_NOT_FOUND");
        }

        if(!group.getCreatorId().equals(creatorId)){
            throw new RuntimeException("NOT_GROUP_CREATOR");
        }

        membershipRepository.deleteByGroupIdAndStudentId(groupId,studentId);
    }
}