package com.jalasoft.automation.rm.config;

import com.jalasoft.automation.core.config.CredentialsConfig;
import com.jalasoft.automation.core.config.JsonConfigFileReader;
import com.jalasoft.automation.core.config.UserCredentials;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class UserCredentialsConfig implements CredentialsConfig {
    private static final String credentialsSection = "Credentials";
    private static final String userNameKey = "userName";
    private static final String passwordKey = "password";
    private Map<String, UserCredentials> credentialsByType;
    private Map<String, UserCredentials> credentialsMapByUserName;

    public UserCredentialsConfig(JsonConfigFileReader jsonConfigFileReader) {
        credentialsByType = new HashMap<String, UserCredentials>();
        credentialsMapByUserName = new HashMap<String, UserCredentials>();
        this.loadCredentials(jsonConfigFileReader);
    }

    @Override
    public UserCredentials getCredentialsByType(String userType) {
        return this.credentialsByType.getOrDefault(userType, new UserCredentials("", "", ""));
    }

    private void loadCredentials(JsonConfigFileReader jsonConfigFileReader) { //TODO review the logic
        JSONObject section = jsonConfigFileReader.getConfigSectionAsJson(credentialsSection);
        UserCredentials userCredentials;
        Set types = section.keySet();
        Iterator iterator = types.iterator();
        while (iterator.hasNext()) {
            String type = (String) iterator.next();
            JSONObject credentialEntry = (JSONObject) section.get(type);
            String userName = (String) credentialEntry.getOrDefault(userNameKey, "");
            String password = (String) credentialEntry.getOrDefault(passwordKey, "");
            if (this.credentialsMapByUserName.containsKey(userName)) {
                userCredentials = this.credentialsMapByUserName.get(userName);
                userCredentials.addCredentialType(type);
            } else {
                userCredentials = new UserCredentials(userName, password, type);
                this.credentialsMapByUserName.put(userName, userCredentials);
            }
            this.credentialsByType.put(type, userCredentials);
        }
    }
}
