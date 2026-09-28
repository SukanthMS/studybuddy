package com.example.studybuddy.service;

import com.example.studybuddy.entity.Student;
import com.example.studybuddy.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository=studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student update(Integer id,Student student) {
        Student existing=studentRepository.findById(id).orElse(null);

        if(existing==null){
            return null;
        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());

        return studentRepository.save(existing);
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}