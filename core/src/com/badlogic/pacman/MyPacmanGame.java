package com.badlogic.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.pacman.client.network.UdpClient;
import com.badlogic.pacman.screens.GameScreen;
import com.badlogic.pacman.screens.MainMenuScreen;
import com.google.common.eventbus.EventBus;

public class MyPacmanGame extends Game {

    private String server;
    private GameScreen gameScreen;

    public SpriteBatch batch;

    public static EventBus eventBus = new EventBus();
    public UdpClient client = new UdpClient(this);

    public MyPacmanGame(String server) {
        this.server = server;
    }

    public void create() {
        try {
            client.start(server);
            this.batch = new SpriteBatch();
            this.setScreen(new MainMenuScreen(this));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        if (gameScreen != null) {
            client.reportPacmanDestroyed(gameScreen.getPacmanId());
            gameScreen.dispose();
        }
        batch.dispose();
    }
}
