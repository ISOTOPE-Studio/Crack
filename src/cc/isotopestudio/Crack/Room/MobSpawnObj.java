package cc.isotopestudio.Crack.room;

import cc.isotopestudio.Crack.mob.Mob;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class MobSpawnObj {
    private final Location loc;
    private final Mob mob;
    private final int freq;
    private final int limit;
    private final Room room;
    private int count = 0;
    private final Set<LivingEntity> mobs;
    private BukkitRunnable task;

    MobSpawnObj(Location loc, Mob mob, int freq, int limit, Room room) {
        this.loc = loc;
        this.mob = mob;
        this.freq = freq;
        this.limit = limit;
        this.room = room;
        mobs = new HashSet<>();
    }

    public Location getLocation() {
        return loc;
    }

    public Mob getMob() {
        return mob;
    }

    public int getFreq() {
        return freq;
    }

    public int getLimit() {
        return limit;
    }

    public void resetCount() {
        count = 0;
    }

    public boolean isAvailble() {
        return count < limit;
    }

    public Set<LivingEntity> getMobs() {
        return mobs;
    }

    public void spawn() {
        mobs.add(mob.spawn(loc));
        if (task == null) { // First run
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    for (LivingEntity entity : mobs)
                        if (entity.getHealth() > 0)
                            mob.onSkill(room, entity);
                }
            };
            task.runTaskTimer(plugin, 1, 20);
        }
        count++;
    }


    public void clear() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        for (LivingEntity mob : mobs) {
            mob.setHealth(0);
        }
    }

    @Override
    public String toString() {
        return "MobSpawnObj{" +
                "loc=" + Utli.locationToString(loc) +
                ", mob=" + mob.getName() +
                ", freq=" + freq +
                ", limit=" + count + " / " + limit +
                ", mobs=" + mobs +
                '}';
    }

    public ConfigurationSection serialize() {
        // TO-DO serialize
        return null;
    }

    static MobSpawnObj deserialize(ConfigurationSection mobSpawns, Room room) {
        String mobName = mobSpawns.getString("mob");
        Mob mob = Mob.mobs.get(mobName);
        if (mob == null)
            return null;
        Location loc = Utli.stringToLocation(mobSpawns.getString("location"));
        int freq = mobSpawns.getInt("freq");
        int limit = mobSpawns.getInt("limit");
        return new MobSpawnObj(loc, mob, freq, limit, room);
    }
}
