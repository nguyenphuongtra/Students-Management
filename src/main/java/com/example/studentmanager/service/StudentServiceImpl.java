package com.example.studentmanager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> searchStudents(String keyword) {
        
        if (keyword == null || keyword.isEmpty()) {
        return studentRepository.findAll(); 
        }
        return studentRepository.searchStudents(keyword);
    }
}