package cc.isotopestudio.Crack.task;

import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/28/2016.
 * Copyright ISOTOPE Studio
 */
class DateTask extends BukkitRunnable {
    @Override
    public void run() {
        Date todayDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(todayDate);

        String date = plugin.getPlayerData().getString("date");
        if (date == null) {
            plugin.getPlayerData().set("date", today);
            plugin.savePlayerData();
            return;
        }

        if (!date.equals(today)) {

            for (String key : plugin.getPlayerData().getKeys(false)) {
                plugin.getPlayerData().set(key + ".times", 0);
            }
            plugin.savePlayerData();
        }
    }
}
