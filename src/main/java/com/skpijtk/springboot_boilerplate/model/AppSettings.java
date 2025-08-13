package com.skpijtk.springboot_boilerplate.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appsettings")
public class AppSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "default_check_in_time", nullable = false)
    private LocalTime defaultCheckInTime;

    @Column(name = "default_check_out_time", nullable = false)
    private LocalTime defaultCheckOutTime;

    @Column(name = "check_in_late_tolerance_minutes", nullable = false)
    private Integer checkInLateToleranceMinutes;

    @Column(name = "check_out_late_tolerance_minutes", nullable = false)
    private Integer checkOutLateToleranceMinutes;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime  updatedAt;

    // Getters & Setters
    public Integer getId() { return id; }

    public LocalTime getDefaultCheckInTime() { return defaultCheckInTime; }
    public void setDefaultCheckInTime(LocalTime defaultCheckInTime) {
        this.defaultCheckInTime = defaultCheckInTime;
    }

    public LocalTime getDefaultCheckOutTime() { return defaultCheckOutTime; }
    public void setDefaultCheckOutTime(LocalTime defaultCheckOutTime) {
        this.defaultCheckOutTime = defaultCheckOutTime;
    }

    public Integer getCheckInLateToleranceMinutes() { return checkInLateToleranceMinutes; }
    public void setCheckInLateToleranceMinutes(Integer checkInLateToleranceMinutes) {
        this.checkInLateToleranceMinutes = checkInLateToleranceMinutes;
    }

    public Integer getCheckOutLateToleranceMinutes() { return checkOutLateToleranceMinutes; }
    public void setCheckOutLateToleranceMinutes(Integer checkOutLateToleranceMinutes) {
        this.checkOutLateToleranceMinutes = checkOutLateToleranceMinutes;
    }

    public LocalDateTime  getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime  updatedAt) { this.updatedAt = updatedAt; }
}
