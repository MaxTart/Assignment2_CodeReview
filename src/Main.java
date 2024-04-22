import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.util.Arrays;


public class Main {
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Posts> postList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Integer input;
        do {
            System.out.println("1. Load input data");
            System.out.println("2. Check visibility");
            System.out.println("3. Retrieve posts");
            System.out.println("4. Search users by location");
            System.out.println("5. Exit:");
            input = s.nextInt();

            if (input == 1){
                loadData();
            } else if (input == 2) {
                // Function to check visibility of a post
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter a postID and a username");
                String postID = sc.nextLine();
                String username = sc.nextLine();
                if (checkVisibility(postID,username)){
                    System.out.println("Access Permitted");
                } else {
                    System.out.println("Access Denied");
                }
            } else if(input == 3){
                // Function to retrieve posts
                retrievePosts();
            } else if(input == 4){
                // Function to search users by location
                searchLocation();
            }
        } while (input != 5);
    }

//* Main Functions *//

// Asks for the file paths of the user and post information files, then loads the data from these files.
    static void loadData(){
        String userInfo, postInfo;
        Scanner prompt = new Scanner(System.in);
        System.out.print("Enter the name of the user info file: ");
        userInfo = prompt.nextLine();
        System.out.print("Enter the name of the post info file: ");
        postInfo = prompt.nextLine();
        // Adjusting file paths
        userInfo = "src/" + userInfo;
        postInfo = "src/" + postInfo;
        File userFile = new File(userInfo);
        File postsFile = new File(postInfo);
        String username, name, state, friendsList;
        String postID, userID, visibility;
        Pattern friendsPattern = Pattern.compile("[\\[\\],]");
        try {
           // Reading user data
            Scanner sc = new Scanner(userFile);
            sc.useDelimiter("[;\\n]");
            while(sc.hasNext()){
                username = sc.next();
                name = sc.next();
                state = sc.next();
                friendsList = sc.next();
                User newUser = new User(username, name, state);
                String[] preList =  friendsPattern.split(friendsList);
                newUser.friends = new ArrayList<String>(Arrays.asList(preList));

                userList.add(newUser);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            // Reading post data
            Scanner sc = new Scanner(postsFile);
            sc.useDelimiter("[;\\n]");
            while(sc.hasNext()){
                postID = sc.next();
                userID = sc.next();
                visibility = sc.next();

                Posts newPost = new Posts(postID, userID, visibility);

                postList.add(newPost);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

// Asks for a post ID and a username, then checks if the user can view the post. It outputs "Access Permitted" if access is granted and "Access Denied" otherwise.
    static boolean checkVisibility(String postID, String username){
        for (int i=0; i< postList.size(); i++){
            if (Objects.equals(postID, postList.get(i).getPostID())){
                if(Objects.equals(postList.get(i).getVisibility(), "public")){
                    return true;
                } else if (Objects.equals(postList.get(i).getVisibility(), "friend")){
                    for(int j = 0; j < userList.size(); j++){
                        if(Objects.equals(postList.get(i).getUserID(), userList.get(j).username)){
                            if(userList.get(j).friends.contains((username)) || Objects.equals(postList.get(i).getUserID(), username)){
                                return true;
                            } else{
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

// Asks for a username and retrieves all posts that the user can view, outputting the post IDs.
    static void retrievePosts(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> visiblePosts = new ArrayList<>();
        System.out.println("Enter a username");
        String username = sc.nextLine();
        for(Posts p : postList){
            if(checkVisibility(p.getPostID(), username)){
                visiblePosts.add(p.getPostID());
            }
        }
        System.out.println("Visible Posts: ");
        for(String s : visiblePosts){
            System.out.println(s);
        }
    }

// Asks for a state location, retrieves users matching the state, and outputs their display names.
    static void searchLocation(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> users = new ArrayList<>();
        System.out.println("Enter a state location");
        String state = sc.nextLine();

        for(User u : userList){
            if(Objects.equals(state, u.getState())){
                users.add(u.getDisplayname());
            }
        }
        for(String s : users){
            System.out.println(s);
        }
    }
}