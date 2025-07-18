package org.lepigslayer.fission.CustomCommand;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomCommandRegistry {
    public static void registerCustomCommand(CustomCommand command, String commandName) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(command.getClass());
        plugin.getCommand(commandName).setExecutor(new CustomCommandHandler(command));
    }
}
