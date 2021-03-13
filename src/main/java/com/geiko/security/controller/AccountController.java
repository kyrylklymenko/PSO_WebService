package com.geiko.security.controller;

import com.geiko.security.model.User;
import com.geiko.security.service.UserService;
import com.geiko.view.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerNewUser(@Valid User user,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) throws MessagingException, InterruptedException {
        if (bindingResult.hasErrors()){
            return "registration";
        }
        if (userService.isUserExists(user.getEmail())){
            bindingResult.rejectValue("email", "error.user", "Пользователь с такой почтой уже зарегистрирован!");
            return "registration";
        }
        String baseUrl = request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
        userService.registerNewUser(user, baseUrl);
        redirectAttributes.addFlashAttribute("page", Pages.REGISTER);
        return "redirect:/index";
    }

    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public String activateAccount(@RequestParam("key") String key,
                                  RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("page", Pages.ACTIVATION);
        String url = !userService.isAccountActivated(key) ? userService.activateAccount(key) ? "redirect:/index" : "redirect:/error" : "redirect:/error";
        return url;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }
}
