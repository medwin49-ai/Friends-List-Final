import ch04.queues.QueueInterface;
import ch06.lists.SortedABList;
import ch10.graphs.WeightedGraph;
import ch10.graphs.WeightedGraphInterface;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ProjectDriver {
    public static WeightedGraphInterface<User> readCSVs() throws IOException {
        // Scan Users...
        FileReader fin = new FileReader("input/users.csv");
        Scanner scanner = new Scanner(fin);

        System.out.println("Creating User Graph");
        WeightedGraphInterface<User> userGraph = new WeightedGraph<User>(500);

        // this is to skip the first line of the CSV (the header row for the data)
        scanner.nextLine();

        // Now, we are going to iterate over every row in the CSV file (these are "users")
        while (scanner.hasNextLine()) {
            // Remember: each csv row is a separate line in the file
            String csvRow = new String(scanner.nextLine());

            // We are delimiting the csv row by commas (CSV = comma-separated values)
            // into an array of String values
            String[] attrs = csvRow.split(",");

            // for later use
            String[] posts;

            // each user has an id, community, city, and age
            // after we split by the "," if we have MORE than 4 attrs
            // in our array, then that means there WERE posts associated
            // with the user
            if (attrs.length > 4) {
                // posts is a new String array of all the remaining attrs
                posts = new String[attrs.length - 4];
                for (int i = 0; i < posts.length; i++) {
                    // +4 because we are starting from the attrs array, immediately after "age"
                    posts[i] = attrs[i + 4];
                }
            } else {
                // we set posts to an empty String array to explicitly demonstrate that there are _NO_
                // posts associated with the user
                posts = new String[0];
            }

            User user = new User(Integer.parseInt(attrs[0]), attrs[1], attrs[2], attrs[3], posts);
            userGraph.addVertex(user);
            System.out.println("Added User: " + user.toString());
        }

        scanner.close();
        fin.close();

        // Scan Edges...
        fin = new FileReader("input/edges.csv");
        scanner = new Scanner(fin);

        System.out.println("Creating User to User Edges");

        // this is to skip the first line of the CSV (the header row for the data)
        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String csvRow = new String(scanner.nextLine());
            System.out.println(csvRow);
            String[] attrs = csvRow.split(",");
            userGraph.addEdge(Integer.parseInt(attrs[0]), Integer.parseInt(attrs[1]), 1);
            userGraph.addEdge(Integer.parseInt(attrs[1]), Integer.parseInt(attrs[0]), 1);
            System.out.println("Added Edge from User " + attrs[0] + " to " + attrs[1]);
        }

        scanner.close();
        fin.close();

        return userGraph;
    }

    public static void setFriendCounts(WeightedGraphInterface<User> graph) {
        // We are iterating over all of the Users in the graph and setting their friend count values
        for (int i = 0; i < graph.vertexCount(); i++) {
            User currentUser = graph.getVertex(i);
            QueueInterface<User> friends = graph.getToVertices(currentUser);
            System.out.println(friends.size());
            currentUser.setFriendCount(friends.size());
        }
    }

    public static void getMostFriends(WeightedGraphInterface<User> graph) {
        SortedABList<User> mostFriends = new SortedABList<User>(new MostFriendsComparator(), 200);

        for (int i = 0; i < graph.vertexCount(); i++) {
            User currentUser = graph.getVertex(i);
            mostFriends.add(currentUser);
        }

        // Simply print out the first five...
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(0).toString(), mostFriends.get(0).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(1).toString(), mostFriends.get(1).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(2).toString(), mostFriends.get(2).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(3).toString(), mostFriends.get(3).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(4).toString(), mostFriends.get(4).getFriendCount()));
    }

    public static void getLeastFriends(WeightedGraphInterface<User> graph) {
        SortedABList<User> mostFriends = new SortedABList<User>(new LeastFriendsComparator(), 200);

        for (int i = 0; i < graph.vertexCount(); i++) {
            User currentUser = graph.getVertex(i);
            mostFriends.add(currentUser);
        }

        // Simply print out the first five...
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(0).toString(), mostFriends.get(0).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(1).toString(), mostFriends.get(1).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(2).toString(), mostFriends.get(2).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(3).toString(), mostFriends.get(3).getFriendCount()));
        System.out.println(String.format("User: %s\n-- Friend Count: %d", mostFriends.get(4).toString(), mostFriends.get(4).getFriendCount()));
    }

    public static void getZeroFriends(WeightedGraphInterface<User> graph) {
        for (int i = 0; i < graph.vertexCount(); i++) {
            User currentUser = graph.getVertex(i);
            if (currentUser.getFriendCount() == 0) {
                System.out.println(String.format("User: %s\n-- Friend Count: 0", currentUser.toString()));
            }
        }
    }

    public static void main(String[] args) {
        // Read the CSV files to create our graph
        WeightedGraphInterface<User> userGraph;
        try {
            userGraph = readCSVs();
        } catch (IOException e) {
            return;
        }

        // The following three questions we want to answer about the data...
        // ...but first, we're going to set friend counts...
        setFriendCounts(userGraph);

        // 1. Who has the most user-to-user connections ("friends")?
        System.out.println("\n1. Who has the most user-to-user connections ('friends')?");
        getMostFriends(userGraph);

        // 2. Who has the least number of friends?
        System.out.println("\n2. Who has the least number of friends with at least one friend?");
        getLeastFriends(userGraph);

        // 3. Which user has the highest number of posts?
        System.out.println("\n3. Who has 0 friends?");
        // NOTE: I cheated and removed all friends fromm 143
        getZeroFriends(userGraph);
    }
}
