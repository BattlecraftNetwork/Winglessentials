package network.battlecraft.winglessentials.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		//server on
		
		//This prevents people flying on the server from being kicked if you haven't set it in server.properties
		((CraftServer) plugin.getServer()).getHandle().getServer().setAllowFlight(true);
		
		//This is a sneaky copy-paste of another site on how to track tps
		//getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		//	long sec;
		//	int ticks;
		//	
		//	@Override
		//	public void run() {
		//		sec = (System.currentTimeMillis() / 1000);
		//		
		//		if(second == sec) {
		//			ticks++;
		//		} else {
		//			second = sec;
		//			tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
		//			ticks = 0;
		//			
		//			System.out.print("TPS = " + tps);
		//		}
		//	}
		//}, 20, 1);
	}
	
	@Override
	public void onDisable() {
		//server off
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			//If sender is console
			sender.sendMessage("Console Users Don't Need Gamemode!");
			return true;
		}
		
		Player player = (Player) sender;
		switch label.toLowerCase() {
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
				
			case "wegmsp":
				if (player.hasPermission("we.gamemode.gms")) { 
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Survival!"));
					return true;
				} else { noPermissionMessage(player); }
				return true;
				
			case "wefly":
				if (player.hasPermission("we.gamemode.fly")) { 
					//player.setGameMode(GameMode.SURVIVAL);
					boolean flightClearance = !player.getAllowFlight​();
					player.setAllowFlight​(flightClearance);
					
					String temp = "Disabled";
					if (flightClearance) { temp = "Enabled"; }
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Flying " + temp + "!"));
				} else {
					player.setAllowFlight​(false);
					player.setFlying(false);
					noPermissionMessage(player);
				}
				
				return true;
				
			case "wespd":
				if (player.hasPermission("we.gamemode.wespd")) {
					player.setFlySpeed((float)args[0] / 10);
				} else { noPermissionMessage(player); }
				return true;
		}
	
		return false;
	}
	
	public void noPermissionMessage(Player p) {
		p.sendMessage(ChatColor.DARK_RED+ChatColor.BOLD+"You don't Have permission to use this!");
	}
	
	public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}


 	