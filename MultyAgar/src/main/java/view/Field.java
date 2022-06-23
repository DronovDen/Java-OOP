package view;

import model.Circle;
import model.Game;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Field extends Canvas {

    private final Dimension dimPanel;
    private final Game game;
    private Graphics gra;
    private String message;

    public Field(Game game, Dimension d) {

        this.message = "";
        this.game = game;
        this.setSize(d);
        this.dimPanel = d;
    }

    public static void render(Graphics g, double scale, Circle a) {

        double r = a.getRadius();
        g.setColor(a.getColor());
        g.fillOval((int) (a.getPosX() - r), (int) (a.getPosY() - r), (int) (2 * r), (int) (2 * r));
        g.setColor(Color.BLACK);
        g.drawOval((int) (a.getPosX() - r), (int) (a.getPosY() - r), (int) (2 * r), (int) (2 * r));
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        gra = g;
        // Paint background
        g.setColor(new Color(220, 220, 220));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, MainAgario.WINDOW_WIDTH, MainAgario.WINDOW_HEIGHT);
        g.setColor(new Color(220, 220, 220));

        int space = 40;
        for (int i = 0; i < dimPanel.width; i += space)
            g.drawLine(i, 0, i, dimPanel.height);
        for (int j = 0; j < dimPanel.height; j += space)
            g.drawLine(0, j, dimPanel.width, j);

        // Paint Avatars

        if (game.getAvatars() != null) {
            this.paintPlayer(game.getAvatars(), g);
        }

        /*if (game.getFood() == null) {
            System.out.println("food null");
        }*/

        // Paint Food
        if (game.getFood() != null) {
            this.paintFood(game.getFood(), g);
            /*try {
                this.paintFood(game.getFood(), g);
            } catch (Exception e) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, e);
            }*/
        }

        // Paint Leader board
        paintLeaderBoard(game.getTop(), g);

        // Paint message
        paintMessege();

    }

    public void paintPlayer(ArrayList<Circle> circles, Graphics g) {

        for (int i = 0; i < circles.size(); i++) {
            try {
                Circle a = circles.get(i);
                render(g, 1, a);

                double x = a.getPosX();
                double y = a.getPosY();
                double r = a.getRadius();
                g.setFont(new Font("Ubuntu", Font.BOLD, (int) r / 2));
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                int xt = (int) x - metrics.stringWidth(a.getNickName()) / 2;
                int yt = (int) (y + r / 4);
                g.drawString(a.getNickName(), xt, yt);

            } catch (Exception e) {
                System.out.print("Problem in paintPlayer");
                e.printStackTrace();
            }
        }
    }

    public void paintFood(ArrayList<Circle> food, Graphics g) {
        for (int i = 0; i < food.size(); i++) {

            Circle f = food.get(i);
            render(g, 1, f);

            /*catch (Exception e) {
                System.out.print("Problem in paintFood");
                e.printStackTrace();
            }*/
        }
    }

    private void paintLeaderBoard(ArrayList<Circle> top, Graphics g) {

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Ubuntu", Font.ITALIC, 18));
        g.drawString("LEADERBOARD", (int) dimPanel.getWidth() - 175, 50);
        g.drawString("---------------------", (int) dimPanel.getWidth() - 175, 60);
        //int i = 30;
        int pos = 1;
        String report;
        for (int i = 0; i < game.getTop().size(); i++) {
            DecimalFormat df = new DecimalFormat("#.##");
            report = (i + 1) + ".  " + game.getTop().get(i).getNickName() + " " + df.format(game.getTop().get(i).getRadius()) + "\n";
            g.drawString(report, (int) dimPanel.getWidth() - 175, 75 + (i * 20));
        }
        //g.drawString(game.reportScores(), (int) dimPanel.getWidth() - 150, 75);

    }

    public void paintMessege() {
        gra.setColor(Color.red);
        gra.setFont(new Font("Ubuntu", Font.BOLD, 80));
        gra.drawString(this.message, (int) dimPanel.getWidth() / 2 - 250, (int) dimPanel.getHeight() / 2 - 40);
    }

    /*public String getMessage() {
        return message;
    }*/

    public void setMessage(String message) {
        this.message = message;
    }

}

