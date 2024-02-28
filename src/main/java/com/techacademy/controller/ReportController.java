package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 日報一覧画面
    @GetMapping
    public String list(Model model) {

        model.addAttribute("listSize", reportService.findAll().size());
        model.addAttribute("employeeList", reportService.findAll());

        return "reports/list";
    }

//    // 従業員詳細画面
//    @GetMapping(value = "/{code}/")
//    public String detail(@PathVariable String code, Model model) {
//
//        model.addAttribute("employee", employeeService.findByCode(code));
//        return "employees/detail";
//    }
//
//    // 従業員更新画面表示
//    @GetMapping(value = "/{code}/update")
//    public String edit(@PathVariable("code") String code, Model model) {
//        model.addAttribute("employee", employeeService.findByCode(code));
//
//        return "employees/update";
//    }
//
//    //従業員更新処理
//    @PostMapping(value = "/{code}/update")
//    public String postUser(@Validated Employee employee, BindingResult res, Model model) {
//
//        // 入力チェック
//        if (res.hasErrors()) {
//            return "employees/update";
//        }
//
//        // 論理削除を行った従業員番号を指定すると例外となるためtry~catchで対応
//        // (findByIdでは削除フラグがTRUEのデータが取得出来ないため)
//        try {
//            ErrorKinds result = employeeService.update(employee);
//
//            if (ErrorMessage.contains(result)) {
//                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//                return "employees/update";
//            }
//
//        } catch (DataIntegrityViolationException e) {
//            model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DUPLICATE_EXCEPTION_ERROR),
//                    ErrorMessage.getErrorValue(ErrorKinds.DUPLICATE_EXCEPTION_ERROR));
//            return "employees/update";
//        }
//
//        return "redirect:/employees";
//    }
//
    // 日報新規登録画面
    @GetMapping(value = "/add")
    public String create(@ModelAttribute Report report) {

        return "reports/new";
    }

//    // 従業員新規登録処理
//    @PostMapping(value = "/add")
//    public String add(@Validated Report report, BindingResult res, Model model) {
//
//
//        // 入力チェック
//        if (res.hasErrors()) {
//            return create(report);
//        }

        // 論理削除を行った従業員番号を指定すると例外となるためtry~catchで対応
        // (findByIdでは削除フラグがTRUEのデータが取得出来ないため)
//        try {
//            ErrorKinds result = ReportService.save(report);
//
//            if (ErrorMessage.contains(result)) {
//                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//                return create(employee);
//            }
//
//        } catch (DataIntegrityViolationException e) {
//            model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.DUPLICATE_EXCEPTION_ERROR),
//                    ErrorMessage.getErrorValue(ErrorKinds.DUPLICATE_EXCEPTION_ERROR));
//            return create(employee);
//        }
//
//        return "redirect:/employees";
//    }
//
//    // 従業員削除処理
//    @PostMapping(value = "/{code}/delete")
//    public String delete(@PathVariable String code, @AuthenticationPrincipal UserDetail userDetail, Model model) {
//
//        ErrorKinds result = employeeService.delete(code, userDetail);
//
//        if (ErrorMessage.contains(result)) {
//            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//            model.addAttribute("employee", employeeService.findByCode(code));
//            return detail(code, model);
//        }
//
//        return "redirect:/employees";
//    }

}

