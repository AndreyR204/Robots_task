package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;


/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final ConfigSaver configSaver = new ConfigSaver();
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        if (!configSaver.loadConfig()){
            JOptionPane.showMessageDialog(desktopPane, "Ошибка загрузки config", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setName("game");
        if (String.valueOf(configSaver.config).equals("{}")){
            gameWindow.setSize(400,  400);
        } else {
            gameWindow.setSize(configSaver.getWindowProperty("game", "width"), configSaver.getWindowProperty("game", "height"));
            try {
                gameWindow.setIcon(configSaver.getWindowProperty("game", "condition") != 1);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        if (String.valueOf(configSaver.config).equals("{}")){
            logWindow.setLocation(10,10);
            logWindow.setSize(300, 800);
        } else {
            logWindow.setLocation(configSaver.getWindowProperty("log", "positionX"),configSaver.getWindowProperty("log", "positionY"));
            logWindow.setSize(configSaver.getWindowProperty("log", "width"), configSaver.getWindowProperty("log", "height"));
            try {
                logWindow.setIcon(configSaver.getWindowProperty("log", "condition") != 1);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
        setMinimumSize(logWindow.getSize());
        //logWindow.pack();
        Logger.debug("Протокол работает");
        logWindow.setName("log");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void setWindow(JInternalFrame window){
        String windowName = window.getName();
        window.setLocation(configSaver.getWindowProperty(windowName, "positionX"),configSaver.getWindowProperty(windowName, "positionY"));
        window.setSize(configSaver.getWindowProperty(windowName, "width"), configSaver.getWindowProperty(windowName, "height"));
        try {
            window.setIcon(configSaver.getWindowProperty(windowName, "condition") != 1);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    private JMenu createMenu(String title, int keyEvent, String description) {
        JMenu menu = new JMenu(title);
        menu.setMnemonic(keyEvent);
        menu.getAccessibleContext().setAccessibleDescription(description);
        return menu;
    }
    private JMenuItem createSubmenu(String title, int keyEvent, ActionListener actionEvent){
        JMenuItem submenu = new JMenuItem(title, keyEvent);
        submenu.addActionListener(actionEvent);
        return submenu;
    }
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = createMenu("Режим отображения",KeyEvent.VK_V,"Управление режимом отображения приложения");

        lookAndFeelMenu.add(createSubmenu("Системная схема", KeyEvent.VK_S, (event) -> {
        setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.invalidate();
        }));

        lookAndFeelMenu.add(createSubmenu("Универсальная схема", KeyEvent.VK_S,(event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        }));

        JMenu testMenu = createMenu("Тесты",KeyEvent.VK_T,"Тестовые команды");
        testMenu.add(createSubmenu("Сообщение в лог", KeyEvent.VK_S,(event) -> {
            Logger.debug("Новая строка");
        }));
        
        JMenu exitMenu = createMenu("Выход", KeyEvent.VK_Q, "Выход");
        exitMenu.add(createSubmenu("Выход", KeyEvent.VK_Q, (event) -> {
            exitApplication();
        }));

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }
    private void exitApplication()
    {
        int option = JOptionPane.showConfirmDialog(desktopPane, "Хотите выйти?", "Выход", JOptionPane.YES_NO_OPTION);
        if (option==0){
            for( JInternalFrame f : this.desktopPane.getAllFrames() ){
                this.configSaver.setWindowProperty(f.getName(),f.getHeight(), "height");
                this.configSaver.setWindowProperty(f.getName(),f.getWidth(), "width");
                this.configSaver.setWindowProperty(f.getName(), f.getLocation().x, "positionX");
                this.configSaver.setWindowProperty(f.getName(), f.getLocation().y, "positionY");
                this.configSaver.setWindowProperty(f.getName(), (f.isIcon())?0:1, "condition");
            }
            this.configSaver.saveConfig();
            System.exit(0);
        } else {
            Logger.debug("Отмена выхода");
        }
    }
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
