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
    ��������&c�������
    ����ģ�ͣ��������ã�����ʯ����
    Ѫ����500
    �˺���5����ʵ�˺���
    �������ܣ��ٶ�2�����������3�����
    �������ܣ���
    */

    Guardian() {
        super("Guardian", EntityType.WITHER_SKULL);
        displayName = S.toRed("�������");
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
