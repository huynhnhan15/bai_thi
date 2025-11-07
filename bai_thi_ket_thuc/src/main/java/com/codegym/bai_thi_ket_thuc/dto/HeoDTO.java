package com.codegym.bai_thi_ket_thuc.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeoDTO {

    private Long id;

    @NotBlank(message = "Mã số heo không được để trống")
    private String maHeo;

    @NotNull(message = "Ngày nhập không được để trống")
    private LocalDate ngayNhap;

    @NotNull(message = "Trọng lượng nhập không được để trống")
    @DecimalMin(value = "10.0", inclusive = false, message = "Trọng lượng phải lớn hơn 10")
    private Double trongLuongNhap;

    private LocalDate ngayXuat;

    @DecimalMin(value = "0.0", inclusive = false, message = "Trọng lượng xuất phải lớn hơn 0")
    private Double trongLuongXuat;

    @NotNull(message = "Xuất xứ không được để trống")
    private Long xuatXuId;

    private String xuatXuTen;
    private boolean daBan;
}