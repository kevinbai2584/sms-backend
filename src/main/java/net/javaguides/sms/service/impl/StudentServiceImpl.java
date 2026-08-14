package net.javaguides.sms.service.impl;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.javaguides.sms.dto.StudentDto;
import net.javaguides.sms.entity.Student;
import net.javaguides.sms.exception.ResourceNotFoundException;
import net.javaguides.sms.mapper.StudentMapper;
import net.javaguides.sms.repository.StudentRepository;
import net.javaguides.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto getStudentById(Long StudentId) {

        Student student = studentRepository.findById(StudentId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Student is not exist with given id: "+ StudentId));


        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getAllStudents() {
        List <Student> students = studentRepository.findAll();
        return students.stream().map((student)-> StudentMapper.mapToStudentDto(student))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public StudentDto updateStudent(Long StudentId, StudentDto updatedStudent) {
        Student student = studentRepository.findById(StudentId).orElseThrow(()->
                new ResourceNotFoundException("Student is not exist with given id: " + StudentId)
                );
        student.setEmail(updatedStudent.getEmail());
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());

        Student updatedStudentObj = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()->
                new ResourceNotFoundException("Student is not exist with given id: " + studentId)
        );
        studentRepository.deleteById(studentId);
    }


}
