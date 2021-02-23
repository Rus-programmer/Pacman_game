package com.badlogic.pacman.collision;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.screens.GameScreen;

public class MeteorAndJetCollisionDetector  {

    private MyPacmanGame game;
    private GameScreen gameScreen;
    private Sound explosionSound;

    public MeteorAndJetCollisionDetector(MyPacmanGame game, GameScreen gameScreen, Sound explosionSound) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.explosionSound = explosionSound;
    }

//    @Override
//    public void collideAndRemove(Array<Meteor> meteors, Array<Jet> jets) {
//        for (Iterator<Meteor> meteorIterator = gameScreen.meteors.iterator(); meteorIterator.hasNext(); ) {
//            Meteor meteor = meteorIterator.next();
//            for (Iterator<Jet> jetIterator = gameScreen.jets.iterator(); jetIterator.hasNext(); ) {
//                Jet jet = jetIterator.next();
//                if (meteor.getRectangle().overlaps(jet.getRectangle())) {
//                    explosionSound.play();
//                    if (jet.getJetId().equals(gameScreen.jet.getJetId())) {
//                        game.client.reportJetDestroyed(gameScreen.jet.getJetId());
//                    }
//                    System.out.print("Removing jet: " + jet.getJetId());
//                    jetIterator.remove();
//                    System.out.println(". Remaining jets: " + gameScreen.jets.size);
//                }
//            }
//        }
//    }
}
