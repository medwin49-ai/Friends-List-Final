import java.util.Comparator;

public class MostFriendsComparator implements Comparator<User> {
    public int compare(User u1, User u2) {
        return u1.compareFriendsTo(u2);
    }
}