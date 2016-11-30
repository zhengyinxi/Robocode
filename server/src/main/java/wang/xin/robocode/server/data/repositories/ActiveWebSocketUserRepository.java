package wang.xin.robocode.server.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wang.xin.robocode.server.data.models.ActiveWebSocketUser;

import java.util.List;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
public interface ActiveWebSocketUserRepository extends JpaRepository<ActiveWebSocketUser, String> {

    //@Query("select DISTINCT(u.username) from ActiveWebSocketUser u where u.username != ?#{principal?.username}")
    //List<String> findAllActiveUsers();
}
