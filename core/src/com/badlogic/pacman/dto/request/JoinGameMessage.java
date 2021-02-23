package com.badlogic.pacman.dto.request;

public class JoinGameMessage extends GameClientMessage {

    private String desiredPacmanId;

    public JoinGameMessage(String desiredPacmanId) {
        this.desiredPacmanId = desiredPacmanId;
    }

    public String getDesiredPacmanId() {
        return desiredPacmanId;
    }
}
