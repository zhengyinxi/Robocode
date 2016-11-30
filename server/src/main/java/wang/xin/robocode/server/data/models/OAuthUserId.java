package wang.xin.robocode.server.data.models;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Created by Xin on 11/30/2016.
 */
@Embeddable
public class OAuthUserId implements Serializable {

    @Enumerated(EnumType.STRING)
    private OAuthSource source;
    private String principal;

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
}
