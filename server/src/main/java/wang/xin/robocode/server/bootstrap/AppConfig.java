package wang.xin.robocode.server.bootstrap;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Xin on 2016/10/5.
 */
@SpringBootApplication(scanBasePackages = "wang.xin.robocode.server")
public class AppConfig {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(AppConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
