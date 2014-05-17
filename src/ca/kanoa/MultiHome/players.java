package ca.kanoa.MultiHome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class players {
	private MultiHome plugin;
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
	public players (MultiHome plugin)
	{
		this.plugin = plugin;
	}
	
    public void reloadCustomConfig() {
        if (customConfigFile == null) {
        customConfigFile = new File(plugin.getDataFolder(), "homes.yml");
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
     
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("homes.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }
    
    public void sendConfig(FileConfiguration newConfig){
    	customConfig = newConfig;
    }
    
    public FileConfiguration getCustomConfig() {
        if (customConfig == null) {
            this.reloadCustomConfig();
        }
        return customConfig;
    }
    
    public void saveCustomConfig() {
        if (customConfig == null || customConfigFile == null) {
        return;
        }
        try {
            getCustomConfig().save(customConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }
}
