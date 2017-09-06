package com.javaacademy.robot.logger;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerLoggerTest {

    @Test
    public void testInitialization() {
        assertFalse(ServerLogger.isInitialized);
        ServerLogger.initializeLogger();
        assertTrue(ServerLogger.isInitialized);
        ServerLogger.initializeLogger();
    }
}