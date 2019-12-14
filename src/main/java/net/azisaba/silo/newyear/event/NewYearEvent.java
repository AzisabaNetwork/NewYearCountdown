package net.azisaba.silo.newyear.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 年が明けたときに呼び出されるイベント
 *
 * @author siloneco
 *
 */
@RequiredArgsConstructor
public class NewYearEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private final int year;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}