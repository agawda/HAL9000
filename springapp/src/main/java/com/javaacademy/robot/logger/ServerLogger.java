package com.javaacademy.robot.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

public class ServerLogger {
    public static final Logger logger = Logger.getLogger(ServerLogger.class.getName());
    private static Handler handler = null;
    public static final Level DEFAULT_LEVEL = Level.CONFIG;
    private static boolean isInitialized = false;

    /**
     * This method will initialize the logger with the path and a name of the logfile, level of the logging info and
     * a formatter which will modify the logged information.
     */
    public static void initializeLogger() {
        if (isInitialized) {
            return;
        }
        try {
            handler = MyFileHandler.createLoggerInstance("Server.log", false);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Could not create file", e);
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.addHandler(new StreamHandler(System.out, new SimpleFormatter()));
        logger.setLevel(Level.ALL);
        isInitialized = true;
    }

    private ServerLogger() {}

    static class MyFileHandler extends FileHandler {

        static MyFileHandler createLoggerInstance(String filename, boolean append) throws IOException {
            String folderName = "logs";
            Path logFilePath = Paths.get(folderName,filename);
            if (createLogFolder(folderName)) {
                return new MyFileHandler(logFilePath.toString(), append);
            } else {
                return new MyFileHandler(filename, append);
            }
        }

        private MyFileHandler(String pattern, boolean append) throws IOException {
            super(pattern, append);
        }

        private static boolean createLogFolder(String folderName) {
            File dir = new File(folderName);
            return dir.exists()|| dir.mkdir();
        }
    }
}
