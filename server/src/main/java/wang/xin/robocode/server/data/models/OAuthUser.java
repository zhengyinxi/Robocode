package wang.xin.robocode.server.data.models;

import javax.persistence.*;

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
    private String id;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne
    private User user;
    private String name;

    public OAuthSource getSource() {
        return source;
    }

    public void setSource(OAuthSource source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
