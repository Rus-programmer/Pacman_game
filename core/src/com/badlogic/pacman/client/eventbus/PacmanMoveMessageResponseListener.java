package com.badlogic.pacman.client.eventbus;

import com.badlogic.gdx.Gdx;
import com.badlogic.pacman.dto.response.PacmanMoveMessageResponse;
import com.badlogic.pacman.screens.GameScreen;
import com.google.common.eventbus.Subscribe;

public class PacmanMoveMessageResponseListener {

    private GameScreen gameScreen;

    public PacmanMoveMessageResponseListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Subscribe
    public void handle(PacmanMoveMessageResponse message) {
        gameScreen.setField(message.getField());
        Gdx.app.postRunnable(() -> gameScreen.pacmanList.forEach(pacman -> {
            if (pacman.getPacmanId().equals(message.getPacmanId())) {
                pacman.setDirection(message.getDirection());
                pacman.setX(message.getX());
                pacman.setY(message.getY());
            }
        }));
    }

}
