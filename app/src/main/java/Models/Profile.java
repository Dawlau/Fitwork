package Models;

public class Profile {

    private String UID;
    private String birthday;

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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
