package org.lukdt.bank_card_management.controller;

import org.lukdt.bank_card_management.dto.CardResponse;
import org.lukdt.bank_card_management.service.adminService.adminServiceInterface.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("{userId}/create")
    public ResponseEntity<CardResponse> createCard(@PathVariable Long userId) {
       CardResponse response = adminService.createCard(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
