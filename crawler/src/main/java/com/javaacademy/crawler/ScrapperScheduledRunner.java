package com.javaacademy.crawler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author devas
 * @author mprtcz
 * @since 24.08.17
 */
public class ScrapperScheduledRunner {

    private Timer timer = new Timer ();
    private TimerTask timerTask = new TimerTask () {
        @Override
        public void run () {
            // your code here...
        }
    };

    public ScrapperScheduledRunner() {
        timer.schedule (timerTask, 0l, (long)1000*60*60);
    }
}
