package com.axon.proArena.object.selection;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;

public class SelectionStatusComponent {

    private static final String LINE = "----------------------------";

    public static Component build(Location min, Location max, Location center) {
        Component header = Component.text(LINE, NamedTextColor.DARK_GRAY);

        Component body =
                Component.text("| ", NamedTextColor.DARK_GRAY)
                        .append(label("pos1"))
                        .append(value(min))
                        .append(Component.newline())

                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(label("pos2"))
                        .append(value(max))
                        .append(Component.newline())

                        .append(Component.text("| ", NamedTextColor.DARK_GRAY))
                        .append(label("center"))
                        .append(value(center))
                        .append(Component.newline())
                        .append(Component.text("|", NamedTextColor.DARK_GRAY))
                        .append(Component.newline());

        Component footer = Component.text(LINE, NamedTextColor.DARK_GRAY);

        return Component.empty()
                .append(header).append(Component.newline())
                .append(body)
                .append(footer);
    }

    private static Component label(String text) {
        return Component.text(text + ": ", NamedTextColor.GRAY);
    }

    private static Component value(Location location) {
        return Component.text(location == null ? "null" : location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ(), NamedTextColor.YELLOW);
    }

    private static Component button(String text, NamedTextColor color, ClickEvent clickEvent) {
        return Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text(text, color)
                                .decorate(TextDecoration.BOLD)
                                .clickEvent(clickEvent)
                )
                .append(Component.text("]", NamedTextColor.DARK_GRAY));
    }
}
