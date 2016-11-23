package com.jalasoft.automation.steps;

import com.jalasoft.automation.rm.utils.AdminNavigationUtils;
import cucumber.api.java.en.Given;

/**
 * Created by virginia sanabria on 11/21/2016.
 */
public class LoginSteps {

    @Given("^I logged with \"([^\"]*)\" credential in admin rm site$")
    public void iLoggedWithAccountInAdminRmSite(String credentialType) {
        AdminNavigationUtils.loggedInAdminByCredentialType(credentialType);
    }
}