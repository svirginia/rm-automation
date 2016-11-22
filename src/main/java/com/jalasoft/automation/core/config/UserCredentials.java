package com.jalasoft.automation.core.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class UserCredentials {
    public String email;
    public String userName;
    public String password;
    public List<String> credentialTypes;

    public UserCredentials(String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        credentialTypes = new ArrayList<>();
        credentialTypes.add(type);
    }

    public void addCredentialType(String type) {
        if (!this.credentialTypes.contains(type)) {
            this.credentialTypes.add(type);
        }
    }
}
