package wang.xin.robocode.server.web.controllers;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.webjars.RequireJS;
import wang.xin.robocode.server.services.SomeService;

import java.security.Principal;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by Xin on 2016/10/4.
 */
@RestController
@RequestMapping
public class HomeController {

    @Autowired
    private SomeService someService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
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

    @RequestMapping(value = "/webjars.js", produces = "application/javascript")
    public String webjarsjs(@Value("${server.context-path}") String contextPath) {
        return RequireJS.getSetupJavaScript(contextPath + "/webjars/");
    }

    @RequestMapping("/async")
    public Callable<Integer> async() throws Exception {
        Future<Integer> future = this.someService.doWork();
        return () -> future.get();
    }

    @GetMapping("/debug")
    public ModelAndView debug() {
        ModelAndView view = new ModelAndView("messages/list");
        view.addObject("messages", Lists.newArrayList());
        return view;
    }
}
