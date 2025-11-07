package com.codegym.bai_thi_ket_thuc.service;
import com.codegym.bai_thi_ket_thuc.dto.HeoDTO;
import com.codegym.bai_thi_ket_thuc.entity.Heo;
import com.codegym.bai_thi_ket_thuc.entity.XuatXu;
import com.codegym.bai_thi_ket_thuc.repository.HeoRepository;
import com.codegym.bai_thi_ket_thuc.repository.XuatXuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HeoServiceImpl implements HeoService {

    @Autowired
    private HeoRepository heoRepository;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Override
    public Page<HeoDTO> searchHeo(String tinhTrang, String maHeo, Long xuatXuId, Pageable pageable) {
        if (tinhTrang == null || tinhTrang.isEmpty()) {
            tinhTrang = "all";
        }
        Page<Heo> heoPage = heoRepository.searchHeo(tinhTrang, maHeo, xuatXuId, pageable);
        return heoPage.map(this::convertToDTO);
    }

    @Override
    public HeoDTO getHeoById(Long id) {
        Heo heo = heoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy heo với mã: " + id));
        return convertToDTO(heo);
    }

    @Override
    public HeoDTO saveHeo(HeoDTO heoDTO) {
        Heo heo = convertToEntity(heoDTO);
        Heo savedHeo = heoRepository.save(heo);
        return convertToDTO(savedHeo);
    }

    @Override
    public HeoDTO updateHeo(Long id, HeoDTO heoDTO) {
        Heo existingHeo = heoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy heo với mã: " + id));

        existingHeo.setMaHeo(heoDTO.getMaHeo());
        existingHeo.setNgayNhap(heoDTO.getNgayNhap());
        existingHeo.setTrongLuongNhap(heoDTO.getTrongLuongNhap());
        existingHeo.setNgayXuat(heoDTO.getNgayXuat());
        existingHeo.setTrongLuongXuat(heoDTO.getTrongLuongXuat());

        XuatXu xuatXu = xuatXuRepository.findById(heoDTO.getXuatXuId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xuất xứ"));
        existingHeo.setXuatXu(xuatXu);

        Heo updatedHeo = heoRepository.save(existingHeo);
        return convertToDTO(updatedHeo);
    }

    @Override
    public void deleteHeo(Long id) {
        heoRepository.deleteById(id);
    }

    @Override
    public Page<HeoDTO> getTopHeoByWeight(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<Heo> heoPage = heoRepository.findTopByTrongLuongXuat(pageable);
        return heoPage.map(this::convertToDTO);
    }

    private HeoDTO convertToDTO(Heo heo) {
        HeoDTO dto = new HeoDTO();
        dto.setId(heo.getId());
        dto.setMaHeo(heo.getMaHeo());
        dto.setNgayNhap(heo.getNgayNhap());
        dto.setTrongLuongNhap(heo.getTrongLuongNhap());
        dto.setNgayXuat(heo.getNgayXuat());
        dto.setTrongLuongXuat(heo.getTrongLuongXuat());
        dto.setXuatXuId(heo.getXuatXu().getId());
        dto.setXuatXuTen(heo.getXuatXu().getTen());
        dto.setDaBan(heo.isDaBan());
        return dto;
    }

    private Heo convertToEntity(HeoDTO dto) {
        Heo heo = new Heo();
        heo.setId(dto.getId());
        heo.setMaHeo(dto.getMaHeo());
        heo.setNgayNhap(dto.getNgayNhap());
        heo.setTrongLuongNhap(dto.getTrongLuongNhap());
        heo.setNgayXuat(dto.getNgayXuat());
        heo.setTrongLuongXuat(dto.getTrongLuongXuat());

        XuatXu xuatXu = xuatXuRepository.findById(dto.getXuatXuId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xuất xứ"));
        heo.setXuatXu(xuatXu);

        return heo;
    }
}
