package com.javaacademy.crawler.common.logger;

import com.javaacademy.crawler.App;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.*;

public class AppLogger {
    public static final Logger logger = Logger.getLogger(AppLogger.class.getName());
    public static final Logger statistics = Logger.getLogger(App.class.getName());
    private static Handler handler = null;
    private static Handler statsHandler = null;
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
            handler = MyFileHandler.createLoggerInstance("logs", getFilenameWithSystemTime());
            statsHandler = MyFileHandler.createLoggerInstance("stats", getFilenameWithSystemTime());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not create file", e);
        }
        handler.setFormatter(new SimpleFormatter());
        statsHandler.setFormatter(new MyFormatter());
        logger.addHandler(handler);
        statistics.addHandler(statsHandler);
        logger.setLevel(Level.ALL);
        statistics.setLevel(Level.INFO);
        isInitialized = true;
        logger.log(Level.FINEST, "Logger initialized");
    }

    private AppLogger() {}

    public static void logAndAddStat(String s) {
        statistics.info(s);
        logger.log(DEFAULT_LEVEL, s);
    }

    static class MyFileHandler extends FileHandler {

        static MyFileHandler createLoggerInstance(String folderName, String filename) throws IOException {
            Path logFilePath = Paths.get(folderName,filename);
            if (createLogFolder(folderName)) {
                return new MyFileHandler(logFilePath.toString(), false);
            } else {
                return new MyFileHandler(filename, false);
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

    private static String getFilenameWithSystemTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm_dd-MM-yyyy");
        return "Crawler-" +dateTimeFormatter.format(LocalDateTime.now()) +".log";
    }

    static class MyFormatter extends Formatter {
        private static final DateFormat df = new SimpleDateFormat("dd-MM-YY hh:mm:ss");

        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            builder.append(df.format(new Date(record.getMillis()))).append(":");
            if(!formatMessage(record).equals("")) {
                builder.append("\n");
                builder.append(formatMessage(record));
            }
            builder.append("\n");
            return builder.toString();
        }
    }
}
