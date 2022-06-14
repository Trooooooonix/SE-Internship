package handlers;

public class Logging {
    protected static boolean log = true;

    public static void print(String s){
        if (log)
            System.out.println(s);
    }
}
