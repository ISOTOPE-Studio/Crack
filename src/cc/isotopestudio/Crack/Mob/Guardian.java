package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
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
    private static final PotionEffect WITHER = new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1, false);

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton entity = (Skeleton) super.spawn(loc);
        EntityEquipment equip = entity.getEquipment();
        equip.setItemInHand(new ItemStack(Material.IRON_SWORD));
        equip.setHelmet(new ItemStack(Material.IRON_HELMET));
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
            player.addPotionEffect(WITHER);
            event.setDamage(0);
            player.setHealth((player.getHealth() < attack ? 0 : player.getHealth() - attack));
        }
    }

    @Override
    public void onSkill(Room room, LivingEntity mob) {

    }
}
