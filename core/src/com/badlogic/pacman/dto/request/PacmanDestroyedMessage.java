package com.badlogic.pacman.dto.request;

public class PacmanDestroyedMessage extends GameClientMessage {

    private String pacmanId;

    public PacmanDestroyedMessage(String pacmanId) {
        this.pacmanId = pacmanId;
    }

    public String getPacmanId() {
        return pacmanId;
    }
}
