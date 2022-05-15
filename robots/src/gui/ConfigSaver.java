package gui;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigSaver {
    private String path = "test.txt";
    public Properties properties= new Properties();
    public Map<String, Integer> config = new HashMap<String, Integer>();
    public ConfigSaver(){
    }
    public Boolean loadConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream(this.path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.config = (Map<String, Integer>) objectInputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        String propertyName = windowName + "_height";
        this.config.put(propertyName, height);
    }

    public void setWindowWidth(String windowName, Integer width){
        String propertyName = windowName + "_width";
        this.config.put(propertyName, width);
    }

    public void setWindowPosition(String windowName, Integer position){
        String propertyName = windowName + "position";
        this.properties.setProperty(propertyName, String.valueOf(position));
    }
}
