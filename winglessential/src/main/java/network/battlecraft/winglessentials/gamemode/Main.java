package network.battlecraft.winglessentials.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
	ServerTracker st;
	
	@Override
	public void onEnable() {
		st = new ServerTracker();
		st.begin(getServer());
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
		
		Player player = (Player) sender;
		switch (label.toLowerCase()) {
			case "wegmc":
				if (player.hasPermission("we.gamemode.gmc")) { 
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Creative!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegma":
				if (player.hasPermission("we.gamemode.gma")) { 
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Adventure!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegmsp":
				if (player.hasPermission("we.gamemode.gmsp")) { 
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Spectator!"));
				} else { noPermissionMessage(player); }
				return true;
				
			case "wegms":
				if (player.hasPermission("we.gamemode.gms")) { 
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Survival!"));
					return true;
				} else { noPermissionMessage(player); }
				return true;
				
			case "wefly":
				if (player.hasPermission("we.gamemode.fly")) { 
					//player.setGameMode(GameMode.SURVIVAL);
					boolean flightClearance = !player.getAllowFlight();
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
				if (player.hasPermission("we.gamemode.wespd")) {
					player.setFlySpeed(Float.parseFloat(args[0]) / 10);
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


 	