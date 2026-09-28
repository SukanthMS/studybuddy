package com.example.studybuddy.service;

import com.example.studybuddy.entity.StudyGroup;
import com.example.studybuddy.repository.StudyGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    public StudyGroupService(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository=studyGroupRepository;
    }

    public StudyGroup create(StudyGroup group) {
        return studyGroupRepository.save(group);
    }

    public List<StudyGroup> getAll() {
        return studyGroupRepository.findAll();
    }

    public StudyGroup getById(Integer id) {
        return studyGroupRepository.findById(id).orElse(null);
    }

    public StudyGroup update(Integer id,StudyGroup group) {
        StudyGroup existing=studyGroupRepository.findById(id).orElse(null);

        if(existing==null){
            return null;
        }

        existing.setSubject(group.getSubject());
        existing.setMaxMembers(group.getMaxMembers());
        existing.setCreatorId(group.getCreatorId());

        return studyGroupRepository.save(existing);
    }

    public void delete(Integer id) {
        studyGroupRepository.deleteById(id);
    }
}