import java.util.ArrayList;
public class User {
    String username;
    String displayname;
    String state;
    ArrayList<String> friends = new ArrayList<String>();

    public User(String username, String displayname, String state) {
        this.username = username;
        this.displayname = displayname;
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", displayname='" + displayname + '\'' +
                ", state='" + state + '\'' +
                ", friends=" + friends +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }


}
