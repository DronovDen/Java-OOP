package controller;

import view.Field;

import javax.swing.*;

public class ThreadRepaint extends Thread {
    private final Field field;
    private boolean control;

    public ThreadRepaint(Field field) {
        this.field = field;
        control = true;
    }

    @Override
    public void run() {
        Timer t = new Timer(150, e -> repaint());
        t.start();
    }


    private void repaint() {
        if (control) {
            this.field.repaint();
        }
    }

}
