package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
class Protector extends Mob {

    /*
    ��������&c����ػ���
    ����ģ�ͣ�ĩӰ��
    Ѫ����100
    �˺���40��������ʵ�˺����Ա����׵�����
    �������ܣ��ٶ�1�����������1��ʧ��
    */

    Protector() {
        super("Protector", EntityType.ENDERMAN);
        displayName = S.toRed("����ػ���");
        health = 100;
        attack = 40;
    }

    private static final PotionEffect SPEED = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false);
    private static final PotionEffect BLINDNESS = new PotionEffect(PotionEffectType.BLINDNESS, 1 * 20, 1, false);

    @Override
    public LivingEntity spawn(Location loc) {
        LivingEntity entity = super.spawn(loc);
        entity.addPotionEffect(SPEED);
        List<Entity> nearbys = entity.getNearbyEntities(10, 10, 10);
        for (Entity nearby : nearbys)
            if (nearby instanceof Player) {
                new EntityTargetLivingEntityEvent(entity, (Player) nearby, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                break;
            }
        return entity;
    }

    @Override
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (attacker.getType() == type &&
                attacker.getCustomName() != null && attacker.getCustomName().equals(displayName)) {
            player.addPotionEffect(BLINDNESS);
            event.setDamage(attack);
        }
    }

    @Override
    public void onSkill(Room room, LivingEntity mob) {

    }
}
