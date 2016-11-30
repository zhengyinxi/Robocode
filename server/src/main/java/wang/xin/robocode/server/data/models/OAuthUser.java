package wang.xin.robocode.server.data.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Xin on 11/30/2016.
 */
@Entity
@IdClass(OAuthUserId.class)
public class OAuthUser {

    @Id
    @Enumerated(EnumType.STRING)
    private OAuthSource source;
    @Id
    private String principal;
    @MapsId
    @JoinColumn
    @ManyToOne
    private User user;
    private String name;

    public OAuthSource getSource() {
        return source;
    }

    public void setSource(OAuthSource source) {
        this.source = source;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
