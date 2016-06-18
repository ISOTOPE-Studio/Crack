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
                sender.sendMessage(S.toPrefixRed("你没有权限"));
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
                sender.sendMessage(S.toPrefixRed("必须要玩家才能执行"));
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
                String result = S.toPrefixAqua("列表: ");
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
                String result = S.toPrefixAqua("怪物列表: ");
                player.sendMessage(result + Mob.mobs.toString());
                return true;
            }
            if (args.length == 1) {
                Room room = Room.rooms.get(args[0]);
                if (room == null) {
                    player.sendMessage(S.toPrefixRed("副本房间 " + args[0] + " 不存在"));
                    return true;
                }
                player.sendMessage(S.toPrefixGreen("副本房间 " + args[0] + " 的信息"));
                if (room.isFinish()) {
                    player.sendMessage(S.toGreen("    已完成设置(怪物刷出点") + S.toGreen("已设置" + room.getMobSpawnObj().size() + "个)"));
                } else {
                    player.sendMessage(S.toAqua("    等待大厅     ") + ((room.getLobby() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    出生点大厅  ") + ((room.getSpawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    重生点       ") + ((room.getRespawn() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                    player.sendMessage(S.toAqua("    怪物刷出点  ") +
                            ((room.getMobSpawnObj().size() == 0) ? S.toRed("未设置") : S.toGreen("已设置" + room.getMobSpawnObj().size() + "个")));
                    player.sendMessage(S.toAqua("    BOSS生成点      ") + ((room.getBossObj() == null) ? S.toRed("未设置") : S.toGreen("已设置")));
                }
                player.sendMessage(S.toAqua("    最小/大玩家数量   ") + S.toGreen(room.getMinPlayer() + " / " + room.getMaxPlayer()));
                player.sendMessage(S.toAqua("    玩家   ") + S.toGreen(room.getPlayersNames().toString()));
                player.sendMessage(S.toAqua("    状态   ") + S.toGreen(room.getStatus().name()));
                return true;
            }
            if (args.length > 1) {
                if (args[1].equalsIgnoreCase("create")) {
                    if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("moblist")) {
                        player.sendMessage(S.toPrefixRed("请换个名字"));
                        return true;
                    }
                    if (Room.rooms.get(args[0]) != null) {
                        player.sendMessage(S.toPrefixRed("副本房间" + args[0] + "已经存在"));
                        return true;
                    }
                    Room room = new Room(args[0]);
                    Room.rooms.put(args[0], room);
                    room.setMaxPlayer(10);
                    room.setMinPlayer(2);
                    player.sendMessage(S.toPrefixGreen("成功创建副本房间 " + args[0]));
                    player.sendMessage(S.toPrefixGreen("请继续设置大厅、出生点、重生点、BOSS生成点和怪物刷出点"));
                    return true;
                }
                Room room = Room.rooms.get(args[0]);
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
                if (args[1].equalsIgnoreCase("boss")) {
                    if (args.length > 2) {
                        Mob mob = Mob.mobs.get(args[2]);
                        if (mob == null) {
                            player.sendMessage(S.toPrefixRed(args[2] + "不是是有效的怪物类型"));
                            return true;
                        }
                        room.setBossLocation(player.getLocation(), mob);
                        player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + " 的BOSS生成点"));
                        return true;
                    }
                    player.sendMessage(S.toBoldGreen("/" + label + " <房间名> boss <怪物类型>") + S.toGray(" - ") + S.toGold("设置副本房间BOSS生成点"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("mob") && args.length > 2) {
                    if (args[2].equalsIgnoreCase("add")) {
                        if (!(args.length > 5)) {
                            player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob add <怪物类型> <刷新频率/s> <刷新次数>") + S.toGray(" - ") + S.toGold("添加副本房间怪物刷出点"));
                            return true;
                        }

                        Mob mob = Mob.mobs.get(args[3]);
                        if (mob == null) {
                            player.sendMessage(S.toPrefixRed(args[3] + "不是是有效的怪物类型"));
                            return true;
                        }

                        int freq;
                        try {
                            freq = Integer.parseInt(args[4]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[4] + "不是数字"));
                            return true;
                        }

                        int limit;
                        try {
                            limit = Integer.parseInt(args[5]);
                        } catch (Exception e) {
                            player.sendMessage(S.toPrefixRed(args[5] + "不是数字"));
                            return true;
                        }

                        room.addMobSpawn(player.getLocation(), mob, freq, limit);
                        player.sendMessage(S.toPrefixGreen("成功添加 " + args[0] + " 的怪物刷出点"));
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("list")) {
                        List<MobSpawnObj> locations = room.getMobSpawnObj();
                        player.sendMessage(S.toPrefixGreen(args[0] + "的怪物刷出点列表"));
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
                if (args[1].equalsIgnoreCase("kill")) {
                    int count;
                    try {
                        count = Integer.parseInt(args[2]);
                    } catch (Exception e) {
                        player.sendMessage(S.toPrefixRed(args[2] + "不是数字"));
                        return true;
                    }
                    if (count < 0) {
                        player.sendMessage(S.toPrefixRed("不能小于0"));
                        return true;
                    }
                    room.setKillRequire(count);
                    player.sendMessage(S.toPrefixGreen("成功设置 " + args[0] + "BOSS前需击杀的怪物数量"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("min")) {
                    int min;
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
                    int max;
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
        player.sendMessage(S.toBoldGreen("/" + label + " list") + S.toGray(" - ") + S.toGold("查看副本房间"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名>") + S.toGray(" - ") + S.toGold("查看副本房间信息"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> create") + S.toGray(" - ") + S.toGold("创建新的副本房间"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> spawn") + S.toGray(" - ") + S.toGold("设置副本房间出生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> respawn") + S.toGray(" - ") + S.toGold("设置副本房间重生点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> lobby") + S.toGray(" - ") + S.toGold("设置副本房间大厅"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> boss <怪物类型>") + S.toGray(" - ") + S.toGold("设置副本房间BOSS生成点"));
        player.sendMessage(S.toYellow("/" + label + " help 2") + S.toGray(" - ") + S.toGold("第二页"));
    }

    private void sendHelpPage2(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 2 页"));
        player.sendMessage(S.toBoldGreen("/" + label + " moblist") + S.toGray(" - ") + S.toGold("查看已设置的怪物类型"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob add <怪物类型> <刷新频率/s> <刷新次数>") + S.toGray(" - ") + S.toGold("添加副本房间怪物刷出点"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob list") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob delete <编号>") + S.toGray(" - ") + S.toGold("查看副本房间怪物刷出点列表"));
        player.sendMessage(S.toBoldGreen("/" + label + " <房间名> mob clear") + S.toGray(" - ") + S.toGold("清除副本房间怪物刷出点列表"));
        player.sendMessage(S.toYellow("/" + label + " help 3") + S.toGray(" - ") + S.toGold("第三页"));
    }

    private void sendHelpPage3(CommandSender player, String label) {
        player.sendMessage(S.toPrefixGreen("帮助菜单 第 3 页"));
        player.sendMessage(S.toBoldGreen("/" + label + " kill <怪物数量>") + S.toGray(" - ") + S.toGold("设置BOSS前需击杀的怪物数量"));
        player.sendMessage(S.toBoldGreen("/" + label + " min <玩家数量>") + S.toGray(" - ") + S.toGold("设置副本房间最小玩家数量"));
        player.sendMessage(S.toBoldGreen("/" + label + " max <玩家数量>") + S.toGray(" - ") + S.toGold("设置副本房间最大玩家数量"));
    }

}
