package handlers;

import java.time.LocalDateTime;

public class Logging {
    protected static boolean log = false;

    Logging() {
    }

    public static void print(String s) {
        if (log) System.out.println(LocalDateTime.now() + ": " + s);

    }
}
