package com.codegym.bai_thi_ket_thuc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "heo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Heo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_heo", nullable = false, unique = true)
    private String maHeo;

    @Column(name = "ngay_nhap", nullable = false)
    private LocalDate ngayNhap;

    @Column(name = "trong_luong_nhap", nullable = false)
    private Double trongLuongNhap;

    @Column(name = "ngay_xuat")
    private LocalDate ngayXuat;

    @Column(name = "trong_luong_xuat")
    private Double trongLuongXuat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "xuat_xu_id", nullable = false)
    private XuatXu xuatXu;

    @Transient
    public boolean isDaBan() {
        return ngayXuat != null;
    }
}