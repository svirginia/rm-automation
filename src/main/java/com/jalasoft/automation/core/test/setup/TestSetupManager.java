package com.jalasoft.automation.core.test.setup;

import com.jalasoft.automation.core.test.annotation.AfterCucumberFeature;
import com.jalasoft.automation.core.test.annotation.AfterCucumberScenario;
import com.jalasoft.automation.core.test.annotation.BeforeCucumberFeature;
import com.jalasoft.automation.core.test.annotation.BeforeCucumberScenario;
import cucumber.api.Scenario;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by virginia sanabria on 11/22/2016.
 */
public class TestSetupManager {
    private static TestSetupManager testSetupManager;
    private Logger log = Logger.getLogger(getClass());
    private Map<String, String> featureNameToTagMap;
    private Map<String, Method> beforeFeatureMethodsMap;
    private Map<String, Method> afterFeatureMethodsMap;
    private Map<String, Method> beforeScenarioMethodsMap;
    private Map<String, Method> afterScenarioMethodsMap;
    private Map<String, Object> testSetupObjectsMap;
    private String featureInExecution;

    public TestSetupManager() {
        this.beforeFeatureMethodsMap = new HashMap<>();
        this.afterFeatureMethodsMap = new HashMap<>();
        this.testSetupObjectsMap = new HashMap<>();
        this.featureNameToTagMap = new HashMap<>();
        this.beforeScenarioMethodsMap = new HashMap<>();
        this.afterScenarioMethodsMap = new HashMap<>();
        this.featureInExecution = "";
    }

    public static TestSetupManager getInstance() {
        if (testSetupManager == null) {
            testSetupManager = new TestSetupManager();
            testSetupManager.init();
        }
        return testSetupManager;
    }

    private void init() {
        try {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.addUrls(ClasspathHelper.forPackage("com.jalasoft.automation.hooks.setupd"));
            builder.setScanners(new SubTypesScanner());
            builder.setScanners(new MethodAnnotationsScanner());
            Reflections reflections = new Reflections(builder);
            Set<Method> beforeMethods = reflections.getMethodsAnnotatedWith(BeforeCucumberFeature.class);
            Set<Method> afterMethods = reflections.getMethodsAnnotatedWith(AfterCucumberFeature.class);
            this.registerFeatureMethods(beforeMethods, afterMethods);
            beforeMethods = reflections.getMethodsAnnotatedWith(BeforeCucumberScenario.class);
            afterMethods = reflections.getMethodsAnnotatedWith(AfterCucumberScenario.class);
            this.registerScenarioMethods(beforeMethods, afterMethods);
        } catch (Exception ex) {
            log.error("Exception raised processing before and after feature", ex);
        }
    }

    private void registerFeatureMethods(Set<Method> beforeMethods, Set<Method> afterMethods) throws Exception {
        for (Method method : beforeMethods) {
            BeforeCucumberFeature beforeFeature = method.getAnnotation(BeforeCucumberFeature.class);
            this.beforeFeatureMethodsMap.put(beforeFeature.value(), method);
            this.registerTestSetupObject(method);
        }
        for (Method method : afterMethods) {
            AfterCucumberFeature afterFeature = method.getAnnotation(AfterCucumberFeature.class);
            this.afterFeatureMethodsMap.put(afterFeature.value(), method);
            this.registerTestSetupObject(method);
        }
    }

    private void registerScenarioMethods(Set<Method> beforeMethods, Set<Method> afterMethods) throws Exception {
        for (Method method : beforeMethods) {
            BeforeCucumberScenario beforeScenario = method.getAnnotation(BeforeCucumberScenario.class);
            this.beforeScenarioMethodsMap.put(beforeScenario.value(), method);
            this.registerTestSetupObject(method);
        }
        for (Method method : afterMethods) {
            AfterCucumberScenario afterScenario = method.getAnnotation(AfterCucumberScenario.class);
            this.afterScenarioMethodsMap.put(afterScenario.value(), method);
            this.registerTestSetupObject(method);
        }
    }

    private void registerTestSetupObject(Method method) throws Exception {
        String className = method.getDeclaringClass().getCanonicalName();
        if (!this.testSetupObjectsMap.containsKey(className)) {
            this.testSetupObjectsMap.put(className, method.getDeclaringClass().newInstance());
        }
    }

    private String getFeatureNameFromScenario(Scenario scenario) {
        String scenarioId = scenario.getId();
        String featureName = scenarioId.substring(0, scenarioId.indexOf(';'));
        return featureName;
    }

    private Object getTestSetupObjectFromMethod(Method method) {
        Object result = null;
        String className = method.getDeclaringClass().getCanonicalName();
        try {
            if (!this.testSetupObjectsMap.containsKey(className)) {
                result = method.getDeclaringClass().newInstance();
                this.testSetupObjectsMap.put(className, result);
            }
            result = this.testSetupObjectsMap.get(className);
        } catch (Exception ex) {
            this.log.error("Error getting setup object from method", ex);
        }
        return result;
    }

    private void checkBeforeFeatureMethods(Scenario scenario) throws Exception {
        String featureName = getFeatureNameFromScenario(scenario);
        for (String tag : scenario.getSourceTagNames()) {
            if (this.beforeFeatureMethodsMap.containsKey(tag)) {
                this.featureNameToTagMap.put(featureName, tag);
                Method beforeMethod = this.beforeFeatureMethodsMap.get(tag);
                beforeMethod.invoke(this.getTestSetupObjectFromMethod(beforeMethod));
            }
        }
    }

    private void checkBeforeScenarioMethods(Scenario scenario) throws Exception {
        for (String tag : scenario.getSourceTagNames()) {
            if (this.beforeScenarioMethodsMap.containsKey(tag)) {
                Method beforeMethod = this.beforeScenarioMethodsMap.get(tag);
                beforeMethod.invoke(this.getTestSetupObjectFromMethod(beforeMethod));
            }
        }
    }

    private void checkAfterFeatureMethods() throws Exception {
        if (!this.featureInExecution.isEmpty() && this.featureNameToTagMap.containsKey(this.featureInExecution)) {
            String featureTag = this.featureNameToTagMap.get(this.featureInExecution);
            if (featureTag != null && this.afterFeatureMethodsMap.containsKey(featureTag)) {
                Method afterMethod = this.afterFeatureMethodsMap.get(featureTag);
                afterMethod.invoke(this.getTestSetupObjectFromMethod(afterMethod));
            }
        }
    }

    private void checkAfterScenarioMethods(Scenario scenario) throws Exception {
        for (String tag : scenario.getSourceTagNames()) {
            if (this.afterScenarioMethodsMap.containsKey(tag)) {
                Method afterMethod = this.afterScenarioMethodsMap.get(tag);
                afterMethod.invoke(this.getTestSetupObjectFromMethod(afterMethod));
            }
        }
    }

    public void processBeforeCucumberScenario(Scenario scenario) {
        String featureName = getFeatureNameFromScenario(scenario);
        //if feature has changed check feature level methods : before and after
        if (!this.featureInExecution.equals(featureName)) {
            try {
                checkAfterFeatureMethods();
                checkBeforeFeatureMethods(scenario);
            } catch (Exception ex) {
                log.error("exception processing before scenario", ex);
            } finally {
                this.featureInExecution = featureName;
            }
        }
        //always check before scneario methods
        try {
            checkBeforeScenarioMethods(scenario);
        } catch (Exception ex) {
            log.error("exception processing before scenario", ex);
        } finally {
            this.featureInExecution = featureName;
        }
    }

    public void processAfterCucumberScenario(Scenario scenario) {
        try {
            this.checkAfterScenarioMethods(scenario);
        } catch (Exception ex) {
            log.error("exception processing scenario", ex);
        }
    }

    public void finalizeExecution() {
        try {
            this.checkAfterFeatureMethods();
        } catch (Exception ex) {
            log.error("exception processing scenario", ex);
        } finally {
            this.featureInExecution = "";
        }
    }
}