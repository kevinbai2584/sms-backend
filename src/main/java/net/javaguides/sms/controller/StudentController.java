package net.javaguides.sms.controller;

import lombok.AllArgsConstructor;
import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.service.StudentService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    //Build Add Student REST API
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        StudentDto savedStudent = studentService.createStudent(studentDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // Build Get Student REST API
    @GetMapping("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long studentId){
        StudentDto studentDto = studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentDto);
    }


    @GetMapping
    public ResponseEntity<List<StudentDto>> getSortedStudents(@RequestParam(defaultValue = "lastName", required = false) String sort){
        List<StudentDto> studentsDto = studentService.getAllStudents();
        if(sort.equals("lastName")) {
            studentsDto.sort((u1, u2) -> u1.getLastName().compareTo(u2.getLastName()));
        } else if (sort.equals("firstName")) {
            studentsDto.sort((u1, u2) -> u1.getFirstName().compareTo(u2.getFirstName()));

        } else if (sort.equals("email")) {
            studentsDto.sort((u1, u2) -> u1.getEmail().compareTo(u2.getEmail()));
        }

        return ResponseEntity.ok(studentsDto);
    }




    // Build Get All Students REST API

//    @GetMapping
//
//    public ResponseEntity<List<StudentDto>> getAllStudents(){
//
//        List<StudentDto> students = studentService.getAllStudents();
//        return ResponseEntity.ok(students);
//    }

    //Build Updated Student REST API
    @PutMapping("{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long studentId, @RequestBody StudentDto updatedStudent){
        StudentDto studentDto = studentService.updateStudent(studentId,updatedStudent);


        return ResponseEntity.ok(studentDto);
    }
    //Build Delete Student REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long studentId){
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("Student deleted successfully!");
    }



}
