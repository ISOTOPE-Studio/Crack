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
        Utli.sendAllPlayers(room, "��d��l[BOSS]����ħ:��c�����������ʵ���...");
    }

    private void onSkill2(Room room) {
        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ�����ͷŶ�ħ֮��,�뾡��Զ�룡");
    }

    private void onSkill3(Room room) {
        Utli.sendAllPlayers(room, "��6��l[��ʾ]:��a����ħ���������Ķ�ħ֮��������ң��뾡��Զ�룡");
    }
}
