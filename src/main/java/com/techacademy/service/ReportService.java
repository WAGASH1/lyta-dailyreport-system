package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    // ログインしているユーザーに関連する日報のみを取得
    public List<Report> findAllReports(UserDetail userDetail) {
        // ユーザーが管理者であるかどうかを確認
        if (userDetail.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            // 管理者の場合はすべての日報を取得
            return reportRepository.findAll();
        } else {
            // 一般ユーザーの場合はユーザーに関連する日報のみを取得
            Employee userEmployee = userDetail.getEmployee(); // 現在のユーザーの従業員情報を取得
            return reportRepository.findByEmployee(userEmployee);
        }
    }

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 日報保存
    @Transactional
    public ErrorKinds save(Report report,  Employee employee) {

        if (reportExists(report, employee)) {
            return ErrorKinds.DATECHECK_ERROR;
        }
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

    // 日報更新
    @Transactional
    public ErrorKinds update(Report report, Employee employee) {
        if (reportExists(report, employee)) {
            return ErrorKinds.DATECHECK_ERROR;
        }

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

    // 名前、日付の重複確認
    public boolean reportExists(Report report, Employee employee) {
        // 日報の日付と従業員を使用して既存の日報を検索
        List<Report> existingReports = reportRepository.findByEmployeeAndReportDate(employee, report.getReportDate());
        return !existingReports.isEmpty();
    }

    // 1件を検索
    public Report findById(int id) {
        String stringId = String.valueOf(id);
        Optional<Report> option = reportRepository.findById(stringId);
        Report report = option.orElse(null);
        return report;
    }

    // 従業員の検索
    @Transactional
    public List<Report> findByEmployee(Employee employee) {
        return reportRepository.findByEmployee(employee);
    }

}