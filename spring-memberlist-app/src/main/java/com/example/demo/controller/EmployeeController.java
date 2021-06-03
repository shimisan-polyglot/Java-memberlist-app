package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeRepository repository;
//以下のrepository.ほにゃららの リポジトリは、全てemployeerepositoryの話です。
//    まずここでの命令は初めのページに対してリポジトリクラス(employee)の中身を丸ごと渡してあげるという命令です。
    @GetMapping("/")
    public String showList(Model model) {
        model.addAttribute("employeedayone", repository.findAll());
        return "index";
    }
//今度はここには、employeeに追加される情報の設定をするので、リポジトリではなく、
//    employeeのコントローラークラスを渡しています。
//    Employeeの右の名前なんでもよい htmlに影響しない
    
//    サイドバーの登録ボタンが押された際に遷移して、formというhtmlファイルを返す処理(その中にmodelクラスがふくまれている
    
    @GetMapping("/add")
    public String addEmployee(@ModelAttribute Employee employeehozon) {
        return "form";
    }
//ここのプロセスのemployeeの右の名前もなんでもよい
//    フォームが入力されたのちに、その値をモデルクラスに遷移する。処理
    @PostMapping("/process")
    public String process(@Validated @ModelAttribute Employee employeekatei,
            BindingResult result) {

        if (result.hasErrors()) {
            return "form";
        }
        repository.save(employeekatei);
        return "redirect:/";
    }

//    
//    
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        model.addAttribute("employee", repository.findById(id));
        return "form";
    }
    
    
//フォームで設定するリンク情報
    @GetMapping("/delete/{id}")
//    URLで受け取るパラメーターの型情報をPathVariableで記述します。
    public String deleteEmployee(@PathVariable Long id) {
//    	受け取った値に対して、削除処理をおこないます。
        repository.deleteById(id);
// 削除処理を終えて、トップページに帰りますよーという処理。
        return "redirect:/";
    }
}
