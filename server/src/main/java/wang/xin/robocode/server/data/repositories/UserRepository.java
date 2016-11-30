package wang.xin.robocode.server.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.xin.robocode.server.data.models.User;

/**
 * Created by Xin on 11/30/2016.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
