package wang.xin.robocode.server.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import wang.xin.robocode.server.data.repositories.ActiveWebSocketUserRepository;

import javax.annotation.PostConstruct;

/**
 * Created by Xin on 2016/10/6.
 */
@Configuration
public class DatabaseConfig {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ActiveWebSocketUserRepository activeWebSocketUserRepository;

    @PostConstruct
    public void clearActiveWebSocketUsers() {
        this.activeWebSocketUserRepository.deleteAllInBatch();
    }
}
