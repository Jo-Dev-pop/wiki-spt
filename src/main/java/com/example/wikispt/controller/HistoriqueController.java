package com.example.wikispt.controller;

import com.example.wikispt.service.HistoriqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/historique")
@RequiredArgsConstructor
public class HistoriqueController {

    private final HistoriqueService historiqueService;

    @GetMapping
    public String liste(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("pageHistorique", historiqueService.findAll(page, 15));
        model.addAttribute("content", "admin/fragments/historique");
        return "admin/dashboard";
    }
}