package wang.xin.robocode.server.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.xin.robocode.server.data.models.OAuthSource;
import wang.xin.robocode.server.data.models.OAuthUser;
import wang.xin.robocode.server.data.models.OAuthUserId;

/**
 * Created by Xin on 11/30/2016.
 */
public interface OAuthUserRepository extends JpaRepository<OAuthUser, OAuthUserId> {

    OAuthUser findBySourceAndId(OAuthSource source, String id);
}
