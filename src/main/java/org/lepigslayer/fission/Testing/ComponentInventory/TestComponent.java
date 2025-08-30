package org.lepigslayer.fission.Testing.ComponentInventory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.lepigslayer.fission.InventorySystem.InventoryComponent;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.InventorySlot;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.LoreSection;
import org.lepigslayer.fission.InventorySystem.SlotRenderer.SlotRendererComponent;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestComponent extends InventoryComponent {
    private static final int FINAL_SLOT = 13;

    private InventorySlot successSlot;
    private List<Integer> previous = new ArrayList<>();
    private int currentSlot;
    private InventorySlot randomSlot;
    private Instant started;

    @Override
    public void update() {
        if(successSlot == null)
            return;

        Duration duration = Duration.between(started, Instant.now());

        long seconds = duration.getSeconds();

        boolean lit = seconds % 2 == 0;

        successSlot.texture(lit ? Material.GOLD_BLOCK : Material.COAL_BLOCK);
    }

    @Override
    public void initalize() {
        if (previous.size() == instance.getSize() - 1) {
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 2f);

            successSlot = new InventorySlot()
                    .name(" ")
                    .texture(Material.GOLD_BLOCK);

            for (int i = 0; i < instance.getSize(); i++) {
                if (i == FINAL_SLOT) {
                    Duration duration = Duration.between(started, Instant.now());

                    double ms = duration.toMillis() / 1000.0;

                    InventorySlot finalSlot = new InventorySlot()
                            .name("§eYou did it!")
                            .texture(Material.CLOCK)
                            .lore("§7And it only took you %.2f seconds!".formatted(ms))
                            .onClick(player::closeInventory);

                    setSlot(i, finalSlot);
                    continue;
                }
                setSlot(i, successSlot);
            }
            return;
        }

        do {
            currentSlot = new Random().nextInt(instance.getSize());
        } while (previous.contains(currentSlot) || currentSlot == FINAL_SLOT);

        randomSlot = new InventorySlot();

        randomSlot
                .name("§5Random Slot")
                .texture(Material.CLOCK)
                .lore("§eWoah look you found me", "§eHow neat is that")
                .onClick(this::click);

        setSlot(currentSlot, randomSlot);

        for (Integer i : previous) {
            InventorySlot slot = new InventorySlot()
                    .name(" ")
                    .texture(Material.BEDROCK);

            setSlot(i, slot);
        }
    }

    private void click() {
        if (started == null)
            started = Instant.now();

        player.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1f, 2f);
        previous.add(currentSlot);
        instance.refresh();
    }
}
