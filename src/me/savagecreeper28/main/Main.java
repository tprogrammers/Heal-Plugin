package me.savagecreeper28.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    // The prefix for the plugin.
    String prefix = "§6[§eHeal§6]§7 ";

    // onEnable() = method to start the plugin.

    @Override
    public void onEnable() {

        // Print a line into console.
        Bukkit.getLogger().info(this.prefix + "§aPlugin enabled.");

        // Load the Command
        this.getCommand("heal").setExecutor(this);

        // Load the onConfig() event. onConfig() = load the config.yml.
        onConfig();

    }

    // onDisable() = method to stop the plugin.

    @Override
    public void onDisable() {

        // Print a line into console.
        Bukkit.getLogger().info(this.prefix + "§cPlugin disabled.");

    }

    // onConfig() = load the config.yml.

    public void onConfig() {

        // String meanings can be found in README.md
        this.getConfig().set("self_healed", "§aYou healed yourself!");
        this.getConfig().set("other_healed", "§aYou healed the Player "); // Putting the player in the empty field...
        this.getConfig().set("no_permissions", "§cYou have no permissions for this command.");
        this.getConfig().set("false_usage", "§cUse /heal to heal yourself or /heal <player_name>");
        this.getConfig().set("player_not_found", "§cThe Player couldn't be found.");

        // Write the Strings into config.yml
        this.getConfig().options().copyDefaults(true);

        // Save the config.
        this.saveConfig();
    }

    // onCommand() = write the heal command.

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Look if the player types "/heal"
        if(command.getName().equalsIgnoreCase("heal")){

            // Define the Player
            Player p = (Player)sender;

            // Look if the player is a player. If not, return a message.
            if(sender instanceof Player){

                // Check the args.
                if(args.length == 0){

                    // Check if the player has permissions. If not, return a message
                    if(p.hasPermission("this.heal.command.use")){

                        // Set the health and hunger of the player to the maximum. And send a message...
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.sendMessage(this.prefix + this.getConfig().getString("self_healed"));

                    }else{

                        // Return the message...
                        p.sendMessage(this.prefix + this.getConfig().getString("no_permissions"));

                    }

                }else if(args.length == 1){

                    // Define the target player.
                    // Add a deprecation warning.
                    @SuppressWarnings("deprecation") Player target = Bukkit.getPlayer(args[0]);

                    // Check if the player has permissions. If not, return a message.
                    if(p.hasPermission("this.heal.command.use.on.other")){

                        // Look if the player is online. If not, return a message...
                        if(target != null){

                            // Set the health and hunger from the target to the maximum. Return a message to both player.
                            target.setHealth(20);
                            target.setFoodLevel(20);
                            target.sendMessage(this.prefix + this.getConfig().getString("self_healed"));
                            p.sendMessage(this.prefix + this.getConfig().getString("other_healed") + target.getName());

                        }else{

                            // Return a message..
                            p.sendMessage(this.prefix + this.getConfig().getString("player_not_found"));

                        }

                    }else{

                        // Return the message..
                        p.sendMessage(this.prefix + this.getConfig().getString("no_permissions"));

                    }

                }else{

                    // Return a message.
                    p.sendMessage(this.prefix + this.getConfig().getString("false_usage"));

                }

            }else{

                // Return the message.
                sender.sendMessage(prefix + "§cYou aren't a player! Please execute this command ingame!");

            }

        }

        return true;
    }
}
