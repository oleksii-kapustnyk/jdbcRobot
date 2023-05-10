package org.example;

import org.example.domain.Student;
import org.example.repository.StudentMysqlRepository;
import org.example.repository.StudentRepository;

import java.sql.*;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentMysqlRepository();

//        Student s1 = Student.builder()
//                .age(20)
//                .name("Petruk")
//                .groupId(4)
//                .build();
//        System.out.println("BS");
//        studentRepository.save(s1);
//        System.out.println("AS");
        List<Student> students = studentRepository.findAll();
        System.out.println(students);
//
//        Student student = studentRepository.findById(1);
//        System.out.println(student);

    }
}