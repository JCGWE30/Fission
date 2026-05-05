package org.lepigslayer.fission.CustomCommand.Parameters;

import org.lepigslayer.fission.CustomCommand.CommandParameter;

public class BooleanParameter extends CommandParameter<Boolean> {
    public BooleanParameter(String name) {
        super(name);
    }

    @Override
    public String validate(String input) {
        if(input.equals("true")||input.equals("false"))
            return null;
        return "Invalid Value";
    }

    @Override
    public Boolean getValue(String input) {
        return input.equals("true");
    }
}
