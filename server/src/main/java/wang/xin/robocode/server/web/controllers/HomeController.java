package wang.xin.robocode.server.web.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Xin on 2016/10/4.
 */
@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/")
    /*public String home() {
        return "Hello World!";
    }*/
    public ModelAndView home() {
        return new ModelAndView("oauth2");
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/debug")
    public Map debug() {
        Map map = Maps.newHashMap();
        map.put("a", 1);
        map.put("null", null);
        return map;
    }


}
