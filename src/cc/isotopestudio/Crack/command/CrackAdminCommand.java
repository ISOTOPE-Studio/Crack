package cc.isotopestudio.Crack.command;

import cc.isotopestudio.Crack.data.RoomData;
import cc.isotopestudio.Crack.utli.S;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class CrackAdminCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("crackadmin")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                sendHelpPage1(sender, label);
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if (args.length > 1 && args[1].equalsIgnoreCase("2")) {
                    sendHelpPage2(player, label);
                    return true;
                }
                sendHelpPage1(player, label);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                String result = S.toPrefixAqua("列表: ");
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
                    player.sendMessage(S.toPrefixRed("副本房间 " + args[0] + " 不存在"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("副本房间 " + args[0] + " 的信息"));
                if (room.isFinish()) {
                    player.sendMessage(S.toGreen("    已完成设置(怪物刷出点") + S.toGreen("已设置" + room.getMobSpawn().size() + "个)"));
                } else {
                    player.sendMessage(S.toAqua("    等待大厅     ") + ((room.getLobby() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    出生点大厅  ") + ((room.getSpawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    重生点       ") + ((room.getRespawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    怪物刷出点  ") +
                            ((room.getMobSpawn().size() == 0) ? S.toRed("未设置") : S.toGreen("已设置" + room.getMobSpawn().size() + "个")));
                }
                player.sendMessage(S.toAqua("    最小/大玩家数量   ") + S.toGreen(room.getMinPlayer() + " / " + room.getMaxPlayer()));
                player.sendMessage(S.toAqua("    玩家   ") + S.toGreen(room.getPlayersNames().toString()));
                player.sendMessage(S.toAqua("    状态   ") + S.toGreen(room.getStatus().name()));
                return true;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("create")) {
                    if (args[0].equalsIgnoreCase("list")) {
                        player.sendMessage(S.toPrefixRed("请换个名字" + ""));
                        return true;
                    }
                    if (RoomData.rooms.get(args[0]) != null) {
                        player.sendMessage(S.toPrefixRed("副本房间" + args[0] + "已经存在"));
                        return true;
                    }
                    RoomData room = new RoomData(args[0]);
                    RoomData.rooms.put(args[0], room);
                    room.setMaxPlayer(10);
                    room.setMinPlayer(2);
                    player.sendMessage(S.toPrefixGreen("成功创建副本房间 " + args[0]));
                    player.sendMessage(S.toPrefixGreen("请继续设置大厅、出生点、重生点和怪物刷出点"));
                    return true;
                }
                RoomData room = RoomData.rooms.get(args[0]);
                if (room == null) {
                    player.sendMessage(S.toPrefixRed("副本房间 " + args[0] + " 不存在"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("spawn")) {
                    room.setSpawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的出生点"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("respawn")) {
                    room.setRespawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的重生点"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("lobby")) {
                    room.setLobby(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的等待大厅"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        room.addMobSpawn(player.getLocation());
                        player.sendMessage(S.toPrefixGreen("成功添加 " + args[0] + " 的怪物刷出点"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        List<Location> locations = room.getMobSpawn();
                        player.sendMessage(S.toPrefixGreen(args[0] + "的怪物刷出点列表"));
                        for (int i = 0; i < locations.size(); i++) {
                            player.sendMessage(S.toGray(" [" + i + "]") +
                                    S.toAqua(" X:" + locations.get(i).getBlockX() + ", Y:" + locations.get(i).getBlockY() + ", Z" + locations.get(i).getBlockZ()));
                        }
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("delete") && args.length > 3) {
                        int index = 0;
                        try {
                            index = Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[3] + "不是数字"));
                            return true;
                        }
                        if (room.deleteMobSpawn(index) == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "不是有效数字"));
                        } else
                            player.sendMessage(S.toPrefixGreen("成功删除 " + args[0] + " 的怪物刷出点"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("clear")) {
                        room.clearMobSpawn();
                        player.sendMessage(S.toPrefixGreen("成功清空 " + args[0] + " 的怪物刷出点"));
                        return true;
                    }
                }
                if (args[1].equalsIgnoreCase("min")) {
                    int min = 0;
                    try {
                        min = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "不是数字"));
                        return true;
                    }
                    if (min > room.getMaxPlayer()) {
                        player.sendMessage(S.toPrefixRed("不能超过最大玩家数量" + room.getMaxPlayer()));
                        return true;
                    }
                    room.setMinPlayer(min);
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的最小玩家数量"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("max")) {
                    int max = 0;
                    try {
                        max = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "不是数字"));
                        return true;
                    }
                    if (max < room.getMinPlayer()) {
                        player.sendMessage(S.toPrefixRed("不能小于最小玩家数量" + room.getMinPlayer()));
                        return true;
                    }
                    room.setMaxPlayer(max);
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的最大玩家数量"));
                    return true;
                }
                player.sendMessage(S.toPrefixRed("未知命令"));
                return true;
            } else {
                player.sendMessage(S.toPrefixRed("未知命令"));
                return true;
            }
        }
        return false;
    }

    private void sendHelpPage1(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 1 页"));
        player.sendMessage(S.toBoldGreen("/" + label + " list") + S.toGray(" - ") + S.toGold("创建新的副本房间"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名>") + S.toGray(" - ") + S.toGold("查看副本房间信息"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> create") + S.toGray(" - ") + S.toGold("创建新的副本房间"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> spawn") + S.toGray(" - ") + S.toGold("设置副本房间出生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> respawn") + S.toGray(" - ") + S.toGold("设置副本房间重生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> lobby") + S.toGray(" - ") + S.toGold("设置副本房间大厅"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob add") + S.toGray(" - ") + S.toGold("添加副本房间怪物刷出点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob list") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob delete <编号>") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob clear") + S.toGray(" - ") + S.toGold("清除副本房间怪物刷出点列表"));
        player.sendMessage(S.toYellow("/" + label + " help 2") + S.toGray(" - ") + S.toGold("第二页"));

    }

    private void sendHelpPage2(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 2 页"));
        player.sendMessage(S.toBoldGreen("/" + label + " min <玩家数量>") + S.toGray(" - ") + S.toGold("副本房间设置副本房间最小玩家数量"));
        player.sendMessage(S.toBoldGreen("/" + label + " max <玩家数量>") + S.toGray(" - ") + S.toGold("副本房间设置副本房间最大玩家数量"));
    }

}
