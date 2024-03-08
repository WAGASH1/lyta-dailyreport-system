package com.techacademy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 日報保存
    @Transactional
    public ErrorKinds save(Report report, Employee employee) {

//        Report existingReport = reportRepository.findByReportDate(report.getReportDate());
//
//        // 既存の日報が見つかった場合はエラーを返す
//        if (existingReport != null) {
//            return ErrorKinds.DATECHECK_ERROR;
//        }

        report.setEmployee(employee);
        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }

    // 日報削除
    @Transactional
    public ErrorKinds delete(int id, UserDetail userDetail) {
        Report report = findById(id);

        LocalDateTime now = LocalDateTime.now();
        report.setUpdatedAt(now);
        report.setDeleteFlg(true);

        return ErrorKinds.SUCCESS;
    }

    @Transactional // 日報更新
    public ErrorKinds update(Report report, Employee employee) {

        report.setEmployee(employee);
        report.setDeleteFlg(false);
        LocalDateTime now = LocalDateTime.now();
        report.setUpdatedAt(now);

        Report newCreat = findById(report.getId());
        report.setCreatedAt(newCreat.getCreatedAt());

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }

    // 従業員一覧表示処理
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    // 日付を使用して日報を検索
    @Transactional(readOnly = true)
    public Report findByReportDate(LocalDate reportDate) {
        return reportRepository.findByReportDate(reportDate);
    }

    // 1件を検索
    public Report findById(int id) {
        String stringId = String.valueOf(id);
        Optional<Report> option = reportRepository.findById(stringId);
        Report report = option.orElse(null);
        return report;
    }

}