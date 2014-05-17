package ca.kanoa.MultiHome;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;



public class home {
	private MultiHome plugin;
	FileConfiguration cf;
	players players;

	public home(MultiHome plugin)
	{
		this.plugin = plugin;
		players = new players(plugin);
		cf = players.getCustomConfig();
	}

	public void set(Player player, Location loc, String home){
		String name = player.getName();

		if(!cf.contains(name + ".homes")){
			cf.set(name + ".homes", 0);
		}

		if(cf.getInt(name + ".homes") < plugin.getConfig().getInt("max_homes")){
			player.sendMessage("Home " + home + " set!");
			if(!cf.getBoolean(name + "." + home + ".enabled")){
				int x = cf.getInt(name + ".homes") + 1;
				cf.set(name + ".homes", x);
			}

			String world = player.getWorld().getName();
			cf.set(name + "." + home + ".enabled", true);
			cf.set(name + "." + home + ".world", world);
			cf.set(name + "." + home + ".X", loc.getX());
			cf.set(name + "." + home + ".Y", loc.getY());
			cf.set(name + "." + home + ".Z", loc.getZ());
			double yawRaw = (double) loc.getYaw();
			cf.set(name + "." + home + ".Yaw", yawRaw);
			double pitchRaw = (double) loc.getPitch();
			cf.set(name + "." + home + ".Pitch", pitchRaw);

			players.sendConfig(cf);
			players.saveCustomConfig();
			players.reloadCustomConfig();
			cf = players.getCustomConfig();
		}
		else{
			player.sendMessage("You have reached the maximum amount of homes, if you want a home here just move and/or delete a home.");
		}
	}

	public void tp(Player player, String home){
		String name = player.getName();

		if(cf.getBoolean(name + "." + home + ".enabled")){
			if(cf.contains(name + "." + home)){

				String worldName = cf.getString(name + "." + home + ".world");
				World world = player.getServer().getWorld(worldName);
				double x = cf.getDouble(name + "." + home + ".X");
				double y = cf.getDouble(name + "." + home + ".Y");
				double z = cf.getDouble(name + "." + home + ".Z");
				float yaw = (float) cf.getDouble(name + "." + home + ".Yaw");
				float pitch = (float) cf.getDouble(name + "." + home + ".Pitch");

				Location dest = new Location(world, x, y, z, yaw, pitch);
				player.teleport(dest);

				player.sendMessage("Welcome home!");
			}
			else{
				player.sendMessage("Home " + home + " has not been set, set it using /mhome set " + home);
				plugin.getLogger().info("Player " + name + " tried to TP to a non-existent home!");
			}
		}
		else{
			player.sendMessage("Home " + home + " has not been set, set it using /mhome set " + home);
			plugin.getLogger().info("Player " + name + " tried to TP to a non-existent home!");
		}

	}

	public void del(Player player, String home){
		String name = player.getName();
		if(cf.contains(name + "." + home)){
			if(cf.getBoolean(name + "." + home + ".enabled")){
				player.sendMessage("Home " + home + " has been deleted!");
				cf.set(name + "." + home + ".enabled", false);
				int x = cf.getInt(name + ".homes") - 1;
				cf.set(name + ".homes", x);
				players.sendConfig(cf);
				players.saveCustomConfig();
				players.reloadCustomConfig();
				cf = players.getCustomConfig();
			}
			else{
				player.sendMessage("Home " + home + " has not been set, set it using /mhome set " + home);
				plugin.getLogger().info("Player " + name + " tried to delete a non-existent home!");
			}
		}
		else{
			player.sendMessage("Home " + home + " has not been set, set it using /mhome set " + home);
			plugin.getLogger().info("Player " + name + " tried to delete a non-existent home!");
		}
	}
}


