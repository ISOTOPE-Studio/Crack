package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.type.RoomStatus;
import cc.isotopestudio.Crack.utli.ParticleEffect;
import cc.isotopestudio.Crack.utli.S;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

import static cc.isotopestudio.Crack.Crack.plugin;

/**
 * Created by Mars on 6/1/2016.
 * Copyright ISOTOPE Studio
 */
class Evil extends Mob implements Listener {

    /*
    ����������d[BOSS]����ħ
    ����ģ�ͣ�����
    Ѫ����20000
    �˺���2����ʵ�˺�������ͷ�ҵ����˺���
    �������ܣ��ٶ�2����������ɵ���Ч��
    �������ܣ�

    ���ʣ�
    �ͷż����30��
    ��Χ����Χȫ�����
    Ч������ʱ�䣺5��
    Ч����ʧ��2��������2��
    ��ʾ��Ϣ����d��l[BOSS]����ħ:��c�����������ʵ���...

    ��ħ֮����
    �ͷż����60��
    ��Χ����Χ�뾶6�������
    Ч���������������߲���ը�����10����ʵ�˺�����������ԭ�����ĩӰ������Ч��
    ��ǰ3����ʾ��Ϣ����6��l[��ʾ]:��a����ħ�����ͷŶ�ħ֮��,�뾡��Զ�룡

    ��ħ֮�ۣ�
    �ͷż����90��
    ��Χ�������һ�����
    Ч����˲�Ƶ���������ɱ����ң���ĩӰ������Ч��
    ǰ������ʾ: ��6��l[��ʾ]:��a����ħ���������Ķ�ħ֮��������ң��뾡��Զ�룡
    */

    Evil() {
        super("Evil", EntityType.WITHER);
        displayName = S.toRed("[BOSS]����ħ");
        health = 2048;
        attack = 2;
    }

    private static final PotionEffect SPEED = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false);
    private static final PotionEffect WITHER = new PotionEffect(PotionEffectType.WITHER, 3 * 20, 1, false);

    @Override
    public LivingEntity spawn(Location loc) {
        LivingEntity entity = super.spawn(loc);
        entity.addPotionEffect(SPEED);
        return entity;
    }

    @Override
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof WitherSkull)) {
            return;
        }
        WitherSkull witherSkull = (WitherSkull) event.getDamager();
        try {
            if (((Wither) witherSkull.getShooter()).getCustomName().equals(displayName)) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    event.setDamage(0);
                    player.addPotionEffect(WITHER);
                    player.setHealth((player.getHealth() < attack ? 0 : player.getHealth() - attack));
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onSkill(Room room, LivingEntity mob) {
        System.out.println(1);
        if (Utli.random(3)) {
            onSkill1(room, mob);
        } else if (Utli.random(6)) {
            onSkill2(room, mob);
        } else if (Utli.random(9)) {
            onSkill3(room, mob);
        }
    }

    private static final PotionEffect SLOW = new PotionEffect(PotionEffectType.SLOW, 5 * 20, 2, false);
    private static final PotionEffect BLINDNESS = new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 2, false);

    private void onSkill1(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "��d��l[BOSS]����ħ:��c�����������ʵ���...");
        for (Player player : room.getAlivePlayer()) {
            player.addPotionEffect(SLOW);
            player.addPotionEffect(BLINDNESS);
            System.out.println("player" + player.getName() + ": " + SLOW + BLINDNESS);
        }
    }

    private void onSkill2(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ�����ͷŶ�ħ֮��,�뾡��Զ�룡");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                    List<Entity> nearby = mob.getNearbyEntities(12, 12, 12);
                    for (Entity entity : nearby) {
                        if (!(entity instanceof Player)) continue;
                        Player player = (Player) entity;
                        if (!room.getAlivePlayer().contains(player)) continue;
                        player.sendMessage("��6��l[��ʾ]:��c�㱻����ħ�Ķ�ħ֮���������ˣ�");
                        ParticleEffect.PORTAL.display(0, 0, 0, 1, 20, player.getEyeLocation(), 50);
                        Vector v = mob.getEyeLocation().subtract(player.getEyeLocation()).toVector().normalize().multiply(2);
                        player.setVelocity(v);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.getWorld().createExplosion(player.getEyeLocation(), 10, false);
                                player.damage(10, mob);
                            }
                        }.runTaskLater(plugin, 20);
                    }
                }
            }
        }.runTaskLater(plugin, 20);
    }

    private void onSkill3(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ���������Ķ�ħ֮��������ң��뾡��Զ�룡");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                    Location mobLocation = mob.getEyeLocation();
                    Player nearest = null;
                    double distance = 0;
                    for (Player player : room.getAlivePlayer()) {
                        System.out.println(player.getName() + " " + player.getLocation().distance(mobLocation));
                        if (nearest == null || nearest.getLocation().distance(mobLocation) < distance) {
                            nearest = player;
                            distance = player.getLocation().distance(mobLocation);
                        }

                    }
                    if (nearest != null) {
                        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ������" + nearest.getName());
                        nearest.setHealth(0);
                    }
                }
            }
        }.runTaskLater(plugin, 20);
    }
}
