package cc.tommymyers.tmod.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TLogger {

    private final String modId;
    private final String prefix;
    private final Logger logger;

    public TLogger(String modId, String prefix) {
        this.modId = modId;
        this.prefix = prefix;
        logger = LogManager.getLogger(modId);
    }

    private String applyPrefix(String message) {
        return "[" + prefix + "] " + message;
    }

    public void info(String message) {
        logger.info(applyPrefix(message));
    }

    public void error(String message) {
        logger.error(applyPrefix(message));
    }

    public void error(String message, Throwable throwable) {
        logger.error(applyPrefix(message), throwable);
    }

}
