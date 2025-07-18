package org.lepigslayer.fission.CustomCommand.ArgumentSteps;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;
import org.lepigslayer.fission.CustomCommand.CommandParameter;

import java.util.List;

public class ArgumentValidatorStep implements ArgumentStep {
    private static final String paramTemplate = "<%s>";
    private static final String optionalTemplate = "[<%s>]";

    @Override
    public void process(ArgumentContext context) {
        if(!context.isIdentified())
            return;

        CommandParameter<?> param = context.getParameter();

        param.updateParameter();

        String paramError = param.validate(context.getArgument());
        if(paramError != null) {
            context.error(paramError);
            return;
        }

        context.store(param.getParameterIndex(),context.getArgument());

        List<String> overridePrompt = param.getOverridePrompt(context.getArgument());
        if(overridePrompt != null) {
            context.prompt(overridePrompt);
            return;
        }

        String template = param.isOptional() ? optionalTemplate : paramTemplate;
        context.prompt(template.formatted(param.getName()));
    }

    @Override
    public void reset() {

    }
}