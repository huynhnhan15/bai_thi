package com.codegym.bai_thi_ket_thuc.controller;



import com.codegym.bai_thi_ket_thuc.dto.HeoDTO;
import com.codegym.bai_thi_ket_thuc.entity.XuatXu;
import com.codegym.bai_thi_ket_thuc.repository.XuatXuRepository;
import com.codegym.bai_thi_ket_thuc.service.HeoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/heo")
public class HeoController {

    @Autowired
    private HeoService heoService;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @GetMapping
    public String listHeo(
            @RequestParam(defaultValue = "all") String tinhTrang,
            @RequestParam(required = false) String maHeo,
            @RequestParam(required = false) Long xuatXuId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // Xử lý mã heo: trim và chuyển thành null nếu rỗng
        String searchMaHeo = (maHeo != null && !maHeo.trim().isEmpty()) ? maHeo.trim() : null;

        Pageable pageable = PageRequest.of(page, size);
        Page<HeoDTO> heoPage = heoService.searchHeo(tinhTrang, searchMaHeo, xuatXuId, pageable);
        List<XuatXu> xuatXuList = xuatXuRepository.findAll();

        model.addAttribute("heoPage", heoPage);
        model.addAttribute("xuatXuList", xuatXuList);
        model.addAttribute("tinhTrang", tinhTrang);
        model.addAttribute("maHeo", maHeo);
        model.addAttribute("xuatXuId", xuatXuId);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        return "heo/list";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<HeoDTO> getHeo(@PathVariable Long id) {
        HeoDTO heo = heoService.getHeoById(id);
        return ResponseEntity.ok(heo);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> createHeo(@Valid @RequestBody HeoDTO heoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            HeoDTO savedHeo = heoService.saveHeo(heoDTO);
            return ResponseEntity.ok(savedHeo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateHeo(@PathVariable Long id, @Valid @RequestBody HeoDTO heoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            HeoDTO updatedHeo = heoService.updateHeo(id, heoDTO);
            return ResponseEntity.ok(updatedHeo);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteHeo(@PathVariable Long id) {
        heoService.deleteHeo(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    @ResponseBody
    public ResponseEntity<List<HeoDTO>> getTopHeo(@RequestParam(defaultValue = "10") int limit) {
        Page<HeoDTO> topHeo = heoService.getTopHeoByWeight(limit);
        return ResponseEntity.ok(topHeo.getContent());
    }
}