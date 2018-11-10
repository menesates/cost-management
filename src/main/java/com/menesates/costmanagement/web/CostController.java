package com.menesates.costmanagement.web;

import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;
import com.menesates.costmanagement.sevice.CostManagementService;
import com.menesates.costmanagement.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Controller
public class CostController {

    private CostManagementService costManagementService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCostManagementService(CostManagementService costManagementService) {
        this.costManagementService = costManagementService;
    }

    @RequestMapping(value = "/newCost", method = RequestMethod.POST)
    public ModelAndView saveIncomeExpense(@ModelAttribute("newCost")@Valid IncomeExpense incomeExpense,
                                          Principal principal,
                                          BindingResult bindingResult){

        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()){
            User user = userService.findUser(principal.getName());
            incomeExpense.setUser(user);
            incomeExpense.setCurrencyCode(949);
            incomeExpense.setDate(new Date());
            costManagementService.addCost(incomeExpense);
            modelAndView.setViewName("redirect:/?newCost=true");
        }
        else {
            modelAndView.setViewName("redirect:/?newCost=false");
        }
        return modelAndView;
    }

}
