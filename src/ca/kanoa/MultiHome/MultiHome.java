package ca.kanoa.MultiHome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiHome extends JavaPlugin implements Listener{
	FileConfiguration config;
	home home;

	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		config = this.getConfig();
		home = new home(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player;
		boolean cont = true;
		if(cmd.getName().equalsIgnoreCase("mhome") || cmd.getName().equalsIgnoreCase("home")){
			if(args.length == 0){
				if(sender.hasPermission("mh.tp")){
					if(!(sender instanceof Player)){
						sender.sendMessage("No consoles allowed!");
						return false;
					}
					else{
						player = (Player) sender;
						home.tp(player, "1");
						return true;
					}
				}
				else{
					sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
					return false;
				}
			}
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("set")){
					if(sender.hasPermission("mh.set")){
						if(!(sender instanceof Player)){
							sender.sendMessage("No consoles allowed!");
							return false;
						}
						else{
							player = (Player) sender;
							home.set(player, player.getLocation(), "1");
						}
					}
					else{
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return false;
					}
				}
				else{
					if(!(sender instanceof Player)){
						sender.sendMessage("No consoles allowed!");
						return false;
					}
					else if(sender.hasPermission("mh.tp")){
						player = (Player) sender;
						if(cont){
							home.tp(player, args[0]);
							return true;
						}
					}
					else{
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return false;
					}
				}
			}
			else if(args.length == 2){
				if(args[0].equalsIgnoreCase("set")){
					if(!(sender instanceof Player)){
						sender.sendMessage("No consoles allowed!");
						return false;
					}
					else if(sender.hasPermission("mh.set")){
						player = (Player) sender;
							home.set(player, player.getLocation(), args[1]);
							return true;
					}
					else{
						sender.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
						return false;
					}
				}
				if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del")){
					player = (Player) sender;
					home.del(player, args[1]);
				}
			}
			else if(args.length > 2){
				sender.sendMessage(ChatColor.DARK_RED + "Too many arguments!");
				return false;
			}
		}
		else{
			return false;
		}
		return cont;
	}

}
