package cc.isotopestudio.Crack.data;

import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.logging.Level;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class MobData {
    private final String name;
    private EntityType type;

    public static HashMap<String, MobData> mobs;

    public static void update() {
        mobs = new HashMap<>();
        for (String mobName : plugin.getMobsData().getKeys(false)) {
            EntityType type;
            String typeName = plugin.getMobsData().getString(mobName + ".type");
            try {
                type = EntityType.valueOf(typeName);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().log(Level.SEVERE, mobName + "中的" + typeName + "无效");
                continue;
            }
            MobData mob = new MobData(mobName, type);
            mobs.put(mobName, mob);
        }
    }

    public MobData(String name, EntityType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MobData{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
