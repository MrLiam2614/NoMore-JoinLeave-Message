package net.mrliam2614.nmjlm.nomorejoinleavemessage.config;

import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.annotation.ConfigField;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.annotation.ConfigFile;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    public static <T> T loadConfig(Class<T> configClass) {
        T config = null;
        try {
            ConfigFile configFile = configClass.getAnnotation(ConfigFile.class);
            if (configFile != null) {

                Path configPath = Paths.get(configFile.path(), configFile.name());
                if (!Files.exists(configPath)) {
                    Files.createDirectories(configPath.getParent());
                    Files.createFile(configPath);
                    T defaultConfig = configClass.newInstance();
                    saveConfig(defaultConfig);
                }

                Yaml yaml = new Yaml();
                try (InputStream in = Files.newInputStream(Paths.get(configFile.path(), configFile.name()))) {
                    Map<String, Object> data = yaml.load(in);
                    config = configClass.newInstance();
                    for (Field field : configClass.getDeclaredFields()) {
                        ConfigField configField = field.getAnnotation(ConfigField.class);
                        if (configField != null) {
                            field.setAccessible(true);
                            field.set(config, data.get(configField.path()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }

    public static <T> void saveConfig(T config) {
        try {
            ConfigFile configFile = config.getClass().getAnnotation(ConfigFile.class);
            if (configFile != null) {
                Yaml yaml = new Yaml();
                Map<String, Object> data = new HashMap<>();
                for (Field field : config.getClass().getDeclaredFields()) {
                    ConfigField configField = field.getAnnotation(ConfigField.class);
                    if (configField != null) {
                        field.setAccessible(true);
                        data.put(configField.path(), field.get(config));
                    }
                }
                try (Writer writer = new OutputStreamWriter(Files.newOutputStream(Paths.get(configFile.path(), configFile.name())))) {
                    yaml.dump(data, writer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}