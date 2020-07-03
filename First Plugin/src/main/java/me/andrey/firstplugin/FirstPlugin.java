package me.andrey.firstplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

//import static spark.Spark.*;
//import com.mixer.*;

class RespawnShulkers extends BukkitRunnable {
    //IGNORE THIS FUNCTION! This function is a WIP and currently does not do anything
    //Once complete, it is supposed to respawn shulkers randomly around end cities, but at a very slow rate
    //Therefore, you can farm shulkers, but it will take a long time
    private final JavaPlugin plugin;

    public RespawnShulkers(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        //Spawn more shulkers if there aren't the max amount in the spawner/end city
        for(Player p : Bukkit.getOnlinePlayers()){
            World world = p.getWorld();
            Location nearestStructure;
            StructureType structure = StructureType.END_CITY;

            try {
                nearestStructure = world.locateNearestStructure(p.getLocation(), structure, 128, false);
            } catch (Exception e) { return; }

            if (nearestStructure != null) {
                //INCOMPLETE SECTION
                //Found end city near player. Actual spawning protocols can finally commence around the nearest end city structure

            }
        }
    }
}

public final class FirstPlugin extends JavaPlugin implements Listener {
    private String[] defaultKeyArray = new String[] {"IncreaseMovementSpeedOnLadders"};
    private String defaultKeys = "";
    private String filepath = "pluginSettings.txt";
    private static File pluginSettingsFile;
    private boolean looped = true;

    public static boolean writeCheck(File file) {
        boolean f = true;
        if(!file.exists()) try {
            if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.err.println("writeCheck error: Cannot create parent file " + file.getParentFile().getAbsolutePath());
                f = false;
            }
            if(!file.createNewFile()) {
                System.err.println("writeCheck error: Cannot create file " + file.getAbsolutePath());
                f = false;
            }
        } catch(IOException e) { e.printStackTrace(); f = false; }
        if(!file.canWrite()) { f = false; }
        return f;
    }

    @Override
    public void onEnable() {
        //Plugin startup logic-----------------------------------------------------------------------------------------
        //Runs once when the plugin/server is first started.
        System.out.println("[MC Upgrade Plugin]: Plugin Starting...");
        PluginManager pm = getServer().getPluginManager();

        for(String x : defaultKeyArray) { defaultKeys += x + ":\n"; }

        //This section reads from the settings file to double check if the file exists. If not, it creates it with defaults
        pluginSettingsFile = new File(getDataFolder(), filepath);
        writeCheck (pluginSettingsFile);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(pluginSettingsFile);
            fileWriter.write(defaultKeys);
        } catch (IOException e) { e.printStackTrace(); } finally { try { fileWriter.close(); } catch (IOException e) { e.printStackTrace(); } }

        //Schedule the task to occur once every 100 seconds
        BukkitTask TaskName = new RespawnShulkers(this).runTaskTimer(this, 20, 5 * 20);

        //Different recipes for crafting a stick------------------------------------------------------------------------
        stickRecipe(Material.OAK_LOG);
        stickRecipe(Material.ACACIA_LOG);
        stickRecipe(Material.BIRCH_LOG);
        stickRecipe(Material.DARK_OAK_LOG);
        stickRecipe(Material.JUNGLE_LOG);
        stickRecipe(Material.SPRUCE_LOG);

        //Crafting recipe for 4 sticks makes 2 planks (Reverse 2 planks to 4 sticks)------------------------------------
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.OAK_PLANKS, 2));
        shapelessRecipe.addIngredient(4, Material.STICK);
        Plugin plugin = FirstPlugin.getPlugin(FirstPlugin.class);
        plugin.getServer().addRecipe(shapelessRecipe);

        //Set normal stone to be able to be used for tools
        //Pickaxe-------------------------------------------------------------------------------------------------------
        ShapedRecipe sr = new ShapedRecipe(new ItemStack(Material.STONE_PICKAXE, 1));
        sr.shape("###"," @ "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);

        //Axe-----------------------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.STONE_AXE, 1));
        sr.shape(" ##"," @#"," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.STONE_AXE, 1));
        sr.shape("## ","#@ "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);

        //Shovel--------------------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.STONE_SHOVEL, 1));
        sr.shape(" # "," @ "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);

        //Hoe-----------------------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.STONE_HOE, 1));
        sr.shape(" ##"," @ "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.STONE_HOE, 1));
        sr.shape("## "," @ "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);

        //Sword---------------------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.STONE_SWORD, 1));
        sr.shape(" # "," # "," @ ");
        sr.setIngredient('#', Material.STONE);
        sr.setIngredient('@', Material.STICK);
        plugin.getServer().addRecipe(sr);

        //Set Zombie flesh to cook into leather-------------------------------------------------------------------------
        FurnaceRecipe fr = new FurnaceRecipe(new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH);
        fr.setCookingTime(64);
        fr.setExperience(5);
        plugin.getServer().addRecipe(fr);

        //Set Ghast Tear to cook into Nether Quartz---------------------------------------------------------------------
        fr = new FurnaceRecipe(new ItemStack(Material.QUARTZ), Material.GHAST_TEAR);
        fr.setCookingTime(128);
        fr.setExperience(10);
        plugin.getServer().addRecipe(fr);

        //Chest from Logs-----------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.OAK_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.BIRCH_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.SPRUCE_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.ACACIA_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.DARK_OAK_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.CHEST, 4));
        sr.shape("###","# #","###");
        sr.setIngredient('#', Material.JUNGLE_LOG);
        plugin.getServer().addRecipe(sr);

        //Make doors----------------------------------------------------------------------------------------------------
        sr = new ShapedRecipe(new ItemStack(Material.OAK_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.OAK_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.OAK_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.OAK_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.BIRCH_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.BIRCH_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.BIRCH_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.BIRCH_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.SPRUCE_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.SPRUCE_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.SPRUCE_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.SPRUCE_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.ACACIA_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.ACACIA_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.ACACIA_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.ACACIA_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.DARK_OAK_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.DARK_OAK_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.DARK_OAK_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.DARK_OAK_LOG);
        plugin.getServer().addRecipe(sr);

        sr = new ShapedRecipe(new ItemStack(Material.JUNGLE_DOOR, 12));
        sr.shape(" ##"," ##"," ##");
        sr.setIngredient('#', Material.JUNGLE_LOG);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.JUNGLE_DOOR, 12));
        sr.shape("## ","## ","## ");
        sr.setIngredient('#', Material.JUNGLE_LOG);
        plugin.getServer().addRecipe(sr);

        //Set Saddle Recipe
        sr = new ShapedRecipe(new ItemStack(Material.SADDLE, 1));
        sr.shape("###","@ @","$ $");
        sr.setIngredient('#', Material.LEATHER);
        sr.setIngredient('@', Material.STRING);
        sr.setIngredient('$', Material.TRIPWIRE_HOOK);
        plugin.getServer().addRecipe(sr);

        //Set horse armour recipe
        sr = new ShapedRecipe(new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        sr.shape("#  ","###","# #");
        sr.setIngredient('#', Material.DIAMOND);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));
        sr.shape("#  ","###","# #");
        sr.setIngredient('#', Material.GOLD_INGOT);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.IRON_HORSE_ARMOR, 1));
        sr.shape("#  ","###","# #");
        sr.setIngredient('#', Material.IRON_INGOT);
        plugin.getServer().addRecipe(sr);
        sr = new ShapedRecipe(new ItemStack(Material.LEATHER_HORSE_ARMOR, 1));
        sr.shape("#  ","###","# #");
        sr.setIngredient('#', Material.LEATHER);
        plugin.getServer().addRecipe(sr);

        //Set red dye from sweet berries--------------------------------------------------------------------------------
        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.RED_DYE, 1));
        shapelessRecipe.addIngredient(1, Material.SWEET_BERRIES);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.GRASS_BLOCK, 1));
        shapelessRecipe.addIngredient(1, Material.DIRT);
        shapelessRecipe.addIngredient(1, Material.WHEAT_SEEDS);
        shapelessRecipe.addIngredient(1, Material.BONE_MEAL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.MYCELIUM, 1));
        shapelessRecipe.addIngredient(1, Material.PODZOL);
        shapelessRecipe.addIngredient(1, Material.BROWN_MUSHROOM);
        shapelessRecipe.addIngredient(1, Material.BONE_MEAL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.MYCELIUM, 1));
        shapelessRecipe.addIngredient(1, Material.PODZOL);
        shapelessRecipe.addIngredient(1, Material.RED_MUSHROOM);
        shapelessRecipe.addIngredient(1, Material.BONE_MEAL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.STRING, 4));
        shapelessRecipe.addIngredient(1, Material.WHITE_WOOL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.BOOK, 1));
        shapelessRecipe.addIngredient(1, Material.ENCHANTED_BOOK);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.MOSSY_COBBLESTONE, 1));
        shapelessRecipe.addIngredient(1, Material.COBBLESTONE);
        shapelessRecipe.addIngredient(1, Material.SLIME_BALL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.MOSSY_STONE_BRICKS, 1));
        shapelessRecipe.addIngredient(1, Material.STONE_BRICKS);
        shapelessRecipe.addIngredient(1, Material.SLIME_BALL);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.QUARTZ, 4));
        shapelessRecipe.addIngredient(1, Material.QUARTZ_BLOCK);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.QUARTZ, 4));
        shapelessRecipe.addIngredient(1, Material.QUARTZ_PILLAR);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.QUARTZ, 4));
        shapelessRecipe.addIngredient(1, Material.CHISELED_QUARTZ_BLOCK);
        plugin.getServer().addRecipe(shapelessRecipe);

        shapelessRecipe = new ShapelessRecipe(new ItemStack(Material.QUARTZ, 4));
        shapelessRecipe.addIngredient(1, Material.SMOOTH_QUARTZ);
        plugin.getServer().addRecipe(shapelessRecipe);

        //Actually register the events
        pm.registerEvents(this, this);
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("You Left A Bed!");
    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("Goodnight " + player.getDisplayName() + "!");
    }

    @EventHandler
    public void onShearSheep(PlayerShearEntityEvent e) {
        Player player = e.getPlayer();
        int rand = new Random().nextInt(11);
        Location playerBlock = player.getLocation();

        if (rand == 1) {
            e.setCancelled(true);
            player.getWorld().dropItemNaturally(playerBlock, new ItemStack(Material.MUTTON, 1));
            LivingEntity entity = (LivingEntity) e.getEntity();
            entity.damage(1);
        }
    }

    @EventHandler
    public void onPlayerRightClickOnEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity.getType().equals(EntityType.CHICKEN)) {
            Player player = e.getPlayer();
            Material mat = player.getItemInHand().getType();

            if (mat.equals(Material.SHEARS)) {
                entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.FEATHER, 1));
                LivingEntity entityLiving = (LivingEntity) entity;
                entityLiving.damage(1);

                player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);

                if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                    player.setItemInHand(null);
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                }
            }
        }
    }

    private void resetCrop(PlayerInteractEvent e, Player player, Material mat, Material seed, Material fruit) {
        if (Objects.requireNonNull(e.getClickedBlock()).getType().equals(mat)) {
            BlockState bs = e.getClickedBlock().getState();
            Crops crop = (Crops) bs.getData();
            ItemStack itemInQuestion = player.getItemInHand();

            if (crop.getState().equals(CropState.RIPE)) {
                if(itemInQuestion.getType().equals(Material.BONE_MEAL)) {
                    if (itemInQuestion.getAmount() > 1) {
                        player.setItemInHand(new ItemStack(itemInQuestion.getType(), itemInQuestion.getAmount() - 1));
                    } else {
                        player.setItemInHand(null);
                    }

                    BlockState blockState = e.getClickedBlock().getState();
                    blockState.setData(new MaterialData(mat, (byte) 0x0));
                    blockState.update();

                    int random1 = new Random().nextInt(3);
                    int random2 = new Random().nextInt(100);

                    if (seed.equals(Material.POTATO) && (random2 == 1)) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.POISONOUS_POTATO, 1));
                    }

                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(seed, 1));

                    if (random1 != 0) {
                        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(fruit, random1));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (looped) {
            looped = false;
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Player player = e.getPlayer();
                resetCrop(e, player, Material.BEETROOTS, Material.BEETROOT_SEEDS, Material.BEETROOT);
                resetCrop(e, player, Material.CARROTS, Material.CARROT, Material.CARROT);
                resetCrop(e, player, Material.WHEAT, Material.WHEAT_SEEDS, Material.WHEAT);
                resetCrop(e, player, Material.POTATOES, Material.POTATO, Material.POTATO);

                Material mat = player.getItemInHand().getType();
                float Volume = 1;
                float pitch = 1;

                if (Objects.requireNonNull(e.getClickedBlock()).getType().equals(Material.PODZOL)) {
                    if (mat.equals(Material.STONE_SHOVEL)) {
                        e.getClickedBlock().setType(Material.DIRT);
                        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                        player.playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, Volume, pitch);
                        if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                            player.setItemInHand(null);
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, Volume, pitch);
                        }
                    }
                    if (mat.equals(Material.IRON_SHOVEL)) {
                        e.getClickedBlock().setType(Material.DIRT);
                        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                        player.playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, Volume, pitch);
                        if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                            player.setItemInHand(null);
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, Volume, pitch);
                        }
                    }
                    if (mat.equals(Material.DIAMOND_SHOVEL)) {
                        e.getClickedBlock().setType(Material.DIRT);
                        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                        player.playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, Volume, pitch);
                        if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                            player.setItemInHand(null);
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, Volume, pitch);
                        }
                    }
                    if (mat.equals(Material.GOLDEN_SHOVEL)) {
                        e.getClickedBlock().setType(Material.DIRT);
                        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                        player.playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, Volume, pitch);
                        if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                            player.setItemInHand(null);
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, Volume, pitch);
                        }
                    }
                    if (mat.equals(Material.WOODEN_SHOVEL)) {
                        e.getClickedBlock().setType(Material.DIRT);
                        player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
                        player.playSound(player.getLocation(), Sound.ITEM_SHOVEL_FLATTEN, Volume, pitch);
                        if (mat.getMaxDurability() == player.getItemInHand().getDurability()) {
                            player.setItemInHand(null);
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, Volume, pitch);
                        }
                    }
                }
            }
        } else {
            looped = true;
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        player.sendMessage("Wow, you really died?");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("Hello " + player.getDisplayName() + "! Welcome to the server!");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        player.sendMessage(player.getDisplayName() + " has left the game");
    }

    @EventHandler
    public void ladder(PlayerMoveEvent e) {
        boolean speedUpLadderSpeed = false;
        String ladderSpeedIncrease = findKey("IncreaseMovementSpeedOnLadders");
        if (ladderSpeedIncrease.equalsIgnoreCase("true")) {
            speedUpLadderSpeed = true;
        }

        Player p = e.getPlayer();
        Block currentBlock = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()).getBlock();

        if((currentBlock.getType().equals(Material.LADDER)) && !p.isOnGround() && speedUpLadderSpeed) {
            double rotation = p.getLocation().getDirection().getY();

            if (rotation > 0.85f) {
                p.setVelocity(new Vector(p.getVelocity().getX() * 10, 0.6, p.getVelocity().getZ() * 10));
            }
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        if (e.getMessage().toLowerCase().contains("lag")) {
            e.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String com = command.getName();
        Player player = null;
        String playerName;

        //If the console sent, then create a name for the console
        if (sender instanceof Player) {
            player = (Player) sender;
            playerName = player.getName();
        } else {
            playerName = "SYSTEM CONSOLE";
        }

        if (com.equalsIgnoreCase("damage")) {
            if (!playerName.equals("SYSTEM CONSOLE")) {
                player.getItemInHand().setDurability(Short.parseShort(args[0]));
            }
        }

        //Run Command if player has permissions
        if (player.hasPermission("FirstPlugin.alterWaypoints")) {
            String fileContents = "";
            BufferedReader br = null;

            //Setup the filereader. If the file doesn't exist, create it
            try {
                FileReader fr = new FileReader("C:/Users/Andrey/AppData/Roaming/.minecraft/Spigot Server/UserWaypoints.txt");
                br = new BufferedReader(fr);
            } catch (IOException e) {
                System.out.println("Could not open file: " + e);
            }

            //Read the data and split it up into an array of lines
            try {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    fileContents += line + "\n";
                }
            } catch (Exception e) {
                try {
                    br.close();
                } catch (IOException ex) {}
            }

            //Discover if the user exists in the file or not
            String[] fileContentsSplit = fileContents.split("\n");
            int looper = 0;
            boolean foundName = false;
            while (true) {
                try {
                    String username = fileContentsSplit[looper].split(":")[0].replaceAll(" ","");

                    if (username.equals(playerName)) {
                        foundName = true;
                        break;
                    }

                    looper++;
                } catch (Exception e) {
                    looper--;
                    break;
                }
            }

            //If the user doesn't exist, then append it to the file
            if (!foundName) {
                while (true) {
                    try {
                        FileWriter fr = new FileWriter(new File("UserWaypoints.txt"), true);
                        fr.write( "\n" + playerName + ":");
                        fr.close();
                        break;
                    } catch (Exception e) {}
                }

                System.out.println("Added " + playerName + " to list");
                if (sender instanceof Player) {
                    return onCommand(sender, command, label, args);
                }
            } else {
                //Actually do something here. This is after all checks and registrations have been done
                if (com.equalsIgnoreCase("setWaypoint")) {
                    float X = (int) player.getLocation().getX() - 0.5f;
                    float Y = (int) player.getLocation().getY();
                    float Z = (int) player.getLocation().getZ() + 0.5f;

                    Location waypointLocation = new Location(player.getWorld(), X, Y, Z);
                    String waypointName = "";

                    if (args.length == 4) {
                        try {
                            waypointLocation = new Location(player.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                        } catch (Exception except) {
                            player.sendMessage(ChatColor.RED + "Please type the waypoint location BEFORE you type the waypoint name");
                            waypointLocation = null;
                        }
                        waypointName = args[3];
                    } else if (args.length == 1) {
                        waypointName = args[0];
                    } else {
                        waypointLocation = null;
                    }

                    if (waypointLocation != null) {
                        player.sendMessage(ChatColor.YELLOW + waypointName + ChatColor.GREEN + " set at position " + ChatColor.YELLOW + waypointLocation.getX() + "," + waypointLocation.getY() + "," + waypointLocation.getZ());
                    } else {
                        player.sendMessage(ChatColor.RED + "Please either enter the co-ordinates for your waypoint and a name, or just enter a name to use your current location as a waypoint");
                    }
                } else if (com.equalsIgnoreCase("clearWaypoint")) {
                    player.sendMessage(ChatColor.GREEN + "clear waypoint");
                } else if (com.equalsIgnoreCase("getWaypoint") || com.equalsIgnoreCase("getwaypoints")) {
                    player.sendMessage(ChatColor.YELLOW + "Here is a list of your waypoints: ");
                }
            }
        }

        return false;
    }

    //Function to turn a certain material (Such as 2 oak logs) into 16 sticks
    private void stickRecipe(Material mat) {
        ItemStack item = new ItemStack(Material.STICK, 16);
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe(item);

        shapelessRecipe.addIngredient(2, mat);
        Plugin plugin = FirstPlugin.getPlugin(FirstPlugin.class);
        plugin.getServer().addRecipe(shapelessRecipe);
    }

    private String findKey(String keyToFind) {
        StringBuilder sb = new StringBuilder();
        Scanner sc = new java.util.Scanner("pluginSettings.txt");
        while (sc.hasNext()) { sb.append(sc.nextLine ()); }

        String[] lines = sb.toString().split("\n");
        for(String x : lines) {
            x = x.replace(" ","").replace("\t","");
            String[] data = x.split(":");

            //data[0] now contains the key, while data[1] contains the result
            if (data[0].equalsIgnoreCase(keyToFind)) { return data[1]; }
        }
        return null;
    }

    private void spawnMobAroundPlayer(Player player,EntityType myMob) {
        World w = player.getWorld();
        double x = player.getLocation().getX();
        double y = player.getLocation().getX();
        double z = player.getLocation().getX();

        Random r = new Random(); int low = 0; int high = 10; int randX = r.nextInt(high-low) + low;
        r = new Random(); low = 0; high = 5; int randY = r.nextInt(high-low) + low;
        r = new Random(); low = 0; high = 5; int randZ = r.nextInt(high-low) + low;

        w.spawnEntity(new Location(w,x+randX,y+randY,z+randZ),myMob);
    }
}
