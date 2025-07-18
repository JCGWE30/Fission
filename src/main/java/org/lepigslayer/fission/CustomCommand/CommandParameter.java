package org.lepigslayer.fission.CustomCommand;

import java.util.List;

public abstract class CommandParameter<T> {
    protected String name;

    boolean isOptional;
    int parameterIndex;

    Runnable updateRunnable;

    public CommandParameter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public void updateParameter(){
        if(updateRunnable != null)
            updateRunnable.run();
    }

    public abstract String validate(String input);

    public abstract T getValue(String input);

    public List<String> getOverridePrompt(String input){
        return null;
    }
}
