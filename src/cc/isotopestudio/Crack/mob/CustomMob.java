package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.room.Room;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

/**
 * Created by Mars on 5/27/2016.
 * Copyright ISOTOPE Studio
 */
public class CustomMob extends Mob {
    String displayName = null;
    int health = -1;
    int attack = -1;

    public static HashMap<String, CustomMob> mobs;

    CustomMob(String name, EntityType type) {
        super(name, type);
    }

    @Override
    public void onAttack(EntityDamageByEntityEvent event) {

    }

    @Override
    public void onSkill(Room room, LivingEntity mob) {

    }

}
