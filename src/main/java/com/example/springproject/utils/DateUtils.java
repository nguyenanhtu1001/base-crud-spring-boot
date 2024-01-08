package com.example.springproject.utils;

import java.time.LocalDate;

/**
 * Utility class for handling date-related operations.
 */
public class DateUtils {

    /**
     * Gets the current date as a string.
     *
     * @return A string representation of the current date.
     */
    public static String getCurrentDateString() {
        return LocalDate.now().toString();
    }

    /**
     * Gets the current time in milliseconds.
     *
     * @return The current time in milliseconds since the epoch.
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}