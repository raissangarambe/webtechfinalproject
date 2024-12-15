package com.webtechproject.backend.service;
import com.webtechproject.backend.model.ProgressReport;
import com.webtechproject.backend.repository.ProgressReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProgressReportService {


    @Autowired
    private ProgressReportRepository progressReportRepository;

    public List<ProgressReport> getReportsByUser(Long userId) {
        return progressReportRepository.findByUser_Uid(userId);
    }

    public ProgressReport getReportForUserOnDate(Long userId, Date date) {
        return progressReportRepository.findByUser_UidAndDate(userId, date)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public List<ProgressReport> getReportsWithinRange(Long userId, Date startDate, Date endDate) {
        return progressReportRepository.findByUserUidAndDateBetween(userId, startDate, endDate);
    }
    public ProgressReport save(ProgressReport report) {
        // Set createdAt and modifiedAt fields
        Date now = new Date();
        report.setCreatedAt(now);
        report.setModifiedAt(now);

        // Save the progress report to the database
        return progressReportRepository.save(report);
    }
}
