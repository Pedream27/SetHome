package br.dev.phsaraiva.setHome.converters;

import br.dev.phsaraiva.setHome.SetHome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class ConfigManipulation {
    private final Path configPath;

    public ConfigManipulation() {
        configPath = Path.of(SetHome.getInstance().getDataFolder() + "/config.yml");
    }

    public boolean oldConfigExists(String oldSetting) {
        if (oldSetting != null) {
            return true;
        } else {
            return false;
        }
    }

    public void backupOldConfig(Path destination) {
        if (Files.exists(destination)) return;
        SetHome.getInstance().getLogger().log(Level.INFO, "Configuração antiga encontrada! Fazendo backup para: " + destination);
        try {
            Files.copy(configPath, destination);
            SetHome.getInstance().getLogger().log(Level.INFO, "Configuração antiga feita backup com sucesso para:: " + destination);
        }
        catch (IOException e) {
            SetHome.getInstance().getLogger().log(Level.SEVERE, "error " + configPath);
            throw new RuntimeException(e);
        }
    }

    public void createNewConfig() {
        SetHome.getInstance().saveResource(configPath.getFileName().toString(), true);
        SetHome.getInstance().reloadConfig();
    }

}