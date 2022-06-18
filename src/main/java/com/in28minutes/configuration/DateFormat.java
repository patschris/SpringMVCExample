package com.in28minutes.configuration;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Class containing constants for the date format.
 *
 * @author Christos Patsouras
 */
@Component
public class DateFormat {

    /**
     *  The default timezone.
     */
    public static final String DATE_TIMEZONE = "Europe/Athens";

    /**
     *  The default date pattern.
     */
    public static final String DATE_PATTERN = "dd/MM/yyyy";

    /**
     * A <code>SimpleDateFormat</code> object using the default date pattern.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
}
