package com.example.studybuddy.repository;

import com.example.studybuddy.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}