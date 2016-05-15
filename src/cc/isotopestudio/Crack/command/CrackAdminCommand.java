package cc.isotopestudio.Crack.command;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CrackAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("crackadmin")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("����Ҫ��Ҳ���ִ��"));
                sendHelp(sender, label);
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.admin")) {
                sender.sendMessage(S.toPrefixRed("��û��Ȩ��"));
                return true;
            }
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                sendHelp(player, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                String result = S.toPrefixAqua("�б�: ");
                for (RoomData room : RoomData.rooms.values()) {

                    if (!room.isFinish())
                        result += S.toRed(room.getName());
                    else
                        result += S.toBoldGreen(room.getName());
                    result += ", ";
                }
                player.sendMessage(result);
                return true;
            }
            if (args.length == 1) {
                RoomData room = RoomData.rooms.get(args[0]);
                if (room == null) {
                    player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("�������� " + args[0] + " ����Ϣ"));
                player.sendMessage(S.toAqua("    �ȴ�����     ") + ((room.getLobby() == null) ? S.toRed("δ����") : S.toGreen("������")));
                player.sendMessage(S.toAqua("    ���������  ") + ((room.getSpawn() == null) ? S.toRed("δ����") : S.toGreen("������")));
                player.sendMessage(S.toAqua("    ������       ") + ((room.getRespawn() == null) ? S.toRed("δ����") : S.toGreen("������")));
                player.sendMessage(S.toAqua("    ����ˢ����  ") +
                        ((room.getMobSpawn().size() == 0) ? S.toRed("δ����") : S.toGreen("������" + room.getMobSpawn().size() + "��")));
                return true;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("create")) {
                    if (args[2].equalsIgnoreCase("list")) {
                        player.sendMessage(S.toPrefixRed("�뻻������" +
                                ""));
                        return true;
                    }
                    if (RoomData.rooms.get(args[0]) != null) {
                        player.sendMessage(S.toPrefixRed("��������" + args[0] + "�Ѿ�����"));
                        return true;
                    }
                    RoomData.rooms.put(args[0], new RoomData(args[0]));
                    player.sendMessage(S.toPrefixGreen("�ɹ������������� " + args[0]));
                    player.sendMessage(S.toPrefixGreen("��������ô����������㡢������͹���ˢ����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("spawn")) {
                    RoomData room = RoomData.rooms.get(args[0]);
                    if (room == null) {
                        player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                        return true;
                    }
                    room.setSpawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĳ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("respawn")) {
                    RoomData room = RoomData.rooms.get(args[0]);
                    if (room == null) {
                        player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                        return true;
                    }
                    room.setRespawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ��������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("lobby")) {
                    RoomData room = RoomData.rooms.get(args[0]);
                    if (room == null) {
                        player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                        return true;
                    }
                    room.setLobby(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĵȴ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        RoomData room = RoomData.rooms.get(args[0]);
                        if (room == null) {
                            player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                            return true;
                        }
                        room.addMobSpawn(player.getLocation());
                        player.sendMessage(S.toPrefixGreen("�ɹ���� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        RoomData room = RoomData.rooms.get(args[0]);
                        if (room == null) {
                            player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                            return true;
                        }
                        List<Location> locations = room.getMobSpawn();
                        player.sendMessage(S.toPrefixGreen(args[0] + "�Ĺ���ˢ�����б�"));
                        for (int i = 0; i < locations.size(); i++) {
                            player.sendMessage(S.toGray(" [" + i + "]") +
                                    S.toAqua(" X:" + locations.get(i).getBlockX() + ", Y:" + locations.get(i).getBlockY() + ", Z" + locations.get(i).getBlockZ()));
                        }
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("delete") && args.length > 3) {
                        int index = 0;
                        try {
                            Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[3] + "��������"));
                            return true;
                        }
                        RoomData room = RoomData.rooms.get(args[0]);
                        if (room == null) {
                            player.sendMessage(S.toPrefixRed("�������� " + args[0] + " ������"));
                            return true;
                        }
                        Location location = room.deleteMobSpawn(index);
                        if (location == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "������Ч����"));
                        } else
                            player.sendMessage(S.toPrefixGreen("�ɹ�ɾ�� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
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

    private void sendHelp(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("�����˵�"));
        player.sendMessage(S.toBoldGreen("/" + label + " list") + S.toGray(" - ") + S.toGold("�����µĸ�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������>") + S.toGray(" - ") + S.toGold("�鿴����������Ϣ"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> create") + S.toGray(" - ") + S.toGold("�����µĸ�������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> spawn") + S.toGray(" - ") + S.toGold("���ø������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> respawn") + S.toGray(" - ") + S.toGold("���ø�������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> lobby") + S.toGray(" - ") + S.toGold("���ø����������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob add") + S.toGray(" - ") + S.toGold("��Ӹ����������ˢ����"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob list") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob delete <���>") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
    }

}
