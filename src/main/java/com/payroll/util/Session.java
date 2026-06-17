public class Session {

    private static User loggedInUser = null;

    public static void setUser(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static String getRole() {
        if (loggedInUser != null) {
            return loggedInUser.getRole();
        }
        return null;
    }

    public static void logout() {
        loggedInUser = null;
        System.out.println("✅ Logged Out Successfully!");
    }
}
