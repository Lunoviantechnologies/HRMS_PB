package com.example.Adminerepository;

import org.bytedeco.librealsense.intrinsics;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AdmineEntity.leavepolicy;

public interface leavePolicyRepo extends JpaRepository<leavepolicy, Integer>{

}
