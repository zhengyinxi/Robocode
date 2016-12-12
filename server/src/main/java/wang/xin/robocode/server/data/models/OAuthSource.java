package wang.xin.robocode.server.data.models;

/**
 * Created by Xin on 11/30/2016.
 */
public enum OAuthSource {

    GitHub,
    Facebook,
    Microsoft;

    public static OAuthSource fromString(String name) {
        for (OAuthSource value : OAuthSource.values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }

        return null;
    }
}
