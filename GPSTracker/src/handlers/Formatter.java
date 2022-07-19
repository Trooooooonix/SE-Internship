package handlers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is responsible for providing formatting methods for the applications' ui.
 */
public class Formatter {
    private static final DateTimeFormatter viewDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter viewStartTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final NumberFormat paceFormatter = new DecimalFormat("#0.00");
    private static final NumberFormat distanceFormatter = new DecimalFormat("#0,000");

    /**
     * This method formats a LocalDateTime to a string within this format: dd.MM.yyyy.
     *
     * @param l given variable for formatting
     * @return formatted string of input
     */
    public static String formatDate(LocalDateTime l) {
        return l.format(viewDateFormatter);
    }

    /**
     * This method formats a LocalDateTime to a string within this format: HH:mm.
     *
     * @param l given variable for formatting
     * @return formatted string of input
     */
    public static String formatTime(LocalDateTime l) {
        return l.format(viewStartTimeFormatter);
    }

    /**
     * This method formats a double to a string within this format: #0,000.
     *
     * @param d given variable for formatting
     * @return formatted string of input
     */
    public static String formatDistance(double d) {
        if (d > 1000) return distanceFormatter.format(d);
        else return String.valueOf(Math.round(d));
    }

    /**
     * This method formats a LocalDateTime to a string within this format: #0.00.
     *
     * @param d given variable for formatting
     * @return formatted string of input
     */
    public static String formatPace(double d) {
        return paceFormatter.format(d);
    }

    /**
     * This method formats a double to a string without decimal places.
     *
     * @param d given variable for formatting
     * @return formatted string of input
     */
    public static String formatWithoutDecimalPlaces(double d) {
        return String.valueOf(Math.round(d));
    }

    /**
     * This method formats a double to a string in this time format: HH:mm:ss
     *
     * @param d given variable for formatting
     * @return formatted string of input
     */
    public static String formatTotalTimeSeconds(double d) {
        return String.format("%02d:%02d:%02d", (int) d / 3600, ((int) d % 3600) / 60, ((int) d % 60));
    }
}
