package dev.itzpipeg.abynspass.utils;

import org.bukkit.Bukkit;

public class Bar {

    public static String bar(double current, double total) {
        double percent = (current / total) * 20;
        int greenBars = (int) percent; // Truncar el valor, no redondear

        StringBuilder bar = new StringBuilder();
        for (int i = 1; i <= 20; i++) {
            if (i <= greenBars) {
                bar.append(CC.translate("&a◼"));
            } else {
                bar.append(CC.translate("&c◼"));
            }
        }

        if(current == total-1){
            return "&a◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼◼&c◼";
        }else {
            return bar.toString();
        }
    }

    public static String formatTimeWithoutPoints(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d h %02d m %02d s", hours, minutes, remainingSeconds);
    }
}
