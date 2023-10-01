package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.AppointmentRequest;
import com.se.kltn.spamanagement.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @Operation(summary = "get list appointment")
    public ResponseEntity<Object> getListAppointment(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                                     @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok().body(this.appointmentService.getAllAppointment(page, size));
    }

    @PostMapping
    @Operation(summary = "create appointment")
    public ResponseEntity<Object> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok().body(this.appointmentService.createAppointment(appointmentRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "update appointment")
    public ResponseEntity<Object> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest) {
        return ResponseEntity.ok().body(this.appointmentService.updateAppointment(id, appointmentRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete appointment")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        this.appointmentService.deleteAppointment(id);
        return ResponseEntity.ok().body("Appointment deleted");
    }
}
