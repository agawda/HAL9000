package com.javaacademy.robot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @author Anna Gawda
 * @since 22.08.2017
 */
@Controller
public class MainPageController {
    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("bookstores", Arrays.asList("Helion", "Google Books", "Meow"));
        return "../static/index";
    }
}
