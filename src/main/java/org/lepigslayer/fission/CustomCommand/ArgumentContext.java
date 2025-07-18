package org.lepigslayer.fission.CustomCommand;

import org.lepigslayer.fission.CustomCommand.ArgumentSteps.ArgumentStep;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ArgumentContext {
    private boolean isFlag;
    private boolean isParameter;
    private boolean isIdentified;
    private CommandParameter<?> parameter;

    boolean shouldHalt;

    boolean hasError;
    String errorText;

    boolean hasExecuteError;
    String executeErrorText;

    boolean hasPrompt;
    List<String> promptText;

    boolean hasArgumentData;
    Map<Integer, String> argumentData;

    private String argument;

    public ArgumentContext(String argument) {
        this.argument = argument;
    }

    public ArgumentContext(String argument, Map<Integer, String> argumentData) {
        this.argument = argument;
        this.argumentData = argumentData;
        this.hasArgumentData = true;
    }

    public String getAnyErrorText(){
        if(hasError)
            return errorText;
        if(hasExecuteError)
            return executeErrorText;
        throw new IllegalArgumentException("No error text found");
    }

    public String getArgument() {
        return argument;
    }

    public CommandParameter<?> getParameter() {
        return parameter;
    }

    public void flag(CommandParameter<?> parameter) {
        this.isFlag = true;
        this.isIdentified = true;
        this.parameter = parameter;
    }

    public void param(CommandParameter<?> parameter) {
        this.isParameter = true;
        this.isIdentified = true;
        this.parameter = parameter;
    }

    public void error(String errorText) {
        this.hasError = true;
        this.errorText = errorText;
        this.shouldHalt = true;
    }

    public void executeError(String errorText) {
        this.hasExecuteError = true;
        this.executeErrorText = errorText;
    }

    public void prompt(String prompt){
        prompt(Collections.singletonList(prompt));
    }

    public void prompt(List<String> prompts){
        this.hasPrompt = true;
        this.promptText = prompts;
        this.shouldHalt = true;
    }

    public void store(int index, String value) {
        if(!hasArgumentData)
            return;
        argumentData.put(index, value);
    }


    public boolean isIdentified() {
        return isIdentified;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public boolean isParameter() {
        return isParameter;
    }
}
