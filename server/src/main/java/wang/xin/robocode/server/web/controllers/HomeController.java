package wang.xin.robocode.server.web.controllers;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xin on 2016/10/4.
 */
@RestController
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/debug")
    public Map debug() {
        Map map = Maps.newHashMap();
        map.put("a", 1);
        map.put("null", null);
        return map;
    }
}
