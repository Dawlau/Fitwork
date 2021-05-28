package Models;

import java.io.Serializable;

public class Profile implements Serializable {

    private String UID;
    private String birthday;
    private String firstName;
    private String lastName;

    public Profile(){
        this.UID = "";
        this.birthday = "";
        this.firstName = "";
        this.lastName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Profile(String UID, String birthday, String firstName, String lastName) {
        this.UID = UID;
        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(){
        return firstName + " " + lastName;
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
