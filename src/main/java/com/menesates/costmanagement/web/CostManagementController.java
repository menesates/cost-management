package com.menesates.costmanagement.web;

import com.menesates.costmanagement.dao.BudgetRepository;
import com.menesates.costmanagement.dao.IncomeExpenseRepository;
import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.Budget;
import com.menesates.costmanagement.model.IncomeExpense;
import com.menesates.costmanagement.model.User;
import com.menesates.costmanagement.sevice.CostManagementService;
import com.menesates.costmanagement.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CostManagementController {

    private CostManagementService costManagementService;

    private UserService userService;

    @Autowired
    public void setCostManagementService(CostManagementService costManagementService) {
        this.costManagementService = costManagementService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView homePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        User user = userService.findUser(principal.getName());
        Budget budget = costManagementService.findBudget(user);
        List<IncomeExpense> incomes = costManagementService.findIncome(user);
        List<IncomeExpense> expenses = costManagementService.findExpense(user);

        modelAndView.addObject("user",user);
        modelAndView.addObject("budget",budget);
        modelAndView.addObject("incomes",incomes);
        modelAndView.addObject("expenses", expenses);
        modelAndView.addObject("newCost",new IncomeExpense());
        return modelAndView;
    }

    @RequestMapping(value="/login.html")
    public ModelAndView loginPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String remoteUser = request.getRemoteUser();
        if (remoteUser == null){
            mav.setViewName("login");
        }
        else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public ModelAndView registerPage(ModelAndView modelAndView){
        modelAndView.setViewName("register");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public ModelAndView registerSave(@ModelAttribute("user")@Valid User user, BindingResult bindingResult){
        ModelAndView mav = new ModelAndView();
        if (!bindingResult.hasErrors()){
            mav.setViewName("redirect:login.html?succes=true");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setEnabled(true);
            user.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword()));
            userService.createUser(user);
        }
        else {
            mav.setViewName("register");
        }
        return mav;
    }


}
