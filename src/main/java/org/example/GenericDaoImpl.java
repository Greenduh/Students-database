package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Objects;

public class GenericDaoImpl implements GenericDao<Student, Long> {

    private EntityManagerFactory emf;

    public GenericDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void save(final Student student) {
        Objects.requireNonNull(student, "Parameter [student] must not be null!");
        EntityManager entityManager = this.emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(student);
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new GenericDaoException("Cannot save student %s".formatted(student));
        }
    }

    @Override
    public Student findById(Long id) {
        return emf.createEntityManager()
                .createQuery("FROM Student a WHERE a.id = :id", Student.class)
                .setParameter("email", Objects.requireNonNull(id))
                .getSingleResult();
    }

    @Override
    public Student findByEmail(String email) {
        return emf.createEntityManager()
                .createQuery("FROM Student a WHERE a.email = :email", Student.class)
                .setParameter("email", Objects.requireNonNull(email))
                .getSingleResult();
    }

    @Override
    public List<Student> findAll() {
        return emf.createEntityManager()
                .createQuery("FROM Student", Student.class)
                .getResultList();
    }

    @Override
    public Student update(Student student) {
        Objects.requireNonNull(student, "Parameter [student] must not be null!");
        EntityManager entityManager = this.emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("UPDATE Student " + "SET firstName = :firstName, lastName = :lastName, " + "phone = :phone, email = :email WHERE id = :id", Student.class)
                    .setParameter("firstName", student.getFirstName())
                    .setParameter("lastName", student.getLastName())
                    .setParameter("email", student.getEmail())
                    .setParameter("id", student.getId())
                    .executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new GenericDaoException("Cannot update student %s".formatted(student));
        }
        return student;
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        EntityManager entityManager = this.emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("DELETE FROM student a WHERE a.id = :id", Student.class).setParameter("id", Objects.requireNonNull(id));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new GenericDaoException("Cannot delete id %s".formatted(id));
        }
        return true;
    }
}