import java.util.Arrays;

public class User {
    private int id;
    private String community;
    private String city;
    private String age;
    private String[] posts;
    private int friendCount;

    public User(int id, String community, String city, String age, String[] posts) {
        this.id = id;
        this.community = community;
        this.city = city;
        this.age = age;
        this.posts = posts;
    }

    public void setFriendCount(int count) {
        this.friendCount = count;
    }

    public int getFriendCount() {
        return this.friendCount;
    }

    public int compareFriendsTo(User otherUser) {
        return otherUser.getFriendCount() - this.getFriendCount();
    }

    public String toString() {
        String postsStr = Arrays.toString(this.posts);
        return String.format("%d - Community: %s, City: %s, Age: %s, Posts: %s", this.id, this.community, this.city, this.age, postsStr);
    }
}
