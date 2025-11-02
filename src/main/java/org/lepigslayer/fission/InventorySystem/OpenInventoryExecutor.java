package org.lepigslayer.fission.InventorySystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class OpenInventoryExecutor implements CommandExecutor {

    private Function<Player,InventoryInstance> generator;
    private boolean needsOp;

    public OpenInventoryExecutor(Function<Player,InventoryInstance> generator, boolean needsOp) {
        this.generator = generator;
        this.needsOp = needsOp;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(needsOp && !sender.isOp())
            return true;
        if(!(sender instanceof Player player))
            return true;

        InventoryInstance inventory = generator.apply(player);
        inventory.open();

        return true;
    }
}
