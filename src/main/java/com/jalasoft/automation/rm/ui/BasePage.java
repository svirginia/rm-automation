package com.jalasoft.automation.rm.ui;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public abstract class BasePage extends RMUIElement {
    private PageTransporter pageTransporter;

    public BasePage() {
        this.pageTransporter = PageTransporter.getInstance();
    }

    public abstract boolean isOnPage();
}
