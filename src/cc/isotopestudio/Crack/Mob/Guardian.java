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
    ��������&c�������
    ����ģ�ͣ��������ã�����ʯ����
    Ѫ����500
    �˺���5����ʵ�˺���
    �������ܣ��ٶ�2�����������3�����
    �������ܣ���
    */

    Guardian() {
        super("Guardian", EntityType.SKELETON);
        displayName = S.toRed("�������");
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
