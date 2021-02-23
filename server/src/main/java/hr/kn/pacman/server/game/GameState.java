package hr.kn.pacman.server.game;

import hr.kn.pacman.server.game.manager.ChannelManager;
import hr.kn.pacman.server.game.manager.PacmanMapManager;

public class GameState {
    public static ChannelManager channelManager = new ChannelManager();
    public static PacmanMapManager pacmanMapManager = new PacmanMapManager();
}
