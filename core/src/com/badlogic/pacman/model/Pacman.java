package com.badlogic.pacman.model;


import java.io.Serializable;

public class Pacman implements Serializable {
    private String pacmanId;
    private Direction direction;
    private float x;
    private float y;

    public Pacman(String pacmanId, float x, float y) {
        this.x = x;
        this.y = y;
        this.pacmanId = pacmanId;
        this.direction = Direction.RIGHT;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getPacmanId() {
        return pacmanId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
