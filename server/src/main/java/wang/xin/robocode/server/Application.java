package wang.xin.robocode.server;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Created by Xin on 2016/10/5.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new SpringApplicationBuilder()
                .sources(Application.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
