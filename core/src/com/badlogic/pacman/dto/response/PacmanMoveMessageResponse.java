package com.badlogic.pacman.dto.response;

import com.badlogic.pacman.model.Direction;

public class PacmanMoveMessageResponse implements GameServerMessage {
    private String pacmanId;
    private float x;
    private float y;
    private Direction direction;
    private String field;

    public PacmanMoveMessageResponse(Direction direction, String pacmanId, float x, float y, String field) {
        this.pacmanId = pacmanId;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.field = field;
    }

    public String getPacmanId() {
        return pacmanId;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getField() {
        return field;
    }
}
