package nat.pink.base.model;

import com.facebook.stetho.json.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectLocation implements Serializable {
    @SerializedName("homeURL")
    @Expose
    private String homeURL;
    @SerializedName("changeURL")
    @Expose
    private String changeURL;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("lct")
    @Expose
    private String lct;

    public String getHomeURL() {
        return homeURL;
    }

    public void setHomeURL(String homeURL) {
        this.homeURL = homeURL;
    }

    public String getChangeURL() {
        return changeURL;
    }

    public void setChangeURL(String changeURL) {
        this.changeURL = changeURL;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLct() {
        return lct;
    }

    public void setLct(String lct) {
        this.lct = lct;
    }

}