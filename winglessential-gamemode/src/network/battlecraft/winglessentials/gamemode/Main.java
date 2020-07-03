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
	//	//basically when server starts
	//	PluginManager pm = getServer().getPluginManager();
    //    Server plugin = RolePlay.getPlugin(RolePlay.class).getServer();
	
    //    pm.registerEvents(this, this);
    //
    //    getLogger().info("Role Play Plugin Successfully Booted!");
	//}
	
	@Override
	public void onDisable() {
		//server off
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("wegmc")) {
			if (sender instanceof Player) {
				//player (not console)
				Player player = (Player) sender;
				if (player.hasPermission("we.gamemode.gmc")) { 
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.translateAlternateColorCodes((char) 0, "Gamemode Changed To Creative!"));
					return true;
				}
					player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"You don't Have permission to use this!");
					return true;
			} else {
				//console (not player)
				sender.sendMessage("Console Users Don't Need Gamemode!");
				return true;
			}
		}
	
		return false;
	}
	
	public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}


 	