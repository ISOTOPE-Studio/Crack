package cc.isotopestudio.Crack.Room;

import cc.isotopestudio.Crack.Mob.Mob;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class MobSpawnObj implements ConfigurationSerializable {
    private final Location loc;
    private final Mob mob;
    private final int freq;
    private final int limit;
    private int count = 0;

    public MobSpawnObj(Location loc, Mob mob, int freq, int limit) {
        this.loc = loc;
        this.mob = mob;
        this.freq = freq;
        this.limit = limit;
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

    public LivingEntity spawn() {
        return mob.spawn(loc);
    }

    @Override
    public String toString() {
        return "MobSpawnObj{" +
                "loc=" + Utli.locationToString(loc) +
                ", mob=" + mob.getName() +
                ", freq=" + freq +
                ", limit=" + count + " / " + limit +
                '}';
    }

    @Override
    public Map<String, Object> serialize() {
        // TO-DO serialize
        return null;
    }

    static MobSpawnObj deserialize(ConfigurationSection mobSpawns) {
        String mobName = mobSpawns.getString("mob");
        Mob mob = Mob.mobs.get(mobName);
        if (mob == null)
            return null;
        Location loc = Utli.stringToLocation(mobSpawns.getString("location"));
        int freq = mobSpawns.getInt("freq");
        int limit = mobSpawns.getInt("limit");
        return new MobSpawnObj(loc, mob, freq, limit);
    }
}
