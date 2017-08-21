package com.javaacademy.robot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * @author Anna Gawda
 * @since 21.08.2017
 */
@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World")
                                    String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("exampleBooks", Arrays.asList("Advanced Java", "Clean Code", "Effective Java"));
        return "../static/templates/greeting";
    }
}
