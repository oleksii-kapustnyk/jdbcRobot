package org.example.repository;

import org.example.domain.Student;

import java.util.List;

public interface StudentRepository {

    void save(Student student);

    List<Student> findAll();

    Student findById(int id);
}
