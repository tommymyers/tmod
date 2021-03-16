package cc.tommymyers.tmod.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TLogger {

    private final String modId;
    private final String prefix;
    private Logger logger;

    public TLogger(String modId, String prefix) {
        this.modId = modId;
        this.prefix = prefix;
        logger = LogManager.getLogger(modId);
    }

    public void info(String message) {
        message = "["+prefix+"] "+message;
        logger.info(message);
    }

    public void error(String message, Throwable throwable) {
        message = "["+prefix+"] "+message;
        logger.error(message, throwable);
    }

}
