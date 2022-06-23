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

        /*Timer ti = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(control){
                    repaint();
                }
            }
        });
        ti.start();*/


    /*    while (control) {
            try {
                this.space.repaint();
                sleep(150);
            } catch (Exception ex) {
                System.out.println("Error in repaint");
            }
        }*/
    }


    private void repaint() {
        if (control) {
            this.field.repaint();
        }
    }

}
