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

    public void onAttack(EntityDamageByEntityEvent event) {
        event.setDamage(attack);
    }
}
