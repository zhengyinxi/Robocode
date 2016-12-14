package wang.xin.robocode.server.web.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

/**
 * Created by Xin on 2016/10/4.
 */
@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/debug")
    public ModelAndView debug() {
        ModelAndView view = new ModelAndView("messages/list");
        view.addObject("messages", Lists.newArrayList());
        return view;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
