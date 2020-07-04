package network.battlecraft.winglessentials.gamemode;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	ServerTracker st;
	BanList bl;
	
	@Override
	public void onEnable() {
		bl = Bukkit.getBanList(BanList.Type.NAME);
		st = new ServerTracker();
		st.begin(getServer(),getName());
	}
	
	@Override
	public void onDisable() {
		//server off
		getServer().getScheduler().cancelTasks(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			//If sender is console
			sender.sendMessage("Console Users Don't Need Gamemode!");
			return true;
		}
		
		String permission;
		Player player = (Player) sender;
		switch (label.toLowerCase()) {
			case "wegmc":
				permission = "we.gamemode.gmc";
				if (player.hasPermission(permission)) { 
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Creative!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegma":
				permission = "we.gamemode.gma";
				if (player.hasPermission(permission)) { 
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Adventure!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegmsp":
				permission = "we.gamemode.gmsp";
				if (player.hasPermission(permission)) { 
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Spectator!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegms":
				permission = "we.gamemode.gms";
				if (player.hasPermission(permission)) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Survival!"));
					return true;
				} else { noPermissionMessage(player); }
				return true;
				
			case "wefly":
				permission = "we.flight.fly";
				if (player.hasPermission(permission)) {
					boolean flightClearance;
					
					if (args.length == 0) {
						flightClearance = !player.getAllowFlight();
					} else if ((args[0].toLowerCase() == "t") || (args[0].toLowerCase() == "true") || (args[0].toLowerCase() == "1")) {
						flightClearance = true;
					} else if ((args[0].toLowerCase() == "f") || (args[0].toLowerCase() == "false") || (args[0].toLowerCase() == "0")) {
						flightClearance = false;
					} else {
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Error: Invalid parameter: \"" + args[0] + "\"");
						return true;
					}
					
					player.setAllowFlight(flightClearance);
					
					String temp = "Disabled";
					if (flightClearance) { temp = "Enabled"; }
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Flying " + temp + "!"));
				} else {
					player.setAllowFlight(false);
					player.setFlying(false);
					noPermissionMessage(player);
				}
				
				return true;
				
			case "wespd":
				permission = "we.flight.speed";
				if (player.hasPermission(permission)) {
					if (args.length == 0) {
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Must specify a speed!");
						return true;
					}
					int value = (int) Float.parseFloat(args[0]);
					if ((value < 1) || (value > 10)) {
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Speed parameter outside command bounds!");
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Must be between 1 and 10!");
					} else { player.setFlySpeed((float)value / 10); }
				} else { noPermissionMessage(player); }
				return true;
				
			case "weping":
				permission = "we.misc.ping";
                if (player.hasPermission(permission)) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Ping");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&ePong"));
                } else { noPermissionMessage(player); }
				return true;
				
			case "weban":
				permission = "we.punish.ban";
                if (player.hasPermission(permission)) {
                	String reason = null;
                	String playerName = null;
                	Date expiry = null;
                	String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
                	
                	switch (args.length) {
                		case 0:
                			player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Must specify a player!");
                			return true;
                		case 1:
                			//Player was specified
                			playerName = args[0];
                		case 2:
                			//Player and reason were specified
                			playerName = args[0];
                			reason = bumper + args[1] + bumper;
                		case 3:
                			//Player, reason and expiry date were specified
                			playerName = args[0];
                			reason = bumper + args[1] + bumper;
                			//expiry = ;
                	}
                	
                	Player result = null;
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		if (p.getName() == playerName) {
                			result = p;
                		}
                	}
                	
                	if (result == null) {
                		player.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+"Could not find player!");
                		return true;
                	}
                	
                	result.kickPlayer(reason);
                	bl.addBan(playerName,reason, expiry, null);
                	
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		if (p.hasPermission(permission)) {
                			p.sendMessage(playerName + " has been banned from the server by " + player);
                		}
                    }
                } else { noPermissionMessage(player); }
				return true;
				
			case "wekick":
				permission = "we.punish.kick";
                if (player.hasPermission(permission)) {
                	if (args.length == 0) {
                		player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Must specify a player!");
                	}
                	
                	String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
                	player.kickPlayer(bumper + args[0] + bumper);
                	
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		if (player.hasPermission(permission)) {
                			p.sendMessage(player.getName() + " has been kicked from the server by " + player);
                		}
                    }
                } else { noPermissionMessage(player); }
				return true;
				
			case "wekickall":
				permission = "we.punish.kickall";
                if (player.hasPermission(permission)) {
                	if (args.length == 0) {
                		player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Must specify a player!");
                	}
                	
                	String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		if (!p.isOp()) {
                			p.kickPlayer(bumper + args[0] + bumper);
                		} else {
                			p.sendMessage("All non-OP players have been kicked by " + player);
                		}
                    }
                } else { noPermissionMessage(player); }
				return true;
		}
		
		return false;
	}
	
	public void noPermissionMessage(Player p) {
		p.sendMessage(ChatColor.DARK_RED + " " + ChatColor.BOLD + "You don't Have permission to use this!");
	}
	
	public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}


 	