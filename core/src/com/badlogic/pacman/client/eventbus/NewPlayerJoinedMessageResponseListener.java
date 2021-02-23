package com.badlogic.pacman.client.eventbus;

import com.badlogic.gdx.Gdx;
import com.badlogic.pacman.dto.response.NewPlayerJoinedResponse;
import com.badlogic.pacman.screens.GameScreen;
import com.google.common.eventbus.Subscribe;

public class NewPlayerJoinedMessageResponseListener {

    private GameScreen gameScreen;

    public NewPlayerJoinedMessageResponseListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Subscribe
    public void handle(NewPlayerJoinedResponse message) {
        Gdx.app.postRunnable(() ->
            this.gameScreen.pacmanList.add(message.getPacman())
        );
    }

}
