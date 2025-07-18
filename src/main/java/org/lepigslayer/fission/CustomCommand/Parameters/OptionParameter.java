package org.lepigslayer.fission.CustomCommand.Parameters;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;
import org.lepigslayer.fission.CustomCommand.CommandParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionParameter extends CommandParameter<String> {
    private List<String> possibleOptions;

    public OptionParameter(String name) {
        super(name);
        this.possibleOptions = new ArrayList<>();
    }

    public void setOptions(List<String> options){
        this.possibleOptions = options;
    }

    @Override
    public String validate(String input) {
        if(!getPossibleOptions(input).isEmpty())
            return null;
        return "Not a valid option";
    }

    @Override
    public String getValue(String input) {
        return input;
    }

    private List<String> getPossibleOptions(String input){
        return possibleOptions.stream()
                .filter(o -> o.startsWith(input))
                .toList();
    }

    @Override
    public List<String> getOverridePrompt(String input) {
        return getPossibleOptions(input);
    }
}
