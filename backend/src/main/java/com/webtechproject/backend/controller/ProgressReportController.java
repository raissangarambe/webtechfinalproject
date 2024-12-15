package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.ProgressReport;
import com.webtechproject.backend.model.User;
import com.webtechproject.backend.service.ProgressReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/progress-reports")
public class ProgressReportController {

    @Autowired
    private ProgressReportService progressReportService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressReport>> getReportsByUser(@PathVariable Long userId) {
        List<ProgressReport> reports = progressReportService.getReportsByUser(userId);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<ProgressReport> getReportForUserOnDate(@PathVariable Long userId, @PathVariable Date date) {
        try {
            ProgressReport report = progressReportService.getReportForUserOnDate(userId, date);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<ProgressReport>> getReportsWithinRange(
            @PathVariable Long userId,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate) {
        List<ProgressReport> reports = progressReportService.getReportsWithinRange(userId, startDate, endDate);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ProgressReport> createProgressReport(@PathVariable Long userId, @RequestBody ProgressReport report) {
        report.setUser(new User()); // Assign the correct user instance
        ProgressReport savedReport = progressReportService.save(report);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }
}
