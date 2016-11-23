package com.jalasoft.automation.steps;

import com.jalasoft.automation.rm.utils.AdminNavigationUtils;
import cucumber.api.java.en.And;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class NavigationSteps {
    @And("^I go to email servers page$")
    public void iGoToEmailServersPage() {
        AdminNavigationUtils.goToEmailServerPage();
    }
}