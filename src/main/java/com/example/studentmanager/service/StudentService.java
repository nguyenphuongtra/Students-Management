package com.example.studentmanager.service;
import java.util.List;
import com.example.studentmanager.entity.Student;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(Integer id);
    Student saveStudent(Student student);
    void deleteStudent(Integer id);
    List<Student> searchStudents(String keyword);
}