package view;

import controller.Controller;
import controller.ThreadRepaint;
import model.Circle;
import threadpool.CustomThreadPool;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainAgarIO extends JFrame {

    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1080;
    public static final int WINDOW_POS_X = 50;
    public static final int WINDOW_POS_Y = 50;
    private static final long serialVersionUID = 1L;
    private final Field field;
    //ExecutorService mainService;
    private final CustomThreadPool mainService;
    private Controller controller;

    public MainAgarIO() {
        //mainService = Executors.newCachedThreadPool();
        mainService = new CustomThreadPool(1);
        controller = new Controller(this);
        LoginView loginWindow = new LoginView(this);
        loginWindow.setVisible(true);
        this.field = new Field(controller.getGame(), new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    }

    public static void main(String[] args) {
        MainAgarIO m = new MainAgarIO();
        m.setVisible(false);
    }

    public void play() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(true);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        controller.startGame();
        paintGame(controller.getAvatar(), controller.getGame().getFood());
    }

    private void paintGame(Circle circle, CopyOnWriteArrayList<Circle> food) {
        // Add player with socket
        this.field.setFocusable(false);
        this.setIgnoreRepaint(false);
        this.add((Component) this.field);

        try {
            mainService.execute(new ThreadRepaint(field));
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        /*ThreadRepaint repaint = new ThreadRepaint(field);
        repaint.start();*/
    }


    @Override
    public void paint(Graphics g) {
        if (this.field != null) {
            this.field.paint(g);
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void showMessage(String message) {
        this.field.setMessage(message);
    }

}
