package utils;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Util {
    private final static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("[EEEE]ha", Locale.ENGLISH);

    public static Map<String, LocalTime> extractTimeFromTimeRange(String dateTxt) {
        String[] trArr = dateTxt.replaceAll(" ", "").split("-");
        Map<String, LocalTime> result = new HashMap<>();
        result.put("from", parseTime(trArr[0]));
        result.put("to", parseTime(trArr[1]));
        return result;
    }

    public static LocalTime parseTime(String t) {
        return LocalTime.parse(t, formatter);
    }

    public static String timeToStr(LocalTime lt) {
        return lt.format(DateTimeFormatter.ofPattern("ha"));
    }

    public static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
