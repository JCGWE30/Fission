package org.lepigslayer.fission.CustomCommand.ArgumentSteps;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;
import org.lepigslayer.fission.CustomCommand.CommandParameter;

import java.util.List;

public class ParameterIdentifierStep implements ArgumentStep{
    private List<CommandParameter<?>> parameters;
    private int parameterIndex;
    private int optionalParameterIndex;

    public ParameterIdentifierStep(List<CommandParameter<?>> parameters){
        this.parameters = parameters;
        for (CommandParameter<?> parameter : parameters) {
            if(parameter.isOptional())
                break;
            optionalParameterIndex++;
        }
    }

    @Override
    public void process(ArgumentContext context) {
        if(parameterIndex+1 < optionalParameterIndex && parameterIndex+1 < parameters.size())
            context.executeError("Missing required parameters");

        if(context.isIdentified())
            return;

        if(parameterIndex >= parameters.size())
            return;

        context.param(parameters.get(parameterIndex++));
    }

    @Override
    public void reset() {
        this.parameterIndex = 0;
    }
}
