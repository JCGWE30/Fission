package org.lepigslayer.fission.CustomCommand.ArgumentSteps;

import org.lepigslayer.fission.CustomCommand.ArgumentContext;
import org.lepigslayer.fission.CustomCommand.CommandParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlagIdentifierStep implements ArgumentStep{
    private Map<String, CommandParameter<?>> flags;
    private List<String> possibleFlags;
    private CommandParameter<?> heldFlag;

    public FlagIdentifierStep(Map<String, CommandParameter<?>> flags){
        this.flags = flags;
        this.possibleFlags = new ArrayList<>(flags.keySet());
    }

    @Override
    public void process(ArgumentContext context) {
        if(context.isIdentified())
            return;

        if(heldFlag != null){
            context.flag(heldFlag);
            heldFlag = null;
            return;
        }

        if(context.getArgument().startsWith("-")){
            String argument = context.getArgument().substring(1);
            List<String> possible = getPossibleFlags(argument);

            if(possible.isEmpty()){
                context.error("Invalid Flag");
                return;
            }

            context.prompt(addFlagPrefix(possible));

            CommandParameter<?> parameter = getSoleFlag(possible,argument);
            if(parameter != null)
                heldFlag = parameter;
        }

        if(heldFlag != null || context.getArgument().startsWith("-"))
            context.executeError("Invalid Flag");
    }

    @Override
    public void reset() {
        this.possibleFlags = new ArrayList<>(flags.keySet());
        heldFlag = null;
    }

    private List<String> getPossibleFlags(String starter){
        if(starter.isEmpty())
            return possibleFlags;

        return possibleFlags.stream()
                .filter(s->s.startsWith(starter))
                .toList();
    }

    private CommandParameter<?> getSoleFlag(List<String> options, String input){
        for (String option : options) {
            if(!option.equals(input))
                continue;
            possibleFlags.remove(option);
            return flags.get(option);
        }

        return null;
    }

    private List<String> addFlagPrefix(List<String> options){
        return options.stream().map(s->"-"+s)
                .toList();
    }
}
