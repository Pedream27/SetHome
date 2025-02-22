package br.dev.phsaraiva.setHome.comands;

import br.dev.phsaraiva.setHome.SetHome;
import br.dev.phsaraiva.setHome.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    public enum COMMAND_TYPE {
        SETHOME,
        HOME,
        DELETEHOME
    }

    private HashMap<COMMAND_TYPE, Integer> cooldownTime;
    private HashMap<COMMAND_TYPE, Integer> warmupTime;

    private static HashMap<UUID, HashMap<COMMAND_TYPE, Long>> cooldownTask;
    private static HashMap<UUID, HashMap<COMMAND_TYPE, Boolean>> warmupInEffect;
    private static HashMap<UUID, HashMap<COMMAND_TYPE, BukkitTask>> warmupTask;

    public CommandExecutor() {
        initializeHashMaps();
    }

    private void initializeHashMaps() {
        cooldownTime = new HashMap<>();
        warmupTime = new HashMap<>();
        cooldownTask = new HashMap<>();
        warmupInEffect = new HashMap<>();
        warmupTask = new HashMap<>();
        cooldownTime.put(COMMAND_TYPE.SETHOME, SetHome.getInstance().configUtils.CMD_SETHOME_COOLDOWN);
        cooldownTime.put(COMMAND_TYPE.HOME, SetHome.getInstance().configUtils.CMD_HOME_COOLDOWN);
        cooldownTime.put(COMMAND_TYPE.DELETEHOME, SetHome.getInstance().configUtils.CMD_DELETEHOME_COOLDOWN);
        warmupTime.put(COMMAND_TYPE.SETHOME, SetHome.getInstance().configUtils.CMD_SETHOME_WARMUP);
        warmupTime.put(COMMAND_TYPE.HOME, SetHome.getInstance().configUtils.CMD_HOME_WARMUP);
        warmupTime.put(COMMAND_TYPE.DELETEHOME, SetHome.getInstance().configUtils.CMD_DELETEHOME_WARMUP);
    }

    public static HashMap<UUID, HashMap<COMMAND_TYPE, Long>> getCooldownTask() {
        return cooldownTask;
    }

    public static HashMap<UUID, HashMap<COMMAND_TYPE, Boolean>> getWarmupInEffect() {
        return warmupInEffect;
    }

    public static HashMap<UUID, HashMap<COMMAND_TYPE, BukkitTask>> getWarmupTask() {
        return warmupTask;
    }

    public void executeCmd(Player player, COMMAND_TYPE commandType) {
        if (commandType == COMMAND_TYPE.SETHOME)
            SetHome.getInstance().commands.cmdSetHome(player);
        else if (commandType == COMMAND_TYPE.HOME)
            SetHome.getInstance().commands.cmdHome(player);
        else if (commandType == COMMAND_TYPE.DELETEHOME)
            SetHome.getInstance().commands.cmdDeleteHome(player);
    }

    public boolean executeCooldown(Player player, COMMAND_TYPE commandType, int seconds) {
        if (cooldownTask.containsKey(player.getUniqueId())) {
            if (cooldownTask.get(player.getUniqueId()).containsKey(commandType)) {
                long secondsLeft = ((cooldownTask.get(player.getUniqueId()).get(commandType) / 1000) + seconds) - (System.currentTimeMillis() / 1000);
                if (secondsLeft > 0) {
                    SetHome.getInstance().messageUtils.displayMessage(player, MessageUtil.MESSAGE_TYPE.COOLDOWN, (int) secondsLeft);
                    return true;
                }
            }
        }
        HashMap<COMMAND_TYPE, Long> cooldownTaskData = new HashMap<>();
        cooldownTaskData.put(commandType, System.currentTimeMillis());
        cooldownTask.put(player.getUniqueId(), cooldownTaskData);
        return false;
    }

    public void executeWarmup(Player player, COMMAND_TYPE commandType, int seconds) {
        SetHome.getInstance().messageUtils.displayMessage(player, MessageUtil.MESSAGE_TYPE.WARMUP, seconds);
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                executeCmd(player, commandType);
                warmupInEffect.get(player.getUniqueId()).put(commandType, false);
            }
        };
        HashMap<COMMAND_TYPE, Boolean> warmupInEffectData = new HashMap<>();
        warmupInEffectData.put(commandType, true);
        warmupInEffect.put(player.getUniqueId(), warmupInEffectData);

        HashMap<COMMAND_TYPE, BukkitTask> warmupTaskData = new HashMap<>();
        warmupTaskData.put(commandType, runnable.runTaskLater(SetHome.getInstance(), 20L * seconds));
        warmupTask.put(player.getUniqueId(), warmupTaskData);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {

            SetHome.getInstance().messageUtils.displayMessage(sender, MessageUtil.MESSAGE_TYPE.DENY_CONSOLE, null);
            return false;
        }

        Player player = (Player) sender;
        COMMAND_TYPE commandType = null;

        if (command.getName().equals("sethome")) {
            commandType = COMMAND_TYPE.SETHOME;
        }
        else if (command.getName().equals("home")) {
            commandType = COMMAND_TYPE.HOME;
        }
        else if (command.getName().equals("deletehome")) {
            commandType = COMMAND_TYPE.DELETEHOME;
        }

        int cooldownSeconds = cooldownTime.get(commandType);
        int warmupSeconds = warmupTime.get(commandType);
        // Both cooldown and warmup enabled
        if (cooldownSeconds > 0 && warmupSeconds > 0) {
            boolean running = executeCooldown(player, commandType, cooldownSeconds);
            if (running)
                return false;
            executeWarmup(player, commandType, warmupSeconds);
        }
        // Just cooldown enabled
        else if (cooldownSeconds > 0) {
            boolean running = executeCooldown(player, commandType, cooldownSeconds);
            if (running)
                return false;
            executeCmd(player, commandType);
        }
        // Just warmup enabled
        else if (warmupSeconds > 0) {
            executeWarmup(player, commandType, warmupSeconds);
        }
        // Both cooldown and warmup disabled
        else {
            executeCmd(player, commandType);
        }

        return false;
    }

}