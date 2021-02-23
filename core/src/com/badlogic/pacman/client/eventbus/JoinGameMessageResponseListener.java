package com.badlogic.pacman.client.eventbus;


import com.badlogic.gdx.Gdx;
import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.client.network.UdpClientState;
import com.badlogic.pacman.dto.response.JoinGameMessageResponse;
import com.badlogic.pacman.model.Pacman;
import com.badlogic.pacman.screens.GameScreen;
import com.google.common.eventbus.Subscribe;

import java.util.Map;

public class JoinGameMessageResponseListener {

    private MyPacmanGame game;

    public JoinGameMessageResponseListener(MyPacmanGame game) {
        this.game = game;
    }

    @Subscribe
    public void handle(JoinGameMessageResponse message) {
        if ("SUCCESS".equals(message.getStatus())) {
            game.client.state = UdpClientState.CONNECTED;
            Gdx.app.postRunnable(() -> {
                GameScreen gameScreen = new GameScreen(game, message.getPacman(), message.getField(),
                        message.getConfig());
                for (Map.Entry<String, Pacman> pacmanEntry : message.getPacmanMap().entrySet()) {
                    gameScreen.pacmanList.add(pacmanEntry.getValue());
                }
                game.setScreen(gameScreen);
            });
        }

    }

}
