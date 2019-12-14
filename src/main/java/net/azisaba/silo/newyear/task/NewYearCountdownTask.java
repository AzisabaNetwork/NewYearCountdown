package net.azisaba.silo.newyear.task;

import java.math.BigDecimal;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.azisaba.silo.newyear.NewYearCountdown;

import lombok.RequiredArgsConstructor;

/**
 * 新年のカウントダウンを行うタスクです
 *
 * @author siloneco
 *
 */
@RequiredArgsConstructor
public class NewYearCountdownTask extends BukkitRunnable {

    private final NewYearCountdown plugin;
    private final Calendar targetCal;

    private final Runnable runnable;

    private boolean executed = false;

    @Override
    public void run() {
        // 現在時刻を取得
        Calendar cal = Calendar.getInstance();

        // Long型に変換
        long now = cal.getTimeInMillis();
        long target = targetCal.getTimeInMillis();

        // 残りミリ秒数を取得
        long remaining = target - now;

        // 残りミリ秒が100を切っている場合
        if ( remaining <= 100 ) {
            // 既に実行されている場合はreturn
            if ( executed ) {
                Bukkit.getLogger().info("already executed runnable (" + getTaskId() + ")");
                return;
            }

            runnable.run();
            executed = true;
            Bukkit.getLogger().info("executed runnable now (" + getTaskId() + ")");
            return;
        }

        // tickに変換
        long remainingTicks = new BigDecimal(remaining)
                .divide(BigDecimal.valueOf(50), BigDecimal.ROUND_DOWN)
                .divide(BigDecimal.valueOf(3), BigDecimal.ROUND_DOWN) // どうしてもtick数は20以下になるので、3で割ることで遅れるのを防ぐ
                .setScale(0, BigDecimal.ROUND_DOWN)
                .longValue();

        // tick数が0以下の場合は1にする
        if ( remainingTicks <= 0 ) {
            remainingTicks = 1;
        }

        // 次のタスクを実行
        new NewYearCountdownTask(plugin, targetCal, runnable).runTaskLater(plugin, remainingTicks);
        Bukkit.getLogger().info("executed new task (" + getTaskId() + ") (Ticks: " + remainingTicks + ")");

        // このオブジェクトを削除
        this.cancel();
        try {
            this.finalize();
        } catch ( Throwable e ) {
            e.printStackTrace();
        }
    }
}
