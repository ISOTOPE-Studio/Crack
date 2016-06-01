package cc.isotopestudio.Crack.Mob;

import cc.isotopestudio.Crack.utli.S;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Mars on 6/1/2016.
 * Copyright ISOTOPE Studio
 */
class Protector extends Mob {

    /*
    怪物名：&c异界守护者
    怪物模型：末影人
    血量：100
    伤害：40（不是真实伤害可以被护甲抵消）
    被动技能：速度1级，攻击造成1秒失明
    主动技能：无
    */

    Protector() {
        super("Protector", EntityType.ENDERMAN);
        displayName = S.toRed("异界守护者");
        health = 100;
        attack = 40;
    }

    public void onAttack(EntityDamageByEntityEvent event) {
        event.setDamage(attack);
    }
}
