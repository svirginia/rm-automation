package com.jalasoft.automation.core.config;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class JsonConfigFileReader implements ConfigFileReader {
    private String filePath;
    private JSONObject fileJsonObject;
    private Logger log = Logger.getLogger(getClass());

    public JsonConfigFileReader(String filePath) {
        this.filePath = filePath;
        log.info(String.format("Reading config file from path:%s", filePath));
        this.Init();
    }

    private void Init() {
        try {
            FileReader reader = new FileReader(this.filePath);
            JSONParser jsonParser = new JSONParser();
            this.fileJsonObject = (JSONObject) jsonParser.parse(reader);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getConfigSection(String sectionName) {
        return this.getConfigSection(this.fileJsonObject, sectionName);
    }

    @Override
    public Map<String, String> getConfigSubSection(String sectionName, String subSectionName) {
        Map<String, String> result;
        JSONObject parentObject = this.getConfigSectionAsJson(sectionName);
        if (parentObject != null) {
            result = this.getConfigSection(parentObject, subSectionName);
        } else {
            result = new HashMap<String, String>();
        }
        return result;
    }

    @Override
    public String getConfigValue(String sectionName, String key) {
        String result = "";
        try {
            JSONObject section = (JSONObject) this.fileJsonObject.get(sectionName);
            result = (String) section.getOrDefault(key, "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isSectionDefined(Object sectionObject, String sectionName) {
        JSONObject jsonObject = (JSONObject) sectionObject;
        return jsonObject.containsKey(sectionName);
    }

    @Override
    public boolean isSectionDefined(String sectionName) {
        return this.isSectionDefined(this.fileJsonObject, sectionName);
    }

    @Override
    public boolean isSubSectionDefined(String parentSection, String subSection) {
        if (this.isSectionDefined(parentSection)) {
            JSONObject parentObject = this.getConfigSectionAsJson(parentSection);
            return this.isSectionDefined(parentObject, subSection);
        }
        return false;
    }

    private Map<String, String> getConfigSection(JSONObject parentObject, String sectionName) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            JSONObject subSection = (JSONObject) parentObject.get(sectionName);
            result.putAll(subSection);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public JSONObject getConfigSectionAsJson(String configSectionName) {
        JSONObject section = null;
        try {
            section = (JSONObject) this.fileJsonObject.get(configSectionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return section;
    }
}