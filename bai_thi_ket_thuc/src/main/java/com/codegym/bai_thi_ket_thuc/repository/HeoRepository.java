package com.codegym.bai_thi_ket_thuc.repository;


import com.codegym.bai_thi_ket_thuc.entity.Heo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HeoRepository extends JpaRepository<Heo, Long> {

    @Query("SELECT h FROM Heo h WHERE " +
            "(:tinhTrang = 'all' OR " +
            "(:tinhTrang = 'da_ban' AND h.ngayXuat IS NOT NULL) OR " +
            "(:tinhTrang = 'chua_ban' AND h.ngayXuat IS NULL)) AND " +
            "(:maHeo IS NULL OR h.maHeo LIKE %:maHeo%) AND " +
            "(:xuatXuId IS NULL OR h.xuatXu.id = :xuatXuId)")
    Page<Heo> searchHeo(@Param("tinhTrang") String tinhTrang,
                        @Param("maHeo") String maHeo,
                        @Param("xuatXuId") Long xuatXuId,
                        Pageable pageable);

    @Query("SELECT h FROM Heo h WHERE h.ngayXuat IS NOT NULL ORDER BY h.trongLuongXuat DESC")
    Page<Heo> findTopByTrongLuongXuat(Pageable pageable);
}