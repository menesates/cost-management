package com.menesates.costmanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CostManagementController {

    @RequestMapping("/")
    @ResponseBody
    public String homePage(){
        return "Welcome!";
    }
}
