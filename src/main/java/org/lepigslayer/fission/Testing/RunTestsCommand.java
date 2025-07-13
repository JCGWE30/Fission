package org.lepigslayer.fission.Testing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.lepigslayer.fission.Testing.CustomInventory.TestCustomInventory;

public class RunTestsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player))
            return true;

        Player player = (Player) sender;
        TestCustomInventory inven = new TestCustomInventory(0);
        inven.openInventory(player);

        return true;
    }
}
