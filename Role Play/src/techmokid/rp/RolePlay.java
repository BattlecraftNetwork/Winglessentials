package techmokid.rp;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RolePlay extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        Server plugin = RolePlay.getPlugin(RolePlay.class).getServer();

        pm.registerEvents(this, this);

        getLogger().info("Role Play Plugin Successfully Booted!");
    }

    @Override
    public void onDisable() { getLogger().info("Role Play Plugin Successfully Shutdown!"); }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage().toLowerCase();

    }

    public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}
