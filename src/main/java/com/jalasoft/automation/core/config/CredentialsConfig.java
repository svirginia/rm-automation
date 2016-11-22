package com.jalasoft.automation.core.config;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public interface CredentialsConfig {
    UserCredentials getCredentialsByType(String userType);
}