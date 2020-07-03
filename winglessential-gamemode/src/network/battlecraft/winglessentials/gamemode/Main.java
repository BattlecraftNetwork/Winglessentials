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
		
	}
	
	//@Override
	//public void onEnable() {
	//	  //basically when server starts
	//	  PluginManager pm = getServer().getPluginManager();
    //    Server plugin = RolePlay.getPlugin(RolePlay.class).getServer();
	
    //    pm.registerEvents(this, this);
    //
    //    getLogger().info("Plugin Successfully Booted!");
	//}
	
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
					return true;
				} else { noPermissionMessage(player); return true; }
				break;
				
			case "wegma":
				if (player.hasPermission("we.gamemode.gma")) { 
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Adventure!"));
					return true;
				} else { noPermissionMessage(player); return true; }
				break;
				
			case "wegmsp":
				if (player.hasPermission("we.gamemode.gmsp")) { 
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Spectator!"));
					return true;
				} else { noPermissionMessage(player); return true; }
				break;
				
			case "wegmsp":
				if (player.hasPermission("we.gamemode.gms")) { 
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Survival!"));
					return true;
				} else { noPermissionMessage(player); return true; }
				break;
				
			
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


 	