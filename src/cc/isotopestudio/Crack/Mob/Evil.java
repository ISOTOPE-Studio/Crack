package cc.isotopestudio.Crack.Mob;

import cc.isotopestudio.Crack.utli.S;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Mars on 6/1/2016.
 * Copyright ISOTOPE Studio
 */
class Evil extends Mob {

    /*
    ��������&d[BOSS]����ħ
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
    ��ʾ��Ϣ��&d&l[BOSS]����ħ:&c�����������ʵ���...

    ��ħ֮����
    �ͷż����60��
    ��Χ����Χ�뾶6�������
    Ч���������������߲���ը�����10����ʵ�˺�����������ԭ�����ĩӰ������Ч��
    ��ǰ3����ʾ��Ϣ��&6&l[��ʾ]:&a����ħ�����ͷŶ�ħ֮��,�뾡��Զ�룡

    ��ħ֮�ۣ�
    �ͷż����90��
    ��Χ�������һ�����
    Ч����˲�Ƶ���������ɱ����ң���ĩӰ������Ч��
    ǰ������ʾ: &6&l[��ʾ]:&a����ħ���������Ķ�ħ֮��������ң��뾡��Զ�룡
    */

    Evil() {
        super("Evil", EntityType.WITHER);
        displayName = S.toRed("[BOSS]����ħ");
        health = 20000;
        attack = 2;
    }

    public void onAttack(EntityDamageByEntityEvent event) {

    }
}
