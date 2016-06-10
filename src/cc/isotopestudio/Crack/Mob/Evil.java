package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.debugGUI.LogGUI;
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
        health = 100;
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
        fireParticle(mob, 3);
        if (Utli.random(30)) {
            onSkill1(room, mob);
            LogGUI.addInfo("Mob Evil-on Skill 1 in "+room.getName());
        } else if (Utli.random(60)) {
            onSkill2(room, mob);
            LogGUI.addInfo("Mob Evil-on Skill 2 in "+room.getName());
        } else if (Utli.random(90)) {
            onSkill3(room, mob);
            LogGUI.addInfo("Mob Evil-on Skill 3 in "+room.getName());
        }
    }

    private static final PotionEffect SLOW = new PotionEffect(PotionEffectType.SLOW, 5 * 20, 2, false);
    private static final PotionEffect BLINDNESS = new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 2, false);

    private void onSkill1(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "��d��l[BOSS]����ħ:��c�����������ʵ���...");
        for (Player player : room.getAlivePlayer()) {
            player.addPotionEffect(SLOW);
            player.addPotionEffect(BLINDNESS);
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
                        for (int i = 0; i < 10; i++)
                            ParticleEffect.PORTAL.display((float) (Math.random() * 2), (float) (Math.random() * 2), (float) (Math.random() * 2), 5, 50, player.getEyeLocation(), 50);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                                    Vector v = mob.getEyeLocation().subtract(player.getEyeLocation()).toVector().normalize().multiply(2);
                                    player.setVelocity(v);
                                    fireParticle(player, 20);
                                }
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                                    player.getWorld().createExplosion(player.getEyeLocation(), 0, false);
                                    player.damage(10, mob);
                                }
                            }
                        }.runTaskLater(plugin, 40);
                    }
                }
            }
        }.runTaskLater(plugin, 20);
    }

    private void fireParticle(Entity entity, int count) {
        if (count > 0)
            new BukkitRunnable() {
                @Override
                public void run() {
                    ParticleEffect.FLAME.display(0, 0, 0, 0, 5 * (21 - count), entity.getLocation(), 50);
                    fireParticle(entity, count - 1);
                }
            }.runTaskLater(plugin, 1);
    }

    private void onSkill3(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ���������Ķ�ħ֮��������ң��뾡��Զ�룡");
        System.out.print(mob);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                    Location mobLocation = mob.getEyeLocation();
                    Player nearest = null;
                    double nearDistance = 0;
                    for (Player player : room.getAlivePlayer()) {
                        double distance = player.getLocation().distance(mobLocation);
                        if (nearest == null || distance < nearDistance) {
                            System.out.println("nearest" + nearest);
                            nearest = player;
                            nearDistance = distance;
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
