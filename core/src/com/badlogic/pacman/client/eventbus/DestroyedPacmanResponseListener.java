package com.badlogic.pacman.client.eventbus;

import com.badlogic.gdx.Gdx;
import com.badlogic.pacman.dto.response.DestroyedMessageResponse;
import com.badlogic.pacman.screens.GameScreen;
import com.google.common.eventbus.Subscribe;

public class DestroyedPacmanResponseListener {
    GameScreen gameScreen;

    public DestroyedPacmanResponseListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Subscribe
    public void handle(DestroyedMessageResponse message) {
        Gdx.app.postRunnable(() ->
                {
                    for (int i = 0; i < this.gameScreen.pacmanList.size; i++) {
                        if (gameScreen.pacmanList.get(i).getPacmanId().equals(message.getDestroyedPacmanId())) {
                            gameScreen.pacmanList.removeIndex(i);
                        }
                    }
                }
        );
    }
}
