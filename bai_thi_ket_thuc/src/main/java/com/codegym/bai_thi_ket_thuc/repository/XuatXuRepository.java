package com.codegym.bai_thi_ket_thuc.repository;

import com.codegym.bai_thi_ket_thuc.entity.XuatXu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XuatXuRepository extends JpaRepository<XuatXu, Long> {
}