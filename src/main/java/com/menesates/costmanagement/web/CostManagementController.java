package com.menesates.costmanagement.web;

import com.menesates.costmanagement.model.Authority;
import com.menesates.costmanagement.model.User;
import com.menesates.costmanagement.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CostManagementController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value="/login.html")
    public ModelAndView loginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("user", new User());
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
            mav.setViewName("registerSucces");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setEnabled(true);
            user.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword()));
            userService.createUser(user);
        }
        else {
            mav.setViewName("register");
        }
        System.out.println(user);
        return mav;
    }


}
