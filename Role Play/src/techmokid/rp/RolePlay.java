package techmokid.rp;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.*;

class Role { Player player; String role; String target; }

class RoleManager {
    List<Role> roles = new ArrayList<Role>();

    public void setRoles(String gameMode) {
        List<Role> roles = new ArrayList<Role>();

        int i = 0;
        int numberOfNonSurvivors = 1;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (gameMode == "Hunted") { if (i < numberOfNonSurvivors) { Role r = new Role(); r.player = p;  r.role = "Hunter"; roles.add(r);
                } else { Role r = new Role(); r.player = p;  r.role = gameMode; roles.add(r); }
            } else if (gameMode == "Blink") { if (i < numberOfNonSurvivors) { Role r = new Role(); r.player = p;  r.role = "Angel"; roles.add(r);
                } else { Role r = new Role(); r.player = p;  r.role = gameMode; roles.add(r); }
            } else if (gameMode == "Apocalypse") { if (i < numberOfNonSurvivors) { Role r = new Role(); r.player = p; r.role = "Zombie"; roles.add(r);
                } else { Role r = new Role(); r.player = p; r.role = gameMode; roles.add(r); }
            }

            i++;
        }

        //We have generated the roles. Now randomly shuffle the list
        List<Role> tempRoleList = new ArrayList<Role>(roles);
        roles = new ArrayList<Role>();

        List<Integer> indexes_1 = new ArrayList<>();
        int x = 0; for (Role r : tempRoleList) { indexes_1.add(x); x++; }
        Collections.shuffle(indexes_1);

        x = 0;
        for (Role r : tempRoleList) {
            Role tempRole = new Role();
            tempRole.player = tempRoleList.get(indexes_1.get(x)).player;
            tempRole.role = tempRoleList.get(x).role;
            tempRole.target = tempRoleList.get(x).target;
            roles.add(tempRole);
        }
    }

    public Role getRole(Player p) { for (Role r : roles) { if (r.player == p) { return r; } } return null; }
}

public class RolePlay extends JavaPlugin implements Listener {
    RoleManager roles;
    String commandStarter = "!";

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

        if (!msg.startsWith(commandStarter)) { return; }
        if (msg == "!help") {
            e.getPlayer().sendMessage("\"Role Play\" Help Page:");
            e.getPlayer().sendMessage(" - !begin: This starts role selection. Alternative name \"!start\"");
            e.getPlayer().sendMessage(" - !help: Display this screen");
            e.getPlayer().sendMessage(" - !role: Check your current role");
        } else if ((msg == "!begin") || (msg == "!start")) {
            if (Bukkit.getOnlinePlayers().size() < 4) {
                e.getPlayer().sendMessage("\"Role Play\" - There aren't enough players to perform that action!");
                e.getPlayer().sendMessage("\"Role Play\" - Players: " + Bukkit.getOnlinePlayers().size() + "/4");
                return;
            }

            //We have enough players to start the role selection
            sendGlobalMessage("Starting Role Selection...",true);

            roles = new RoleManager();
            roles.setRoles("Apocalypse");

            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("Your secret role for this game is: " + roles.getRole(p).role);
                if (roles.getRole(p).target != null) { p.sendMessage(" - Your target is: " + roles.getRole(p).target); }
            }

            sendGlobalMessage("Tip to all: For those who have a special role, try to blend in with the others...",true);
        } else if (msg == "!role") {
            if (roles != null) {
                e.getPlayer().sendMessage("Your role for this game is: " + roles.getRole(e.getPlayer()).role);
            } else {
                e.getPlayer().sendMessage("Roles have not been assigned yet! Use \"!begin\" or \"!start\" to assign roles");
            }
        }
    }

    public void sendGlobalMessage(String msg) {sendGlobalMessage(msg,false);}
    public void sendGlobalMessage(String msg, boolean showModName) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("\"Role Play\"" + msg);
        }
    }
}
