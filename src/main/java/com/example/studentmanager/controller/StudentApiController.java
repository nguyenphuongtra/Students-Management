package com.example.studentmanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanager.common.ApiResponse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*") 
public class StudentApiController {

    @Autowired
    private StudentService studentService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> list = studentService.getAllStudents();
        return ResponseEntity.ok(new ApiResponse<>("success", "Tải danh sách thành công", list));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(new ApiResponse<>("success", "Tìm thấy sinh viên", student));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("error", "Không tìm thấy sinh viên ID: " + id));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("success", "Thêm mới thành công", savedStudent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", e.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@PathVariable Integer id, @RequestBody Student studentDetails) {
        Student currentStudent = studentService.getStudentById(id);
        if (currentStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("error", "Không tìm thấy sinh viên để sửa"));
        }
        
        currentStudent.setName(studentDetails.getName());
        currentStudent.setEmail(studentDetails.getEmail());
        
        Student updatedStudent = studentService.saveStudent(currentStudent);
        return ResponseEntity.ok(new ApiResponse<>("success", "Cập nhật thành công", updatedStudent));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Integer id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(new ApiResponse<>("success", "Xóa thành công", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Lỗi khi xóa: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Student>>> searchStudents(@RequestParam("keyword") String keyword) {
        try {
            List<Student> results = studentService.searchStudents(keyword);
            
            if (results.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>("success", "Không tìm thấy kết quả nào", results));
            }
            
            return ResponseEntity.ok(new ApiResponse<>("success", "Tìm thấy " + results.size() + " kết quả", results));
            
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("error", "Lỗi tìm kiếm: " + e.getMessage()));
        }
    }

}