package handlers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    private static final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final NumberFormat paceFormatter = new DecimalFormat("#0.00");
    private static final NumberFormat distanceFormatter = new DecimalFormat("#0,000");

    public static String formatDate(LocalDateTime l) {
        return l.format(viewDateFormatter);
    }

    public static String formatTime(LocalDateTime l) {
        return l.format(viewStartTimeFormatter);
    }

    public static String formatDistance(double d) {
        if (d > 1000) return distanceFormatter.format(d);
        else return String.valueOf(Math.round(d));
    }

    public static String formatPace(double d) {
        return paceFormatter.format(d);
    }

    public static String formatWithoutDecimalPlaces(double d) {
        return String.valueOf(Math.round(d));
    }

    public static String formatTotalTimeSeconds(double d) {
        return String.format("%02d:%02d:%02d", (int) d / 3600, ((int) d % 3600) / 60, ((int) d % 60));
    }
}
