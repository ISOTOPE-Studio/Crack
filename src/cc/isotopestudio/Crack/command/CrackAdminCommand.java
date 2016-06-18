package cc.isotopestudio.Crack.command;

import cc.isotopestudio.Crack.debugGUI.LogGUI;
import cc.isotopestudio.Crack.debugGUI.SettingsGUI;
import cc.isotopestudio.Crack.mob.Mob;
import cc.isotopestudio.Crack.room.MobSpawnObj;
import cc.isotopestudio.Crack.room.Room;
import cc.isotopestudio.Crack.utli.ParticleEffect;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrackAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("crackadmin")) {
            if (!sender.hasPermission("crack.admin")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            if (args.length > 0 && args[0].equalsIgnoreCase("debug") && args.length > 1) {
                if (args[1].equalsIgnoreCase("on")) {
                    SettingsGUI.on(true);
                    LogGUI.on(true);
                    return true;
                }
                if (args[1].equalsIgnoreCase("off")) {
                    SettingsGUI.on(false);
                    LogGUI.on(false);
                    return true;
                }
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
                sendHelpPage1(sender, label);
                return true;
            }
            Player player = (Player) sender;
            for (int i = 0; i < 10; i++)
                ParticleEffect.PORTAL.display((float) (Math.random() * 2), (float) (Math.random() * 2), (float) (Math.random() * 2), 5, 50, player.getEyeLocation(), 50);
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("2")) {
                        sendHelpPage2(player, label);
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("3")) {
                        sendHelpPage3(player, label);
                        return true;
                    }
                }
                sendHelpPage1(player, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                String result = S.toPrefixAqua("�б�: ");
                for (Room room : Room.rooms.values()) {

                    if (!room.isFinish())
                        result += S.toRed(room.getName());
                    else
                        result += S.toBoldGreen(room.getName());
                    result += ", ";
                }
                player.sendMessage(result);
                return true;
            }
            if (args[0].equalsIgnoreCase("moblist")) {
                String result = S.toPrefixAqua("�����б�: ");
                player.sendMessage(result + Mob.mobs.toString());
                return true;
            }
            if (args.length == 1) {
                Room room = Room.rooms.get(args[0]);
                if (room == null) {
                    player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("�������� " + args[0] + " ����Ϣ"));
                if (room.isFinish()) {
                    player.sendMessage(S.toGreen("    ���������(����ˢ����") + S.toGreen("������" + room.getMobSpawnObj().size() + "��)"));
                } else {
                    player.sendMessage(S.toAqua("    �ȴ�����     ") + ((room.getLobby() == null) ? S.toRed("δ����") : S.toGreen("������")));
                    player.sendMessage(S.toAqua("    ���������  ") + ((room.getSpawn() == null) ? S.toRed("δ����") : S.toGreen("������")));
                    player.sendMessage(S.toAqua("    ������       ") + ((room.getRespawn() == null) ? S.toRed("δ����") : S.toGreen("������")));
                    player.sendMessage(S.toAqua("    ����ˢ����  ") +
                            ((room.getMobSpawnObj().size() == 0) ? S.toRed("δ����") : S.toGreen("������" + room.getMobSpawnObj().size() + "��")));
                    player.sendMessage(S.toAqua("    BOSS���ɵ�      ") + ((room.getBossObj() == null) ? S.toRed("δ����") : S.toGreen("������")));
                }
                player.sendMessage(S.toAqua("    ��С/���������   ") + S.toGreen(room.getMinPlayer() + " / " + room.getMaxPlayer()));
                player.sendMessage(S.toAqua("    ���   ") + S.toGreen(room.getPlayersNames().toString()));
                player.sendMessage(S.toAqua("    ״̬   ") + S.toGreen(room.getStatus().name()));
                return true;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("create")) {
                    if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("moblist")) {
                        player.sendMessage(S.toPrefixRed("�뻻������"));
                        return true;
                    }
                    if (Room.rooms.get(args[0]) != null) {
                        player.sendMessage(S.toPrefixRed("��������" + args[0] + "�Ѿ�����"));
                        return true;
                    }
                    Room room = new Room(args[0]);
                    Room.rooms.put(args[0], room);
                    room.setMaxPlayer(10);
                    room.setMinPlayer(2);
                    player.sendMessage(S.toPrefixGreen("�ɹ������������� " + args[0]));
                    player.sendMessage(S.toPrefixGreen("��������ô����������㡢�����㡢BOSS���ɵ�͹���ˢ����"));
                    return true;
                }
                Room room = Room.rooms.get(args[0]);
                if (room == null) {
                    player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("spawn")) {
                    room.setSpawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĳ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("respawn")) {
                    room.setRespawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ��������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("lobby")) {
                    room.setLobby(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĵȴ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("boss")) {
                    if (args.length > 2) {
                        Mob mob = Mob.mobs.get(args[2]);
                        if (mob == null) {
                            player.sendMessage(S.toPrefixRed(args[2] + "��������Ч�Ĺ�������"));
                            return true;
                        }
                        room.setBossLocation(player.getLocation(), mob);
                        player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ��BOSS���ɵ�"));
                        return true;
                    }
                    player.sendMessage(S.toBoldGreen("/" + label + " <������> boss <��������>") + S.toGray(" - ") + S.toGold("���ø�������BOSS���ɵ�"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        if (!(args.length > 5)) {
                            player.sendMessage(S.toBoldGreen("/" + label + " <������> mob add <��������> <ˢ��Ƶ��/s> <ˢ�´���>") + S.toGray(" - ") + S.toGold("��Ӹ����������ˢ����"));
                            return true;
                        }

                        Mob mob = Mob.mobs.get(args[3]);
                        if (mob == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "��������Ч�Ĺ�������"));
                            return true;
                        }

                        int freq;
                        try {
                            freq = Integer.parseInt(args[4]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[4] + "��������"));
                            return true;
                        }

                        int limit;
                        try {
                            limit = Integer.parseInt(args[5]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[5] + "��������"));
                            return true;
                        }

                        room.addMobSpawn(player.getLocation(), mob, freq, limit);
                        player.sendMessage(S.toPrefixGreen("�ɹ���� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        List<MobSpawnObj> locations = room.getMobSpawnObj();
                        player.sendMessage(S.toPrefixGreen(args[0] + "�Ĺ���ˢ�����б�"));
                        player.sendMessage(S.toPrefixGreen(locations.toString()));
                        for (int i = 0; i < locations.size(); i++) {
                            //player.sendMessage(S.toGray(" [" + i + "]") + S.toAqua(" X:" + locations.get(i).getBlockX() + ", Y:" + locations.get(i).getBlockY() + ", Z" + locations.get(i).getBlockZ()));
                        }
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("delete") && args.length > 3) {
                        int index;
                        try {
                            index = Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[3] + "��������"));
                            return true;
                        }
                        if (room.deleteMobSpawn(index) == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "������Ч����"));
                        } else
                            player.sendMessage(S.toPrefixGreen("�ɹ�ɾ�� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("clear")) {
                        room.clearMobSpawn();
                        player.sendMessage(S.toPrefixGreen("�ɹ���� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                }
                if (args[1].equalsIgnoreCase("kill")) {
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "��������"));
                        return true;
                    }
                    if (count < 0) {
                        player.sendMessage(S.toPrefixRed("����С��0"));
                        return true;
                    }
                    room.setKillRequire(count);
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + "BOSSǰ���ɱ�Ĺ�������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("min")) {
                    int min;
                    try {
                        min = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "��������"));
                        return true;
                    }
                    if (min > room.getMaxPlayer()) {
                        player.sendMessage(S.toPrefixRed("���ܳ�������������" + room.getMaxPlayer()));
                        return true;
                    }
                    room.setMinPlayer(min);
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ����С�������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("max")) {
                    int max;
                    try {
                        max = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "��������"));
                        return true;
                    }
                    if (max < room.getMinPlayer()) {
                        player.sendMessage(S.toPrefixRed("����С����С�������" + room.getMinPlayer()));
                        return true;
                    }
                    room.setMaxPlayer(max);
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ������������"));
                    return true;
                }
                player.sendMessage(S.toPrefixRed("δ֪����"));
                return true;
            } else {
                player.sendMessage(S.toPrefixRed("δ֪����"));
                return true;
            }
        }
        return false;
    }

    private void sendHelpPage1(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("�����˵� �� 1 ҳ"));
        player.sendMessage(S.toBoldGreen("/" + label + " list") + S.toGray(" - ") + S.toGold("�鿴��������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������>") + S.toGray(" - ") + S.toGold("�鿴����������Ϣ"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> create") + S.toGray(" - ") + S.toGold("�����µĸ�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> spawn") + S.toGray(" - ") + S.toGold("���ø������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> respawn") + S.toGray(" - ") + S.toGold("���ø�������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> lobby") + S.toGray(" - ") + S.toGold("���ø����������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> boss <��������>") + S.toGray(" - ") + S.toGold("���ø�������BOSS���ɵ�"));
        player.sendMessage(S.toYellow("/" + label + " help 2") + S.toGray(" - ") + S.toGold("�ڶ�ҳ"));
    }

    private void sendHelpPage2(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("�����˵� �� 2 ҳ"));
        player.sendMessage(S.toBoldGreen("/" + label + " moblist") + S.toGray(" - ") + S.toGold("�鿴�����õĹ�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob add <��������> <ˢ��Ƶ��/s> <ˢ�´���>") + S.toGray(" - ") + S.toGold("��Ӹ����������ˢ����"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob list") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob delete <���>") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob clear") + S.toGray(" - ") + S.toGold("��������������ˢ�����б�"));
        player.sendMessage(S.toYellow("/" + label + " help 3") + S.toGray(" - ") + S.toGold("����ҳ"));
    }

    private void sendHelpPage3(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("�����˵� �� 3 ҳ"));
        player.sendMessage(S.toBoldGreen("/" + label + " kill <��������>") + S.toGray(" - ") + S.toGold("����BOSSǰ���ɱ�Ĺ�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " min <�������>") + S.toGray(" - ") + S.toGold("���ø���������С�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " max <�������>") + S.toGray(" - ") + S.toGold("���ø�����������������"));
    }

}
