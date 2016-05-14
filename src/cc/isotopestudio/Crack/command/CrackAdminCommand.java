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
            if (args.length > 1 && !args[0].equalsIgnoreCase("help")) {
                if (args[1].equalsIgnoreCase("check")) {
                    RoomData room = new RoomData(args[0]);
                    if (!room.isExist()) {
                        player.sendMessage(S.toPrefixRed("��������" + args[0] + "������"));
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
                if (args[1].equalsIgnoreCase("spawn")) {
                    new RoomData(args[0]).setSpawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĳ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("respawn")) {
                    new RoomData(args[0]).setRespawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " ��������"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("lobby")) {
                    new RoomData(args[0]).setLobby(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("�ɹ����� " + args[0] + " �ĵȴ�����"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        new RoomData(args[0]).addMobSpawn(player.getLocation());
                        player.sendMessage(S.toPrefixGreen("�ɹ���� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        List<Location> locations = new RoomData(args[0]).getMobSpawn();
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
                        Location location = new RoomData(args[0]).deleteMobSpawn(index);
                        if (location == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "������Ч����"));
                        } else
                            player.sendMessage(S.toPrefixGreen("�ɹ�ɾ�� " + args[0] + " �Ĺ���ˢ����"));
                        return true;
                    }
                }
                sendHelp(player, label);
                return true;
            } else {
                sendHelp(player, label);
                return true;
            }
        }
        return false;
    }

    private void sendHelp(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("�����˵�"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������>") + S.toGray(" - ") + S.toGold("�鿴����������Ϣ"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> spawn") + S.toGray(" - ") + S.toGold("���ø������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> respawn") + S.toGray(" - ") + S.toGold("���ø�������������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> lobby") + S.toGray(" - ") + S.toGold("���ø����������"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob add") + S.toGray(" - ") + S.toGold("��Ӹ����������ˢ����"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob list") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
        player.sendMessage(S.toBoldGreen("/" + label + " <������> mob delete <���>") + S.toGray(" - ") + S.toGold("�鿴�����������ˢ�����б�"));
    }

}
