package com.badlogic.pacman.dto.request;

import com.badlogic.pacman.model.Direction;

public class PacmanMoveMessage extends GameClientMessage {

    private String pacmanId;
    private float x;
    private float y;
    private Direction direction;

    public PacmanMoveMessage(Direction direction, String pacmanId, float x, float y) {
        this.pacmanId = pacmanId;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
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
}
