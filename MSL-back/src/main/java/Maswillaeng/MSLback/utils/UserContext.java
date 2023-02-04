package Maswillaeng.MSLback.utils;

public class UserContext {
    public static ThreadLocal<Long> userId = new ThreadLocal<>();

    public static void remove() {
        if (userId != null)
            userId.remove();
    }
}
