package com.example.masterdetailtable.controller;

import com.example.masterdetailtable.model.Master;
import com.example.masterdetailtable.model.Detail;
import com.example.masterdetailtable.service.MasterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/master-detail")
public class MasterDetailController {

    @Autowired
    private MasterDetailService service;

    // Главная страница со списком мастер-записей
    @GetMapping
    public String showMasterTable(Model model) {
        model.addAttribute("masters", service.getAllMasters());
        model.addAttribute("newMaster", new Master());
        return "master-table";
    }

    // Добавление новой мастер-записи
    @PostMapping("/master")
    public String addMaster(@ModelAttribute Master master) {
        service.saveMaster(master);
        return "redirect:/master-detail";
    }

    // Удаление мастер-записи
    @PostMapping("/master/{id}/delete")
    public String deleteMaster(@PathVariable Long id) {
        service.deleteMaster(id);
        return "redirect:/master-detail";
    }

    // Просмотр деталей для мастер-записи
    @GetMapping("/master/{id}/details")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Master> master = service.getMasterById(id);
        if (master.isPresent()) {
            model.addAttribute("master", master.get());
            model.addAttribute("details", service.getDetailsByMasterId(id));
            model.addAttribute("newDetail", new Detail());
            return "detail-table";
        }
        return "redirect:/master-detail";
    }

    // Добавление детали к мастер-записи
    @PostMapping("/master/{masterId}/detail")
    public String addDetail(@PathVariable Long masterId, @ModelAttribute Detail detail) {
        service.addDetailToMaster(masterId, detail);
        return "redirect:/master-detail/master/" + masterId + "/details";
    }

    // Удаление детали
    @PostMapping("/detail/{id}/delete")
    public String deleteDetail(@PathVariable Long id) {
        Optional<Detail> detail = service.getDetailById(id);
        if (detail.isPresent()) {
            Long masterId = detail.get().getMaster().getId();
            service.deleteDetail(id);
            return "redirect:/master-detail/master/" + masterId + "/details";
        }
        return "redirect:/master-detail";
    }
}
