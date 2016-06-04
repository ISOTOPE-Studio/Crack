package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.List;

/**
 * Created by Mars on 6/1/2016.
 * Copyright ISOTOPE Studio
 */
class Protector extends Mob implements Listener {

    /*
    ��������&c����ػ���
    ����ģ�ͣ�ĩӰ��
    Ѫ����100
    �˺���40��������ʵ�˺����Ա����׵�����
    �������ܣ��ٶ�1�����������1��ʧ��
    �������ܣ���
    */

    Protector() {
        super("Protector", EntityType.ENDERMAN);
        displayName = S.toRed("����ػ���");
        health = 100;
        attack = 40;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        LivingEntity entity = super.spawn(loc);
        List<Entity> nearbys = entity.getNearbyEntities(10, 10, 10);
        for (Entity nearby : nearbys)
            if (nearby instanceof Player) {
                new EntityTargetLivingEntityEvent(entity, (Player) nearby, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
                break;
            }
        return entity;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (attacker.getType() == type &&
                attacker.getCustomName() != null && attacker.getCustomName().equals(S.toRed("����ػ���"))) {
            event.setDamage(attack);
        }
    }
}
