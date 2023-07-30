package com.example.repository;

import com.example.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ADMIN 7/30/2023
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByDeviceToken(String deviceToken);
}
