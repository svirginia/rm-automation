package com.jalasoft.automation.rm.utils;

import com.jalasoft.automation.core.config.UserCredentials;
import com.jalasoft.automation.rm.RMAutomationApp;
import com.jalasoft.automation.rm.config.RMConfig;
import com.jalasoft.automation.rm.ui.PageTransporter;
import com.jalasoft.automation.rm.ui.admin.AdminLoginPage;
import com.jalasoft.automation.rm.ui.admin.NavigationBar;
import org.apache.log4j.Logger;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class AdminNavigationUtils {
    private static Logger log = Logger.getLogger("AdminNavigationUtils");
    private static String currentUsername = "";

    public static void loggedInAdminByCredentialType(String credentialType) {
        log.info("Logged in Admin with \"" + credentialType + "\" credentials");
        RMAutomationApp.setupFramework();
        if (!isLoggedUserUsingCredentialsOfType(credentialType)) {
            log.info("User is not logged in with \"" + credentialType + "\" credentials, starting a new session with it");
            AdminLoginPage adminLoginPage = PageTransporter.getInstance().navigateToAdminLoginPage();
            adminLoginPage.login(credentialType);
            NavigationBar navigationBar = new NavigationBar();
        }
    }

    private static boolean isLoggedUserUsingCredentialsOfType(String credentialType) {
        boolean result = false;
        if (isWebDriverInsideAdminSite()) {
            if (!currentUsername.isEmpty()) {
                UserCredentials credentials = RMConfig.getInstance().getCredentialsConfig().getCredentialsByUsername(currentUsername);
                result = ((credentials != null) && (credentials.credentialTypes.contains(credentialType)));
            }
        }
        return result;
    }

    public static boolean isWebDriverInsideAdminSite() {
        String currentUrl = PageTransporter.getInstance().getCurrentUrl();
        return currentUrl.startsWith(PageTransporter.getInstance().getAdminBaseUrl()) &&
                (!currentUrl.equals(PageTransporter.getInstance().getAdminLoginPageUrl()));
    }

    public static void goToEmailServerPage() {
        log.info("Go to email servers page");
        String currentUrl = PageTransporter.getInstance().getCurrentUrl();
        if (!currentUrl.contains(PageTransporter.getInstance().getEmailServersPageUrl())) {
            log.info("User is not in email servers page");
            NavigationBar navigationBar = new NavigationBar();
            navigationBar.clickEmailServersMenu();
        }
    }
}