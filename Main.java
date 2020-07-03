package network.battlecraft.HelloWorld;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		//basically when server starts
		PluginManager pm = getServer().getPluginManager();
        Server plugin = RolePlay.getPlugin(RolePlay.class).getServer();

        pm.registerEvents(this, this);

        getLogger().info("Role Play Plugin Successfully Booted!");
	}
	
	@Override
	public void onDisable() {
		//server off
		getLogger().info("Role Play Plugin Successfully Shutdown!");
	}
	
	// /truth = response of "alex stinks"
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
	}
	
	public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}
