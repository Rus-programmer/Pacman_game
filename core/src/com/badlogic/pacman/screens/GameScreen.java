package com.badlogic.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.client.eventbus.DestroyedPacmanResponseListener;
import com.badlogic.pacman.client.eventbus.PacmanMoveMessageResponseListener;
import com.badlogic.pacman.client.eventbus.NewPlayerJoinedMessageResponseListener;
import com.badlogic.pacman.controller.PacmanMovementControls;
import com.badlogic.pacman.model.*;
import com.badlogic.pacman.render.PacmanRenderer;

import java.util.Map;

public class GameScreen implements Screen {
    private PacmanAnimation pacmanAnimation;
    private MyPacmanGame game;
    private String pacmanId;
    private Pacman pacman;
    private int[][] skeleton;
    private SpriteBatch textBatch = new SpriteBatch();
    public Array<Pacman> pacmanList;
    private GameMap map;
    private Texture block;
    private Texture emptyBlock;
    private Texture pellet;
    private PacmanMovementControls controls;
    private PacmanRenderer pacmanRenderer = new PacmanRenderer();
    private String field;

    private Map<String, Integer> config;

    public GameScreen(MyPacmanGame game, Pacman pacman, String field, Map<String, Integer> config) {
        MyPacmanGame.eventBus.register(new PacmanMoveMessageResponseListener(this));
        MyPacmanGame.eventBus.register(new NewPlayerJoinedMessageResponseListener(this));
        MyPacmanGame.eventBus.register(new DestroyedPacmanResponseListener(this));
        this.pacman = pacman;
        this.game = game;
        this.game.setGameScreen(this);
        this.field = field;
        this.pacmanId = pacman.getPacmanId();
        this.pacmanAnimation = new PacmanAnimation();
        this.config = config;
        map = new GameMap();
        block = new Texture(Gdx.files.internal("block.png"));
        emptyBlock = new Texture(Gdx.files.internal("dark.png"));
        pellet = new Texture(Gdx.files.internal("pellet.png"));
        this.controls = new PacmanMovementControls(config);
        this.pacmanList = Array.with(pacman);
    }

    @Override
    public void render(float delta) {
        this.skeleton = map.getSkeleton(field, config);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        int c = 0;
        int b = 0;
        for (int y = 0; y < skeleton.length; y++) {
            for (int x = 0; x < skeleton[y].length; x++) {
                if (skeleton[y][x] == 0)
                    game.batch.draw(block, c, b, 506 / 23, 484 / 22);
                else if (skeleton[y][x] == 2) {
                    game.batch.draw(pellet, c, b, 506 / 23, 484 / 22);
                } else
                    game.batch.draw(emptyBlock, c, b, 506 / 23, 484 / 22);
                c += 22;
                if (x >= skeleton[y].length-1) {
                    c = 0;
                    b += 22;
                }
            }
        }
        pacmanAnimation.update(delta);
        game.batch.end();

        pacmanRenderer.render(pacmanList, pacmanAnimation.update(delta));
        controls.processPacmanControls(pacman, skeleton);
        game.client.movePacman(pacman.getDirection(), pacmanId, pacman.getX(), pacman.getY());
    }

    @Override
    public void show() {

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
        textBatch.dispose();
    }

    public String getPacmanId() {
        return pacmanId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}