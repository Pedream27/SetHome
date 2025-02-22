package br.dev.phsaraiva.setHome.everts;

import br.dev.phsaraiva.setHome.SetHome;
import br.dev.phsaraiva.setHome.comands.CommandExecutor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SetHome.getInstance().homeUtils.getHomeFiles().remove(event.getPlayer().getUniqueId());
        //SetHome.getInstance().getLogger().info("Removed " + event.getPlayer().getUniqueId() + " from File HashMap");
        SetHome.getInstance().homeUtils.getHomeYamls().remove(event.getPlayer().getUniqueId());
        //SetHome.getInstance().getLogger().info("Removed " + event.getPlayer().getUniqueId() + " from YamlConfiguration HashMap");
        CommandExecutor.getCooldownTask().remove(event.getPlayer().getUniqueId());
        //SetHome.getInstance().getLogger().info("Removed " + event.getPlayer().getUniqueId() + " from cooldownTask HashMap");
        CommandExecutor.getWarmupInEffect().remove(event.getPlayer().getUniqueId());
        //SetHome.getInstance().getLogger().info("Removed " + event.getPlayer().getUniqueId() + " from warmupInEffect HashMap");
        CommandExecutor.getWarmupTask().remove(event.getPlayer().getUniqueId());
        //SetHome.getInstance().getLogger().info("Removed " + event.getPlayer().getUniqueId() + " from warmupTask HashMap");
    }

}
