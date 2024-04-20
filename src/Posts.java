public class Posts {
    String postID;
    String userID;
    String visibility;

    @Override
    public String toString() {
        return "Posts{" +
                "postID='" + postID + '\'' +
                ", userID='" + userID + '\'' +
                ", visibility='" + visibility + '\'' +
                '}';
    }

    public Posts(String postID, String userID, String visibility) {
        this.postID = postID;
        this.userID = userID;
        this.visibility = visibility;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
