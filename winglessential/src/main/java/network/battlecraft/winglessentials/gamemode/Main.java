package network.battlecraft.winglessentials.gamemode;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	BanList bl;
	ServerTracker st;
	
	@Override
	public void onEnable() {
		bl = Bukkit.getBanList(BanList.Type.NAME);
		System.out.println("Plugin: 1");
		st = new ServerTracker();
		st.begin(getServer());
		System.out.println("Plugin: 2");
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
					String state = args[0].toLowerCase();
					
					if ((state == "t") || (state == "true") || (state == "1")) {
						flightClearance = true;
					} else if ((state == "f") || (state == "false") || (state == "0")) {
						flightClearance = false;
					} else if (state == null) {
						flightClearance = !player.getAllowFlight();
					} else {
						player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Error: Invalid argument!");
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
				permission = "we.flight.wespd";
				if (player.hasPermission(permission)) {
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
                	String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
                	player.kickPlayer(bumper + args[0] + bumper);
                	bl.addBan(player.getName(),bumper + args[0] + bumper, null, null);
                	
                	for(Player p : Bukkit.getOnlinePlayers()) {
                		if (player.hasPermission(permission)) {
                			p.sendMessage(player.getName() + " has been banned from the server by " + player);
                		}
                    }
                } else { noPermissionMessage(player); }
				return true;
				
			case "wekick":
				permission = "we.punish.kick";
                if (player.hasPermission(permission)) {
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


 	