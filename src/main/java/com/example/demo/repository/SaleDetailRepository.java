package com.example.demo.repository;

import com.example.demo.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
