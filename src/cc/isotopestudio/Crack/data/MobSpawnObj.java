package cc.isotopestudio.Crack.data;

import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class MobSpawnObj/* implements Serializable*/ {
    private Location loc;
    private MobData mob;
    private int freq;

    public MobSpawnObj(Location loc, MobData mob, int freq) {
        this.loc = loc;
        this.mob = mob;
        this.freq = freq;
    }

    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public MobData getMob() {
        return mob;
    }

    public void setMob(MobData mob) {
        this.mob = mob;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public String toString() {
        return "MobSpawnObj{" +
                "loc=" + Utli.locationToString(loc) +
                ", mob=" + mob.getName() +
                ", freq=" + freq +
                '}';
    }
}
