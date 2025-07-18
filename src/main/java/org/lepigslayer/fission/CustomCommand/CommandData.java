package org.lepigslayer.fission.CustomCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandData {
    private Map<Integer, String> dataMap;
    private CommandSender commandSender;

    CommandData(Map<Integer,String> dataMap, CommandSender sender){
        this.dataMap = dataMap;
        this.commandSender = sender;
    }

    public Player getPlayerSender(){
        if(commandSender instanceof Player)
            return (Player) commandSender;
        return null;
    }

    public CommandSender getCommandSender(){
        return commandSender;
    }

    public <T> T getValue(CommandParameter<T> parameter){
        if(!dataMap.containsKey(parameter.parameterIndex))
            return null;

        String rawValue = dataMap.get(parameter.parameterIndex);
        return parameter.getValue(rawValue);
    }

    public <T> T getValue(CommandParameter<T> parameter, T defaultValue){
        //This is the current system for declaring flag defaults, works fine however it could be nice to move it to some logic within CommandHandler or ArgumentContext idk.
        if(!dataMap.containsKey(parameter.parameterIndex))
            return defaultValue;

        String rawValue = dataMap.get(parameter.parameterIndex);
        return parameter.getValue(rawValue);
    }
}
