package org.lepigslayer.fission.CustomCommand;

import org.lepigslayer.fission.CustomCommand.ArgumentSteps.ArgumentStep;
import org.lepigslayer.fission.CustomCommand.ArgumentSteps.ArgumentValidatorStep;
import org.lepigslayer.fission.CustomCommand.ArgumentSteps.FlagIdentifierStep;
import org.lepigslayer.fission.CustomCommand.ArgumentSteps.ParameterIdentifierStep;

import java.util.*;
import java.util.function.Consumer;

public abstract class CustomCommand {
    private int dataOrderSize;
    Map<Integer,CommandParameter<?>> dataOrder;

    List<CommandParameter<?>> parameters;
    Map<String, CommandParameter<?>> flags;

    public CustomCommand() {
        dataOrder = new HashMap<>();
        parameters = new ArrayList<>();
        flags = new HashMap<>();
    }

    protected final CustomCommand registerParameter(CommandParameter<?> parameter) {
        return registerParameter(parameter, null);
    }

    protected final CustomCommand registerParameter(CommandParameter<?> parameter, Runnable updateRunnable) {
        throwIfOptional();
        parameter.parameterIndex = dataOrderSize++;

        parameters.add(parameter);
        dataOrder.put(parameter.parameterIndex, parameter);

        parameter.updateRunnable = updateRunnable;
        return this;
    }

    protected final CustomCommand registerOptional(CommandParameter<?> parameter) {
        return registerOptional(parameter, null);
    }

    protected final CustomCommand registerOptional(CommandParameter<?> parameter, Runnable updateRunnable) {
        parameter.isOptional = true;
        return registerParameter(parameter,updateRunnable);
    }

    protected final CustomCommand registerFlag(String key, CommandParameter<?> parameter) {
        return registerFlag(key, parameter, null);
    }

    protected final CustomCommand registerFlag(String key, CommandParameter<?> parameter, Runnable updateRunnable) {
        parameter.parameterIndex = dataOrderSize++;

        flags.put(key, parameter);
        dataOrder.put(parameter.parameterIndex, parameter);

        parameter.updateRunnable = updateRunnable;
        return this;
    }

    public abstract void executeCommand(CommandData data);

    private void throwIfOptional(){
        if(parameters.isEmpty())
            return;

        if(parameters.getLast().isOptional)
            throw new IllegalArgumentException("Cannot add more parameters after optional");
    }
}
