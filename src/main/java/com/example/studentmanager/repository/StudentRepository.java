package com.example.studentmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.studentmanager.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Student> searchStudents(@Param("keyword") String keyword);
}