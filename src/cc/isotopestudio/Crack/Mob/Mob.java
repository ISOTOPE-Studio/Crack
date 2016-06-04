package cc.isotopestudio.Crack.mob;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.logging.Level;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class Mob implements Listener {
    protected final String name;
    protected final EntityType type;
    String displayName = null;
    int health = -1;
    int attack = -1;

    public static HashMap<String, Mob> mobs;

    public static void update() {
        mobs = new HashMap<>();
        mobs.put("Protector", new Protector());
        mobs.put("Guardian", new Guardian());
        mobs.put("Evil", new Evil());
        for (String mobName : plugin.getMobsData().getKeys(false)) {
            EntityType type;
            String typeName = plugin.getMobsData().getString(mobName + ".type");
            try {
                type = EntityType.valueOf(typeName);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().log(Level.SEVERE, mobName + "中的" + typeName + "无效");
                continue;
            }
            Mob mob = new Mob(mobName, type);
            int maxHealth = plugin.getMobsData().getInt(mobName + ".health");
            if (maxHealth > 0)
                mob.health = maxHealth;
            String display = plugin.getMobsData().getString(mobName + ".name");
            if (display != null)
                mob.displayName = display;
            mobs.put(mobName, mob);
        }
        PluginManager pm = plugin.getServer().getPluginManager();
        for (Mob mob : mobs.values()) {
            pm.registerEvents(mob, plugin);
        }
    }

    Mob(String name, EntityType type) {
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
        return "mob{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", displayName='" + displayName + '\'' +
                ", health=" + health +
                '}';
    }
}
