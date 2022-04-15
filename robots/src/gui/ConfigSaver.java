package gui;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigSaver {
    private String path = "";
    public Properties properties;
    private File file;
    public ConfigSaver(){
        this.file = new File(this.path);
        this.properties = loadConfig();
    }
    public Properties loadConfig(){
        this.properties = new Properties();
        try {
            properties.load(new FileReader(this.file));
        } catch (IOException e) {
            createConfig();
        }
        return properties;
    }

    public void createConfig(){
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
