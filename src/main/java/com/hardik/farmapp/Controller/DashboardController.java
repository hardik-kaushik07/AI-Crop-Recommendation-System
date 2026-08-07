package com.hardik.farmapp.Controller;

import com.hardik.farmapp.DTO.DashboardStatus;
import com.hardik.farmapp.Service.DashboardStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private  final DashboardStatusService dashboardStatusService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/stats")
    public DashboardStatus stats(Authentication authentication){

        return dashboardStatusService.getDashboardStats(authentication);

    }
}
