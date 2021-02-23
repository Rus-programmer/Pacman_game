package com.badlogic.pacman.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.pacman.model.Pacman;
import com.badlogic.pacman.model.PacmanAnimation;

import java.util.Iterator;

public class PacmanRenderer {
    private SpriteBatch batch = new SpriteBatch();
    private PacmanAnimation animation = new PacmanAnimation();

    public void render(Array<Pacman> pacmanArray, int delta) {
        batch.begin();
        for (Iterator<Pacman> iter = pacmanArray.iterator(); iter.hasNext(); ) {
            Pacman pacman = iter.next();
            batch.draw(animation.getTexture(pacman, delta), pacman.getX(), pacman.getY(), 506 / 23, 484 / 22);
        }
        batch.end();
    }
}
