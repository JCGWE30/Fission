package org.lepigslayer.fission.CustomCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.checkerframework.checker.units.qual.A;
import org.lepigslayer.fission.CustomCommand.ArgumentSteps.*;

import java.util.*;

public class CustomCommandHandler implements CommandExecutor, TabCompleter {
    private Set<String> possibleFlags;
    private CustomCommand customCommand;

    private List<ArgumentStep> argumentSteps;

    public CustomCommandHandler(CustomCommand command) {
        this.customCommand = command;
        this.possibleFlags = customCommand.flags.keySet();
        this.argumentSteps = Arrays.asList(
                new FlagIdentifierStep(customCommand.flags),
                new ParameterIdentifierStep(customCommand.parameters),
                new IdentiryEnforcerStep(),
                new ArgumentValidatorStep()
        );
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        ArgumentContext context = parseCommand(arguments, true);

        if(context == null){
            commandSender.sendMessage("§cMissing required parameters");
            return true;
        }

        if(context.hasExecuteError || context.hasError){
            commandSender.sendMessage("§c"+context.getAnyErrorText());
            return true;
        }

        CommandData data = new CommandData(context.argumentData, commandSender);
        customCommand.executeCommand(data);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] arguments) {
        ArgumentContext context = parseCommand(arguments, false);

        if(context.hasError)
            return Collections.singletonList("§c"+context.errorText);

        if(context.hasPrompt)
            return context.promptText;

        return Collections.emptyList();
    }

    private ArgumentContext parseCommand(String[] unProcessedInput, boolean collectData){
        String[] arguments = clumpQuotations(unProcessedInput);

        argumentSteps.forEach(ArgumentStep::reset);

        Iterator<String> argumentIterator = Arrays.asList(arguments).iterator();

        ArgumentContext context = null;

        HashMap<Integer, String> dataMap = new HashMap<>();

        while (argumentIterator.hasNext()) {
            String argument = argumentIterator.next();

            context = !collectData ? new ArgumentContext(argument) : new ArgumentContext(argument, dataMap);

            for (ArgumentStep step : argumentSteps) {
                step.process(context);
                if (context.shouldHalt)
                    break;
            }
            if (context.hasError) {
                return context;
            }
        }
        if(context == null)
            return null;

        if (context.hasPrompt) {
            return context;
        }
        return context;
    }

    private String[] clumpQuotations(String[] input){
        return input;
    }
}
