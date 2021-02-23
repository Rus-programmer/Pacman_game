package com.badlogic.pacman.dto.response;

import com.badlogic.pacman.model.Pacman;

import java.util.Map;

public class JoinGameMessageResponse implements GameServerMessage {
    private String status;
    private Map<String, Pacman> pacmanMap;
    private Pacman pacman;
    private Map<String, Integer> config;
    private String field;

    public JoinGameMessageResponse(String status, Map<String, Pacman> pacmanMap,
                                   Pacman pacman, Map<String, Integer> config, String field) {
        this.status = status;
        this.pacmanMap = pacmanMap;
        this.pacman = pacman;
        this.config = config;
        this.field = field;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, Pacman> getPacmanMap() {
        return pacmanMap;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Map<String, Integer> getConfig() {
        return config;
    }

    public String getField() {
        return field;
    }
}
