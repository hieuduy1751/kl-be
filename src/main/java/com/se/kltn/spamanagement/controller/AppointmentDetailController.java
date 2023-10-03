package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.AppointmentDetailRequest;
import com.se.kltn.spamanagement.service.AppointmentDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment-detail")
public class AppointmentDetailController {

    private final AppointmentDetailService appointmentDetailService;

    @Autowired
    public AppointmentDetailController(AppointmentDetailService appointmentDetailService) {
        this.appointmentDetailService = appointmentDetailService;
    }

    @GetMapping
    @Operation(summary = "get list appointment detail")
    public ResponseEntity<Object> getListAppointmentDetail(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                           @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.appointmentDetailService.getListAppointmentDetail(page, size));
    }

    @PostMapping
    @Operation(summary = "create appointment detail")
    public ResponseEntity<Object> createAppointmentDetail(@RequestParam("appointmentId") Long appointmentId,
                                                          @RequestParam("customerId") Long customerId,
                                                          @RequestBody AppointmentDetailRequest appointmentDetailRequest) {
        return ResponseEntity.ok().body(this.appointmentDetailService.createAppointmentDetail(customerId, appointmentId, appointmentDetailRequest));
    }

    @PutMapping
    @Operation(summary = "update appointment detail")
    public ResponseEntity<Object> updateAppointmentDetail(@RequestParam("appointmentId") Long appointmentId,
                                                          @RequestParam("customerId") Long customerId,
                                                          @RequestBody AppointmentDetailRequest appointmentDetailRequest) {
        return ResponseEntity.ok().body(this.appointmentDetailService.updateAppointmentDetail(customerId, appointmentId, appointmentDetailRequest));
    }

    @DeleteMapping
    @Operation(summary = "delete appointment detail")
    public ResponseEntity<String> deleteAppointmentDetail(@RequestParam("customerId") Long customerId,
                                                          @RequestParam("appointmentId") Long appointmentId) {
        this.appointmentDetailService.deleteAppointmentDetail(customerId, appointmentId);
        return ResponseEntity.ok().body("Appointment detail deleted");
    }
}
