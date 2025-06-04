package ru.pro.zakupki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/welcome")
public class MainController {
    @GetMapping
    public String Welcome() {
        return "ПРИВЕТ ЭТО ПРИЛОЖЕНИЕ ПО ЗАКУПКАМ,СПАСИБО ЗА РЕГИСТРАЦИЮ";
    }
}
