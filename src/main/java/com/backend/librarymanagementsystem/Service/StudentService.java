package com.backend.librarymanagementsystem.Service;

import com.backend.librarymanagementsystem.DTO.StudentRequestDto;
import com.backend.librarymanagementsystem.DTO.StudentResponseDto;
import com.backend.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backend.librarymanagementsystem.Entity.LibraryCard;
import com.backend.librarymanagementsystem.Entity.Student;
import com.backend.librarymanagementsystem.Enum.CardStatus;
import com.backend.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public void addStudent(StudentRequestDto studentRequestDto)
    {
        //Create a student object
        Student student=new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setDepartment(studentRequestDto.getDepartment());
        student.setEmail(studentRequestDto.getEmail());

        //create a card object
       LibraryCard libraryCard=new LibraryCard();
       libraryCard.setCardStatus(CardStatus.ACTIVATED);
       libraryCard.setStudent(student);

       //set card in student
       student.setCard(libraryCard);
       //save student and car librarywill be saved by default
       studentRepository.save(student);

    }
    public String findStudentByEmail(String email)
    {
        Student student=studentRepository.findByEmail(email);
        return student.getName();
    }
    public List<String> findStudentsByAge(int age)
    {
        List<Student> students=studentRepository.findByAge(age);
        List<String> names=new ArrayList<>();
        for(Student s:students)
        {
            names.add(s.getName());
        }
        return names;
    }
    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto)
    {
        Student  student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());
        Student updatedStudent=studentRepository.save(student);
        StudentResponseDto studentResponseDto=new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getStudId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());
        return  studentResponseDto;
    }

}
