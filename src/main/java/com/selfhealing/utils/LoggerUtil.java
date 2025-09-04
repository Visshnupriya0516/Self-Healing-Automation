package com.selfhealing.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger("com.selfhealing");

    public static Logger getLogger() {
        return logger;
    }
}

