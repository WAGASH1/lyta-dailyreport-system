package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.techacademy.constants.ErrorKinds;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ReportService(ReportRepository reportRepository, PasswordEncoder passwordEncoder) {
        this.reportRepository = reportRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 日報保存
    @Transactional
    public ErrorKinds save(Report report) {

        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }


    // 従業員一覧表示処理
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
//
//    // 1件を検索
//    public Employee findByCode(String code) {
//        // findByIdで検索
//        Optional<Employee> option = employeeRepository.findById(code);
//        // 取得できなかった場合はnullを返す
//        Employee employee = option.orElse(null);
//        return employee;
//    }
//


}
