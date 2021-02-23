package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestUtils {
    public static final long WAIT = 30;

    public Logger log()
    {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }
}
