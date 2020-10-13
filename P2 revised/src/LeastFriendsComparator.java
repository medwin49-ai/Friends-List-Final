import java.util.Comparator;

public class LeastFriendsComparator implements Comparator<User> {
    public int compare(User u1, User u2) {
        return u2.compareFriendsTo(u1);
    }
}
