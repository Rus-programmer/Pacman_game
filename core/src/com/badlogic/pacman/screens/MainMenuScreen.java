package com.badlogic.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.client.eventbus.JoinGameMessageResponseListener;

import java.util.Random;

public class MainMenuScreen implements Screen {
    private final MyPacmanGame game;
    public String pacmanId;
    public long lastConnectionAttemptTimestamp;

    private Random random = new Random();

    public MainMenuScreen(final MyPacmanGame game) {
        MyPacmanGame.eventBus.register(new JoinGameMessageResponseListener(game));
        this.game = game;
        this.pacmanId = String.valueOf(random.nextInt());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        tryToReconnectToServer();
        game.batch.end();
    }

    private void tryToReconnectToServer() {
        if (System.currentTimeMillis() - lastConnectionAttemptTimestamp > 5000) {
            game.client.joinGame(pacmanId);
            this.lastConnectionAttemptTimestamp = System.currentTimeMillis();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}