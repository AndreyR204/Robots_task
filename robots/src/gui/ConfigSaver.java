package gui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigSaver {
    private String path = "Config.properties";
    public Properties properties= new Properties();
    private File file = new File(this.path);
    public ConfigSaver(){
        loadConfig();
    }
    public Boolean loadConfig(){
        try {
            this.properties.load(new FileReader(this.file));
        } catch (IOException e) {
            createConfig();
            return false;
        }
        return true;
    }

    public Boolean saveConfig(){
        try {
            this.properties.store(new FileWriter(this.file),null);
        } catch (IOException e) {
            createConfig();
            return false;
        }
        return true;
    }

    public void createConfig(){
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getWindowHeight(String windowName){
        String propertyName = windowName + "height";
        return (Integer) this.properties.get(propertyName);
    }

    public Integer getWindowWidth(String windowName){
        String propertyName = windowName + "width";
        return (Integer) this.properties.get(propertyName);
    }

    public Integer getWindowPosition(String windowName){
        String propertyName = windowName + "position";
        return (Integer) this.properties.get(propertyName);
    }

    public void setWindowHeight(String windowName, Integer height){
        String propertyName = windowName + "height";
        this.properties.setProperty(propertyName, String.valueOf(height));
    }

    public void setWindowWidth(String windowName, Integer width){
        String propertyName = windowName + "width";
        this.properties.setProperty(propertyName, String.valueOf(width));
    }

    public void setWindowPosition(String windowName, Integer position){
        String propertyName = windowName + "position";
        this.properties.setProperty(propertyName, String.valueOf(position));
    }
}
