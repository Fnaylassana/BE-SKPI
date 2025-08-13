package com.skpijtk.springboot_boilerplate.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


import com.skpijtk.springboot_boilerplate.constant.CheckInStatus;
import com.skpijtk.springboot_boilerplate.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>  {
    // Total sudah check-in hari ini
    long countByAttendanceDateAndCheckInTimeIsNotNull(LocalDate date);

    List<Attendance> findByStudentId(Long studentId);

    long countByAttendanceDateAndCheckInStatus(LocalDate date, CheckInStatus status);

    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    @Query("SELECT a FROM Attendance a " +
       "JOIN a.student s " +
       "WHERE (:studentId IS NULL OR s.id = :studentId) " +
       "AND (:startDate IS NULL OR a.attendanceDate >= :startDate) " +
       "AND (:endDate IS NULL OR a.attendanceDate <= :endDate)")
    List<Attendance> findFilteredAttendances(
        @Param("studentId") Long studentId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

}
