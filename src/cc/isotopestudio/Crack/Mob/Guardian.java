package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Created by Mars on 6/1/2016.
 * Copyright ISOTOPE Studio
 */
class Guardian extends Mob {

    /*
    怪物名：&c异界守卫
    怪物模型：凋零骷髅（拿钻石剑）
    血量：500
    伤害：5（真实伤害）
    被动技能：速度2级，攻击造成3秒凋零
    主动技能：无
    */

    Guardian() {
        super("Guardian", EntityType.SKELETON);
        displayName = S.toRed("异界守卫");
        health = 500;
        attack = 5;
    }

    private static final PotionEffect SPEED = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false);
    private static final PotionEffect WITHER = new PotionEffect(PotionEffectType.WITHER, 3, 1, false);

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton entity = (Skeleton) super.spawn(loc);
        entity.setSkeletonType(Skeleton.SkeletonType.WITHER);
        entity.addPotionEffect(SPEED);
        List<Entity> nearbys = entity.getNearbyEntities(10, 10, 10);
        for (Entity nearby : nearbys)
            if (nearby instanceof Player) {
                new EntityTargetLivingEntityEvent(entity, (Player) nearby, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                break;
            }
        return entity;
    }

    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (attacker.getType() == type &&
                attacker.getCustomName() != null && attacker.getCustomName().equals(displayName)) {
            player.addPotionEffect(WITHER);
            player.setHealth((player.getHealth() < attack ? 0 : player.getHealth() - attack));
        }
    }
}
