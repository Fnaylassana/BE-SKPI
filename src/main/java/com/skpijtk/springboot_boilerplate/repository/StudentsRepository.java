package com.skpijtk.springboot_boilerplate.repository;

import com.skpijtk.springboot_boilerplate.model.Student;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentsRepository extends JpaRepository<Student, Long> {
    @Query("SELECT COUNT(s) FROM Student s")
    long countTotalStudents();

    Optional<Student> findByUserId(Long userId);

    @Query("SELECT s FROM Student s JOIN s.user u " +
       "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
       "ORDER BY u.name ASC")
    Page<Student> findFilteredStudents(@Param("name") String name, Pageable pageable);

}
