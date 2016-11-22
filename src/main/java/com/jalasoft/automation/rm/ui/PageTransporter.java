package com.jalasoft.automation.rm.ui;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class PageTransporter {
    private static PageTransporter instance;

    public static PageTransporter getInstance() {
        if (instance == null) {
            instance = new PageTransporter();
        }
        return instance;
    }
}
