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
		if (label.equalsIgnoreCase("wegmc")) {
			if (player.hasPermission("we.gamemode.gmc")) { 
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Creative!"));
				return true;
			} else { noPermissionMessage(player); return true; }
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


 	