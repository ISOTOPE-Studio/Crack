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
    怪物名：§d[BOSS]异界恶魔
    怪物模型：凋零
    血量：20000
    伤害：2（真实伤害被凋零头砸到的伤害）
    被动技能：速度2级，攻击造成凋零效果
    主动技能：

    梦魇：
    释放间隔：30秒
    范围：周围全部玩家
    效果持续时间：5秒
    效果：失明2级，减速2级
    提示消息：§d§l[BOSS]异界恶魔:§c你陷入了梦魇当中...

    恶魔之力：
    释放间隔：60秒
    范围：周围半径6格内玩家
    效果：把玩家拉倒身边并爆炸，造成10点真实伤害（被拉的人原地造成末影粒子特效）
    提前3秒提示消息：§6§l[提示]:§a异界恶魔即将释放恶魔之力,请尽快远离！

    恶魔之眼：
    释放间隔：90秒
    范围：最近的一个玩家
    效果：瞬移到玩家身后并秒杀该玩家（有末影粒子特效）
    前三秒提示: §6§l[提示]:§a异界恶魔正在用他的恶魔之眼锁定玩家！请尽快远离！
    */

    Evil() {
        super("Evil", EntityType.WITHER);
        displayName = S.toRed("[BOSS]异界恶魔");
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
        Utli.sendAllPlayers(room, "§d§l[BOSS]异界恶魔:§c你陷入了梦魇当中...");
        for (Player player : room.getAlivePlayer()) {
            player.addPotionEffect(SLOW);
            player.addPotionEffect(BLINDNESS);
        }
    }

    private void onSkill2(Room room, LivingEntity mob) {
        Utli.sendAllPlayers(room, "§6§l[提示]:§a异界恶魔即将释放恶魔之力,请尽快远离！");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (room.getStatus() != RoomStatus.WAITING && mob.getHealth() > 0) {
                    List<Entity> nearby = mob.getNearbyEntities(12, 12, 12);
                    for (Entity entity : nearby) {
                        if (!(entity instanceof Player)) continue;
                        Player player = (Player) entity;
                        if (!room.getAlivePlayer().contains(player)) continue;
                        player.sendMessage("§6§l[提示]:§c你被异界恶魔的恶魔之力所吸引了！");
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
        Utli.sendAllPlayers(room, "§6§l[提示]:§a异界恶魔正在用他的恶魔之眼锁定玩家！请尽快远离！");
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
                        Utli.sendAllPlayers(room, "§6§l[提示]:§a异界恶魔锁定了" + nearest.getName());
                        nearest.setHealth(0);
                    }
                }
            }
        }.runTaskLater(plugin, 20);
    }
}
