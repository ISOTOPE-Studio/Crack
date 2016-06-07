package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.utli.S;
import cc.isotopestudio.Crack.utli.Utli;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        health = 2048;
        attack = 2;
    }

    private static final PotionEffect SPEED = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, false);
    private static final PotionEffect WITHER = new PotionEffect(PotionEffectType.WITHER, 3, 1, false);

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
            onSkill1(room);
        } else if (Utli.random(6)) {
            onSkill2(room);
        } else if (Utli.random(9)) {
            onSkill3(room);
        }
    }

    private void onSkill1(Room room) {
        Utli.sendAllPlayers(room, "§d§l[BOSS]异界恶魔:§c你陷入了梦魇当中...");
    }

    private void onSkill2(Room room) {
        Utli.sendAllPlayers(room, "§6§l[提示]:§a异界恶魔即将释放恶魔之力,请尽快远离！");
    }

    private void onSkill3(Room room) {
        Utli.sendAllPlayers(room, "§6§l[提示]:§a异界恶魔正在用他的恶魔之眼锁定玩家！请尽快远离！");
    }
}
