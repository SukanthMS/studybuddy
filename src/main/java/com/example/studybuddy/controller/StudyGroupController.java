package com.example.studybuddy.controller;

import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.service.StudyGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService=studyGroupService;
    }

    @PostMapping
    public StudyGroup create(@RequestBody StudyGroup group) {
        return studyGroupService.create(group);
    }

    @GetMapping
    public List<StudyGroup> getAll() {
        return studyGroupService.getAll();
    }

    @GetMapping("/{id}")
    public StudyGroup getById(@PathVariable Integer id) {
        return studyGroupService.getById(id);
    }

    @PutMapping("/{id}")
    public StudyGroup update(@PathVariable Integer id,@RequestBody StudyGroup group) {
        return studyGroupService.update(id,group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        studyGroupService.delete(id);
    }
}