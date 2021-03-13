package com.geiko.controller;

import com.geiko.view.Pages;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @RequestMapping(value = {"/index","/"}, method = RequestMethod.GET)
    public ModelAndView home(Model model){
        ModelAndView mav = new ModelAndView("index");
        if (model.asMap().get("page") != null) {
            Pages page = (Pages) model.asMap().get("page");
            mav.addObject(page.getViewCode(), true);
        }
        return mav;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String welcome(){
        return "user";
    }


}
