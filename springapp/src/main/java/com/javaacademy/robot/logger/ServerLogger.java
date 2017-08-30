package com.javaacademy.robot.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class ServerLogger {
    public static final Logger logger = Logger.getLogger(ServerLogger.class.getName());
    public static final Level DEFAULT_LEVEL = Level.CONFIG;
    private static boolean isInitialized = false;
    private static final String FILE_NAME = "Server.log";
    private static final boolean APPEND_TO_FILE = true;
    public static final String FOLDER_NAME = "serverLogs";

    /**
     * This method will initialize the logger with the path and a name of the logfile, level of the logging info and
     * a formatter which will modify the logged information.
     */
    public static void initializeLogger() {
        if (isInitialized) {
            return;
        }
        try {
            Handler loggerHandler = MyFileHandler.createLoggerInstance();
            loggerHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(loggerHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not create file", e);
        }
        isInitialized = true;
    }

    private ServerLogger() {
    }

    static class MyFileHandler extends FileHandler {

        private MyFileHandler(String pattern, boolean append) throws IOException {
            super(pattern, append);
        }

        static MyFileHandler createLoggerInstance() throws IOException {
            Path logFilePath = Paths.get(FOLDER_NAME, FILE_NAME);
            if (createLoggersFolder(FOLDER_NAME)) {
                return new MyFileHandler(logFilePath.toString(), APPEND_TO_FILE);
            } else {
                return new MyFileHandler(FILE_NAME, APPEND_TO_FILE);
            }
        }

        private static boolean createLoggersFolder(String folderName) {
            File dir = new File(folderName);
            return dir.exists() || dir.mkdir();
        }
    }
}
