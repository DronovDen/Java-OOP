package model;

import view.MainAgario;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Circle implements Serializable {

    public static final int INIT_SPEED = 1;
    public static final int MAX_COLOR = 256;
    private static final long serialVersionUID = 1L;
    private static Random random = new Random();

    private String nickName;
    private double posX;
    private double posY;
    private double radius;
    private double speed;
    private boolean avatar;
    private boolean alive;
    private Color color;
    private int id;

    public Circle() {
        this.posX = random.nextInt(MainAgario.WINDOW_WIDTH - 15) + 15;
        this.posY = random.nextInt(MainAgario.WINDOW_HEIGHT - 15) + 15;
        this.avatar = false;
        this.alive = true;
        radius = 5;
        set_color();
    }

    public Circle(String nickName, int id) {
        this.posX = random.nextInt(MainAgario.WINDOW_WIDTH - 15) + 15;
        this.posY = random.nextInt(MainAgario.WINDOW_HEIGHT - 15) + 15;
        this.avatar = true;
        this.alive = true;
        this.id = id;
        this.nickName = nickName;
        set_color();
        this.radius = 15;
    }

    public void set_color() {
        int r = random.nextInt(MAX_COLOR);
        int g = random.nextInt(MAX_COLOR);
        int b = random.nextInt(MAX_COLOR);
        this.color = new Color(r, g, b);
    }

    public void move(double x, double y) {
        this.posX += x;
        this.posY += y;
    }

    public void calculate_speed() {
        this.setSpeed(INIT_SPEED / this.radius);
    }

    private double distance(double xi, double yi, double xf, double yf) {
        return Math.sqrt((yf - yi) * (yf - yi) + (xf - xi) * (xf - xi));
    }

    private boolean collision(Circle other) {
        if (other != null) {
            double d = distance(this.posX, this.posY, other.posX, other.posY);
            if (d < this.radius + other.getRadius()) {
                if (this.radius > other.getRadius()) {
                    return true;
                } else if (this.radius < other.radius) {
                    return false;
                }
            }
        }
        return false;
    }

    public void checkCollision(Circle other) {
        boolean c = collision(other);
        if (c == true && other != null) {
            this.radius += (other.getRadius() / 3);
            other.setAlive(false);
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

}
