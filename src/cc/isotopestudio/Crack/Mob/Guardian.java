package cc.isotopestudio.Crack.mob;

import cc.isotopestudio.Crack.utli.S;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
        super("Guardian", EntityType.WITHER_SKULL);
        displayName = S.toRed("异界守卫");
        health = 500;
        attack = 5;
    }

    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            player.setHealth((player.getHealth() < attack ? 0 : player.getHealth() - attack));
        }

    }
}
