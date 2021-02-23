package com.badlogic.pacman.dto.response;

import com.badlogic.pacman.model.Pacman;

public class NewPlayerJoinedResponse implements GameServerMessage {
    private Pacman pacman;

    public NewPlayerJoinedResponse(Pacman pacman) {
        this.pacman = pacman;
    }

    public Pacman getPacman() {
        return pacman;
    }
}
