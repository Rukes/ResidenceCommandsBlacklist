/*
 * Copyright Rukes
 */

/*
 *
 * @author Rukes
 *
 */

package eu.rukes.rescmdsblacklist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    private static Plugin _plugin;
    private static JavaPlugin _javaPlugin;
  
    @Override
    public void onEnable(){
        _plugin = this;
        _javaPlugin = this;

        PluginManager pm = getServer().getPluginManager();
        if (pm.getPlugin("Residence") != null){
            pm.registerEvents(new PlayerListener(this), this);
        }else{
            consoleMessage(ChatColor.RED + "Plugin Residence not found. Disabling.");
            pm.disablePlugin(_plugin);
            return;
        }
        saveDefaultConfig();
    }
  
    @Override
    public void onDisable(){
        _plugin = null;
        _javaPlugin = null;
        unReg(new Listener[] {new PlayerListener(this) });
        unRegPlugin(this);
    }
  
    public static void consoleMessage(String message){
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage(ChatColor.GREEN + "[" + _plugin.getName() + "] " + message);
    }
  
    public static void unReg(Listener... listeners){
        Listener[] arrayOfListener = listeners;int j = listeners.length;
        for (int i = 0; i < j; i++){
            Listener l = arrayOfListener[i];
            HandlerList.unregisterAll(l);
        }
    }
  
    public static void unRegPlugin(Plugin p){
        HandlerList.unregisterAll(p);
    }
  
    public static FileConfiguration config(){
        return _plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (sender.hasPermission("rescmdsblacklist.admin")){
            if (args[0].equals("reload")){
                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "[" + _plugin.getName() + "]" + " Reloaded config.");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Na tento prikaz nemas opravneni.");
        }
        return false;
    }
}
