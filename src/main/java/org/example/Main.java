package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hillel-persistence-unit");

        Student student1 = new Student();
        student1.setFirstName("John");
        student1.setLastName("Snow");
        student1.setEmail("j.snow@gmail.com");

        Homework homework1 = new Homework();
        homework1.setDescription("homework description");
        homework1.setDeadline(LocalDate.parse("2025-10-10"));
        homework1.setMark(99);
        homework1.setStudent(student1);

        student1.addHomework(homework1);

        GenericDao dao = new GenericDaoImpl(emf);
        dao.save(student1);

        Student newStudent = (Student) dao.findByEmail(student1.getEmail());
        System.out.println(newStudent);
    }
}