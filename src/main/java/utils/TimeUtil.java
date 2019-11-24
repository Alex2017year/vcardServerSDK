package utils;

public class TimeUtil {
    static final long ONE_SECOND = 1000L;
    public static long toSecs(long timeInMillis) {
        return (long)Math.ceil((double)timeInMillis / ONE_SECOND);
    }

    public static int getCurrentSec() {
        long seconds = toSecs(System.currentTimeMillis());
        if (seconds > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else
            return (int) seconds;
    }
}
