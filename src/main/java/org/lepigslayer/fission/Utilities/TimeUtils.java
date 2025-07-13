package org.lepigslayer.fission.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimeUtils {

    public static void nextTick(Plugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, 1L);
    }

    public static void nextTick(Class<?> providedClass, Runnable runnable) {
        nextTick(JavaPlugin.getProvidingPlugin(providedClass), runnable);
    }

    public static BukkitRunnable runTask(Plugin plugin, Runnable runnable, long interval) {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        };

        bukkitRunnable.runTaskTimer(plugin, 0, interval);

        return bukkitRunnable;
    }

    public static BukkitRunnable runTask(Class<?> providedClass, Runnable runnable, long interval) {
        return runTask(JavaPlugin.getProvidingPlugin(providedClass), runnable, interval);
    }
}
