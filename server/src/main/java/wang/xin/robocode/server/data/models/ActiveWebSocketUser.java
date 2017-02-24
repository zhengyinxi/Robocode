package wang.xin.robocode.server.data.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by zhengyinxi on 2016/10/8.
 */
@Entity
public class ActiveWebSocketUser {

    @Id
    private String sessionId;
    private LocalDateTime connectTime;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User user;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getConnectTime() {
        return connectTime;
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
