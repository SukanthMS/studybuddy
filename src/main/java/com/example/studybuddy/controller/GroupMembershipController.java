package com.example.studybuddy.controller;

import com.example.studybuddy.entity.GroupMembership;
import com.example.studybuddy.service.GroupMembershipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memberships")
public class GroupMembershipController {

    private final GroupMembershipService membershipService;

    public GroupMembershipController(GroupMembershipService membershipService) {
        this.membershipService=membershipService;
    }

    @PostMapping("/join")
    public GroupMembership joinGroup(@RequestBody Map<String,Integer> request) {
        Integer groupId=request.get("groupId");
        Integer studentId=request.get("studentId");

        return membershipService.joinGroup(groupId,studentId);
    }

    @GetMapping("/group/{groupId}")
    public List<GroupMembership> getMembersByGroup(@PathVariable Integer groupId) {
        return membershipService.getMembersByGroup(groupId);
    }

    @GetMapping("/student/{studentId}")
    public List<GroupMembership> getGroupsByStudent(@PathVariable Integer studentId) {
        return membershipService.getGroupsByStudent(studentId);
    }

    @DeleteMapping("/leave")
    public void leaveGroup(@RequestParam Integer groupId,
                           @RequestParam Integer studentId) {
        membershipService.leaveGroup(groupId,studentId);
    }

    @DeleteMapping("/remove")
    public void removeMember(@RequestParam Integer groupId,
                             @RequestParam Integer studentId,
                             @RequestParam Integer creatorId) {
        membershipService.removeMember(groupId,studentId,creatorId);
    }
}