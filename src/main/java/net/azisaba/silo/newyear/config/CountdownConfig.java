package net.azisaba.silo.newyear.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.azisaba.silo.newyear.NewYearCountdown;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * カウントダウンの設定を行うConfigを生成、ロードするクラス
 *
 * @author siloneco
 *
 */
@Getter
public class CountdownConfig extends Config {

    private List<Integer> countdownSeconds = new ArrayList<>();
    private String countdownTitleFormat = null;
    private String countdownChatFormat = null;

    public CountdownConfig(@NonNull NewYearCountdown plugin) {
        super(plugin, "configs/config.yml", "config.yml");
    }

    @SneakyThrows(value = { Exception.class })
    @Override
    public void loadConfig() {
        super.loadConfig();

        // カウントダウンする秒数が決まっている場合は取得、決まっていない場合は無視
        if ( config.isSet("CountdownSeconds") ) {
            countdownSeconds = config.getStringList("CountdownSeconds").stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());
            // 逆にして大きい順にする
            Collections.reverse(countdownSeconds);
        }

        // カウントダウン時にタイトルに表示される内容を取得、設定されていない場合は無視
        if ( config.isSet("CountdownTitleFormat") ) {
            countdownTitleFormat = config.getString("CountdownTitleFormat");
        }

        // カウントダウン時にチャットに表示される内容を取得、設定されていない場合は無視
        if ( config.isSet("CountdownChatFormat") ) {
            countdownChatFormat = config.getString("CountdownChatFormat");
        }
    }
}
