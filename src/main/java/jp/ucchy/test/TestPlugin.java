/*
 * @author     ucchy
 * @license    LGPLv3
 * @copyright  Copyright ucchy 2014
 */
package jp.ucchy.test;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcbans.firestar.mcbans.MCBans;
import com.mcbans.firestar.mcbans.api.MCBansAPI;

/**
 * @author ucchy
 */
public class TestPlugin extends JavaPlugin {

    private MCBansAPI api;

    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ( args.length <= 1 ) {
            sender.sendMessage("There are too few arguments.");
            return true;
        }

        if ( args[0].equalsIgnoreCase("kick") ) {

            Player target = getPlayer(args[1]);
            if ( target == null ) {
                sender.sendMessage("Please specify an online player name.");
                return true;
            }

//            String tid = target.getUniqueId().toString();

            String senderName = sender.getName();
//            String senderId = (sender instanceof Player) ? ((Player)sender).getUniqueId().toString() : "";

//            getMCBansAPI().kick(target.getName(), tid, senderName, senderId, "this is debug kick.");
            getMCBansAPI().kick(target.getName(), "", senderName, "", "this is debug kick.");
            sender.sendMessage("Done!");
            return true;

        } else if ( args[0].equalsIgnoreCase("lban") ) {

            @SuppressWarnings("deprecation")
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if ( target == null ) {
                sender.sendMessage("Please specify a correctly player name.");
                return true;
            }

            String tid = target.getUniqueId().toString();

            String senderName = sender.getName();
            String senderId = (sender instanceof Player) ? ((Player)sender).getUniqueId().toString() : "";


            getMCBansAPI().localBan(target.getName(), tid, senderName, senderId, "this is debug ban.");
            sender.sendMessage("Done!");
            return true;
        }

        return false;
    }

    private static Player getPlayer(String name) {

        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( player.getName().equals(name) ) {
                return player;
            }
        }
        return null;
    }

    private MCBansAPI getMCBansAPI() {

        if ( api != null ) {
            return api;
        }

        MCBans mcbans = (MCBans)Bukkit.getPluginManager().getPlugin("MCBans");
        api = mcbans.getAPI(this);
        return api;
    }
}
