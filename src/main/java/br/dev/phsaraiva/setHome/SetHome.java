package br.dev.phsaraiva.setHome;

import br.dev.phsaraiva.setHome.comands.CommandExecutor;
import br.dev.phsaraiva.setHome.comands.Commands;
import br.dev.phsaraiva.setHome.converters.ConfigManipulation;
import br.dev.phsaraiva.setHome.everts.EventMove;
import br.dev.phsaraiva.setHome.everts.EventQuit;
import br.dev.phsaraiva.setHome.everts.EventRespawn;
import br.dev.phsaraiva.setHome.util.ConfigUtil;
import br.dev.phsaraiva.setHome.util.HomeUtil;
import br.dev.phsaraiva.setHome.util.MessageUtil;
import br.dev.phsaraiva.setHome.util.UpdateChecker;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SetHome extends JavaPlugin {

        private static SetHome instance;

        public SetHome() {
            SetHome.instance = this;
        }

        public static SetHome getInstance() {
            return SetHome.instance;
        }

        public ConfigUtil configUtils;
        public MessageUtil messageUtils;
        public HomeUtil homeUtils;
        public Commands commands;
        public ConfigManipulation configManipulation;


        @Override
        public void onEnable() {
            // Copy default config
            saveDefaultConfig();

            // Initialize objects
            configUtils = new ConfigUtil();
            messageUtils = new MessageUtil();
            homeUtils = new HomeUtil();
            commands = new Commands();
            configManipulation = new ConfigManipulation();


            // Register commands
            getCommand("sethome").setExecutor(new CommandExecutor());
            getCommand("home").setExecutor(new CommandExecutor());
            getCommand("deletehome").setExecutor(new CommandExecutor());

            // Register events
            getServer().getPluginManager().registerEvents(new EventMove(), this);
            getServer().getPluginManager().registerEvents(new EventQuit(), this);
            getServer().getPluginManager().registerEvents(new EventRespawn(), this);

            // Check for updates
            new UpdateChecker(1).getVersion(version -> {
                if (!getDescription().getVersion().equals(version)) {
                    getLogger().info("Home Versão: " + version);

                }
            });
        }

        @Override
        public void onDisable() {
            // Unregister commands
            getCommand("sethome").setExecutor(null);
            getCommand("home").setExecutor(null);
            getCommand("deletehome").setExecutor(null);

            // Unregister events
            PlayerMoveEvent.getHandlerList().unregister(this);
            PlayerQuitEvent.getHandlerList().unregister(this);
            PlayerRespawnEvent.getHandlerList().unregister(this);

            // De-initialize objects
            configUtils = null;
            messageUtils = null;
            homeUtils = null;
            commands = null;
            configManipulation = null;

        }

    }