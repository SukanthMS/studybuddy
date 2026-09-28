package com.example.studybuddy.controller;

import com.example.studybuddy.entity.Student;
import com.example.studybuddy.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService=studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Integer id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Integer id,@RequestBody Student student) {
        return studentService.update(id,student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.delete(id);
    }
}