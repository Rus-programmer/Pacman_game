package com.badlogic.pacman.model;

import com.badlogic.gdx.graphics.Texture;

public class PacmanAnimation {
    private final Texture[] UpTextures = new Texture[4];
    private final Texture[] DownTextures = new Texture[4];
    private final Texture[] LeftTextures = new Texture[4];
    private final Texture[] RightTextures = new Texture[4];
    private float time = 0;
    private int state = 0;

    public PacmanAnimation() {
        Texture pacmanUpClosed = new Texture("pacmanUp.png");
        Texture pacmanLeftClosed = new Texture("pacmanLeft.png");
        Texture pacmanDownClosed = new Texture("pacmanDown.png");
        Texture pacmanRightClosed = new Texture("pacmanRight.png");
        Texture pacmanUpOpen = new Texture("pacmanUp-2.png");
        Texture pacmanLeftOpen = new Texture("pacmanLeft-2.png");
        Texture pacmanDownOpen = new Texture("pacmanDown-2.png");
        Texture pacmanRightOpen = new Texture("pacmanRight-2.png");
        Texture pacmanClosed = new Texture("pacman-3.png");

        UpTextures[0] = pacmanClosed;
        UpTextures[1] = pacmanUpClosed;
        UpTextures[2] = pacmanUpOpen;
        UpTextures[3] = pacmanUpClosed;

        DownTextures[0] = pacmanClosed;
        DownTextures[1] = pacmanDownClosed;
        DownTextures[2] = pacmanDownOpen;
        DownTextures[3] = pacmanDownClosed;

        LeftTextures[0] = pacmanClosed;
        LeftTextures[1] = pacmanLeftClosed;
        LeftTextures[2] = pacmanLeftOpen;
        LeftTextures[3] = pacmanLeftClosed;

        RightTextures[0] = pacmanClosed;
        RightTextures[1] = pacmanRightClosed;
        RightTextures[2] = pacmanRightOpen;
        RightTextures[3] = pacmanRightClosed;
    }

    public int update(float deltaTime) {
        time += deltaTime;
        return (int) (time * (500 / 50)) % 4;
    }

    public Texture getTexture(Pacman pacman, int state) {
        switch (pacman.getDirection()) {
            case UP:
                return UpTextures[state];
            case RIGHT:
                return RightTextures[state];
            case DOWN:
                return DownTextures[state];
            case LEFT:
                return LeftTextures[state];
        }
        return null;
    }
}
