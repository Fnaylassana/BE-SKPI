package com.skpijtk.springboot_boilerplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skpijtk.springboot_boilerplate.model.AppSettings;

public interface AppSettingRepository extends JpaRepository<AppSettings, Long>{
    
}
