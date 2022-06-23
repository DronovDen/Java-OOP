package model;

import java.io.Serializable;


public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nickname;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}

