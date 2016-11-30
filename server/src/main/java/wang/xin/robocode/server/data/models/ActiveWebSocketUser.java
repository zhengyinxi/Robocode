package wang.xin.robocode.server.data.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Entity
public class ActiveWebSocketUser {

    @Id
    private String sessionId;
    private LocalDateTime connectTime;
    @MapsId
    @JoinColumn
    @ManyToOne
    private User user;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getConnectTime() {
        return this.connectTime;
    }

    public void setConnectTime(LocalDateTime connectTime) {
        this.connectTime = connectTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
