package net.azisaba.silo.newyear;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * メインクラス
 *
 * @author siloneco
 *
 */
public class NewYearCountdown extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(getName() + " enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(getName() + " disabled.");
    }
}
