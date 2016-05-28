package cc.isotopestudio.Crack.data;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.logging.Level;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class MobData {
    private final String name;
    private final EntityType type;
    private String displayName = null;
    private int health = -1;

    public static HashMap<String, MobData> mobs;

    public static void update() {
        mobs = new HashMap<>();
        for (String mobName : plugin.getMobsData().getKeys(false)) {
            EntityType type;
            String typeName = plugin.getMobsData().getString(mobName + ".type");
            try {
                type = EntityType.valueOf(typeName);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().log(Level.SEVERE, mobName + "�е�" + typeName + "��Ч");
                continue;
            }
            MobData mob = new MobData(mobName, type);
            int maxHealth = plugin.getMobsData().getInt(mobName + ".health");
            if (maxHealth > 0)
                mob.health = maxHealth;
            String display = plugin.getMobsData().getString(mobName + ".name");
            if (display != null)
                mob.displayName = display;
            mobs.put(mobName, mob);
        }
    }

    private MobData(String name, EntityType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }

    public LivingEntity spawn(Location loc) {
        LivingEntity mob = (LivingEntity) loc.getWorld().spawn(loc, getType().getEntityClass());
        if (health != -1) {
            mob.setMaxHealth(health);
            mob.setHealth(health);
        }
        if (displayName != null)
            mob.setCustomName(displayName);
        return mob;
    }

    @Override
    public String toString() {
        return "MobData{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", displayName='" + displayName + '\'' +
                ", health=" + health +
                '}';
    }
}
