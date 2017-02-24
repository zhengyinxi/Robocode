package wang.xin.robocode.server.data.models;

import com.google.common.base.Objects;

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
    private String id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuthUserId that = (OAuthUserId) o;
        return source == that.source &&
                Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source, id);
    }
}
