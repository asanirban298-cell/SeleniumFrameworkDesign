package anirbansarkar.Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	static {
		System.setProperty("log4j2.configurationFile",
				System.getProperty("user.dir") + "//src//main//java//anirbansarkar//resources//log4j2.xml");
	}

	public static final Logger logger = LogManager.getLogger(Log.class);

	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}
}
