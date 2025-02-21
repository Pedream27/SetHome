package br.dev.phsaraiva.setHome.comands;

import br.dev.phsaraiva.setHome.SetHome;
import org.bukkit.entity.Player;

public class Commands {

    public void cmdSetHome(Player player) {
        SetHome.getInstance().homeUtils.setPlayerHome(player);
    }

    public void cmdHome(Player player) {
        SetHome.getInstance().homeUtils.sendPlayerHome(player);
    }

    public void cmdDeleteHome(Player player) {
        if (SetHome.getInstance().homeUtils.homeExists(player, true))
            SetHome.getInstance().homeUtils.deletePlayerHome(player);
    }

}