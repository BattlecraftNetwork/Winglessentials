package network.battlecraft.winglessentials.gamemode;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class ServerTracker {
	Server server;
	Plugin pm;
	
	private int tps = 0;
	
	public void begin(Server server,String pluginName) {
		this.pm = server.getPluginManager().getPlugin(pluginName);
		this.server = server;
		
		//This is a sneaky copy-paste of another site on how to track tps
		server.getScheduler().scheduleSyncRepeatingTask(pm, new Runnable() {
			long sec;
			long second;
			int ticks;
		
			@Override
			public void run() {
				sec = (System.currentTimeMillis() / 1000);
			
				if(second == sec) { ticks++; } else {
					second = sec;
					tps = (tps == 0 ? ticks : ((tps + ticks) / 2));
					ticks = 0;
				}
			}
		}, 20, 1);
	}
}
