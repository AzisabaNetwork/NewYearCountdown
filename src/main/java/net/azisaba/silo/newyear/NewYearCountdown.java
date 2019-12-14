package net.azisaba.silo.newyear;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import net.azisaba.silo.newyear.config.CountdownConfig;
import net.azisaba.silo.newyear.task.NewYearCountdownTask;
import net.md_5.bungee.api.ChatColor;

import lombok.Getter;
import lombok.Setter;

import me.rayzr522.jsonmessage.JSONMessage;

/**
 * メインクラス
 *
 * @author siloneco
 *
 */
public class NewYearCountdown extends JavaPlugin {

    @Setter
    private BukkitTask task;
    @Getter
    private CountdownConfig countdownConfig;

    // デバッグ用
    private static Calendar enable = Calendar.getInstance();

    @Override
    public void onEnable() {
        enable.setTimeInMillis(System.currentTimeMillis());

        countdownConfig = new CountdownConfig(this);
        countdownConfig.loadConfig();

        Calendar newYear = getNextNewYearCalendar();
        Calendar now = Calendar.getInstance();

        for ( int countdown : countdownConfig.getCountdownSeconds() ) {
            Bukkit.getLogger().info("registering \"" + countdown + "\"");
            // カレンダーを調節
            Calendar cal = (Calendar) newYear.clone();
            cal.add(Calendar.SECOND, -1 * countdown);

            if ( cal.before(now) ) {
                Bukkit.getLogger().info("returned because of after.");
                continue;
            }

            new NewYearCountdownTask(this, cal, () -> {
                // タイトルがある場合のみ実行する
                if ( countdownConfig.getCountdownTitleFormat() != null ) {
                    // タイトルメッセージを取得
                    String msg = countdownConfig.getCountdownTitleFormat().replace("{TIME}", DateFormatUtils.format(Calendar.getInstance(), newYear));
                    msg = ChatColor.translateAlternateColorCodes('&', msg);
                    JSONMessage title = JSONMessage.create(msg);

                    // 全プレイヤーにタイトルを送信する
                    Bukkit.getOnlinePlayers().forEach(p -> {
                        title.title(0, 30, 10, p);
                    });
                }

                // チャットメッセージがある場合のみ実行する
                if ( countdownConfig.getCountdownChatFormat() != null ) {
                    // メッセージを取得
                    String msg = countdownConfig.getCountdownChatFormat().replace("{TIME}", DateFormatUtils.format(Calendar.getInstance(), newYear));
                    msg = ChatColor.translateAlternateColorCodes('&', msg);

                    // 全プレイヤーに送信する
                    Bukkit.broadcastMessage(msg);
                }
            }).runTaskLater(this, 0);
        }

        Bukkit.getLogger().info(getName() + " enabled.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(getName() + " disabled.");
    }

    public static Calendar getNextNewYearCalendar() {
//        Calendar newYear = Calendar.getInstance();
//        newYear.set(newYear.get(Calendar.YEAR) + 1, 0, 1, 0, 0, 0);
//        newYear.set(Calendar.MILLISECOND, 0);
//
//        return newYear;

        Calendar debug = (Calendar) enable.clone();
        debug.add(Calendar.MINUTE, 1);

        return debug;
    }
}
