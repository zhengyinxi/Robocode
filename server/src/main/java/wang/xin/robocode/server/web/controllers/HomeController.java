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

    @GetMapping("/messages")
    public ModelAndView list() {
        List<Message> messages = Lists.newArrayList();
        messages.add(new Message() {{
            this.setId(0L);
            this.setSummary("wtf");
            this.setText("wtf");
            this.setCreated(Calendar.getInstance());
        }});
        return new ModelAndView("messages/list", "messages", messages);
    }

    static class Message {

        private Long id;

        private String text;

        private String summary;

        private Calendar created;

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Calendar getCreated() {
            return this.created;
        }

        public void setCreated(Calendar created) {
            this.created = created;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getSummary() {
            return this.summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
