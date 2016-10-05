package wang.xin.robocode.server.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xin on 2016/10/4.
 */
@RestController
@RequestMapping("")
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/ping")
    public String ping() {
        return "pong";
    }
}
