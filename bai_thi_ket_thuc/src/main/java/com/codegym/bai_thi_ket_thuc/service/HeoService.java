package com.codegym.bai_thi_ket_thuc.service;

import com.codegym.bai_thi_ket_thuc.dto.HeoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HeoService {

    Page<HeoDTO> searchHeo(String tinhTrang, String maHeo, Long xuatXuId, Pageable pageable);

    HeoDTO getHeoById(Long id);

    HeoDTO saveHeo(HeoDTO heoDTO);

    HeoDTO updateHeo(Long id, HeoDTO heoDTO);

    void deleteHeo(Long id);

    Page<HeoDTO> getTopHeoByWeight(int limit);
}