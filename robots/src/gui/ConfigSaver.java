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
        String propertyName = windowName + "_height";
        return this.config.get(propertyName);
    }

    public Integer getWindowWidth(String windowName){
        String propertyName = windowName + "_width";
        return this.config.get(propertyName);
    }

    public Integer getWindowPositionX(String windowName){
        String propertyName = windowName + "_positionX";
        return this.config.get(propertyName);
    }

    public Integer getWindowPositionY(String windowName){
        String propertyName = windowName + "_positionY";
        return this.config.get(propertyName);
    }


    public void setWindowHeight(String windowName, Integer height){
        String propertyName = windowName + "_height";
        this.config.put(propertyName, height);
    }

    public void setWindowWidth(String windowName, Integer width){
        String propertyName = windowName + "_width";
        this.config.put(propertyName, width);
    }

    public void setWindowPositionX(String windowName, Integer position){
        String propertyName = windowName + "_positionX";
        this.config.put(propertyName, position);
    }
    public void setWindowPositionY(String windowName, Integer position){
        String propertyName = windowName + "_positionY";
        this.config.put(propertyName, position);
    }
}
