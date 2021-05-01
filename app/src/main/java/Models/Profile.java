package Models;

public class Profile {

    private String UID;
    private String birthday;
    private PublicProfile publicProfile;
    private PrivateProfile privateProfile;

    public Profile(){
        this.UID = "";
        this.birthday = "";
    }

    public Profile(String UID, String birthday) {
        this.UID = UID;
        this.birthday = birthday;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getBirthday() {
        return birthday;
    }

    public PublicProfile getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(PublicProfile publicProfile) {
        this.publicProfile = publicProfile;
    }

    public PrivateProfile getPrivateProfile() {
        return privateProfile;
    }

    public void setPrivateProfile(PrivateProfile privateProfile) {
        this.privateProfile = privateProfile;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
