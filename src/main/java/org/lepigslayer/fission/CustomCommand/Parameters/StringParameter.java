package org.lepigslayer.fission.CustomCommand.Parameters;

import org.lepigslayer.fission.CustomCommand.CommandParameter;

import java.util.List;
import java.util.Objects;

public class StringParameter extends CommandParameter<String> {
    private String errorFormat = "%s is too long, must be less than %d characters";

    private int textLimit = -1;

    public StringParameter(String name) {
        super(name);
    }

    public StringParameter(String name, int textLimit){
        super(name);
        this.textLimit = textLimit;
    }

    @Override
    public String validate(String input) {
        if(textLimit == -1 || textLimit > input.length())
            return null;

        return errorFormat.formatted(name,textLimit);
    }

    @Override
    public String getValue(String input) {
        return input;
    }
}
