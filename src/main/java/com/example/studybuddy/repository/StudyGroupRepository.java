package com.example.studybuddy.repository;

import com.example.studybuddy.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup,Integer> {
}