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
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
                sendHelp(sender, label);
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("crack.admin")) {
                sender.sendMessage(S.toPrefixRed("你没有权限"));
                return true;
            }
            if (args.length > 1 && !args[0].equalsIgnoreCase("help")) {
                if (args[1].equalsIgnoreCase("check")) {
                    RoomData room = new RoomData(args[0]);
                    if (!room.isExist()) {
                        player.sendMessage(S.toPrefixRed("副本房间" + args[0] + "不存在"));
                        return true;
                    }
                    player.sendMessage(S.toPrefixGreen("副本房间 " + args[0] + " 的信息"));
                    player.sendMessage(S.toAqua("    等待大厅     ") + ((room.getLobby() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    出生点大厅  ") + ((room.getSpawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    重生点       ") + ((room.getRespawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    怪物刷出点  ") +
                            ((room.getMobSpawn().size() == 0) ? S.toRed("未设置") : S.toGreen("已设置" + room.getMobSpawn().size() + "个")));
                    return true;
                }
                if (args[1].equalsIgnoreCase("spawn")) {
                    new RoomData(args[0]).setSpawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的出生点"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("respawn")) {
                    new RoomData(args[0]).setRespawn(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的重生点"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("lobby")) {
                    new RoomData(args[0]).setLobby(player.getLocation());
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的等待大厅"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        new RoomData(args[0]).addMobSpawn(player.getLocation());
                        player.sendMessage(S.toPrefixGreen("成功添加 " + args[0] + " 的怪物刷出点"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        List<Location> locations = new RoomData(args[0]).getMobSpawn();
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
                            Integer.parseInt(args[3]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[3] + "不是数字"));
                            return true;
                        }
                        Location location = new RoomData(args[0]).deleteMobSpawn(index);
                        if (location == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "不是有效数字"));
                        } else
                            player.sendMessage(S.toPrefixGreen("成功删除 " + args[0] + " 的怪物刷出点"));
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
        player.sendMessage(S.toPrefixGreen("帮助菜单"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名>") + S.toGray(" - ") + S.toGold("查看副本房间信息"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> spawn") + S.toGray(" - ") + S.toGold("设置副本房间出生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> respawn") + S.toGray(" - ") + S.toGold("设置副本房间重生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> lobby") + S.toGray(" - ") + S.toGold("设置副本房间大厅"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob add") + S.toGray(" - ") + S.toGold("添加副本房间怪物刷出点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob list") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob delete <编号>") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
    }

}
