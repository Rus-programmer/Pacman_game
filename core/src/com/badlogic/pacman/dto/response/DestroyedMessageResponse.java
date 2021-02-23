package com.badlogic.pacman.dto.response;

public class DestroyedMessageResponse implements GameServerMessage {
    private String destroyedPacmanId;

    public DestroyedMessageResponse(String destroyedPacmanId) {
        this.destroyedPacmanId = destroyedPacmanId;
    }

    public String getDestroyedPacmanId() {
        return destroyedPacmanId;
    }
}
