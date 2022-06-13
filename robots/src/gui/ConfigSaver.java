package gui;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigSaver {
    private String path = System.getProperty("user.home") + "\\" + "config.txt";
    public Map<String, Integer> config = new HashMap<String, Integer>();
    public Boolean loaded;
    public ConfigSaver(){
        this.loaded = loadConfig();
    }
    public Boolean loadConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream(this.path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.config = (Map<String, Integer>) objectInputStream.readObject();

        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public Boolean saveConfig(){
        try {
            FileOutputStream outputStream = new FileOutputStream(this.path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public Integer getWindowProperty(String windowName, String property, Integer defaultValue){
        String propertyName = windowName + "_" + property;
        if (this.config.get(propertyName) == null){
            return defaultValue;
        } else{
            return this.config.get(propertyName);
        }
    }


    public void setWindowProperty(String windowName, Integer value, String property){
        String propertyName = windowName + "_" + property;
        this.config.put(propertyName, value);
    }


}
