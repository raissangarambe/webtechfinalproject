package com.webtechproject.backend.repository;

import com.webtechproject.backend.model.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {

    // Custom query methods

    // Find reports for a specific user
    List<ProgressReport> findByUser_Uid(Long userId);

    // Find reports for a specific user on a specific date
    List<ProgressReport> findByUser_UidAndDate(Long userId, Date date);

    // Find reports within a date range for a specific user
    List<ProgressReport> findByUserUidAndDateBetween(Long userId, Date startDate, Date endDate);
}
