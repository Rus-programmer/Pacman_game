package hr.kn.pacman.server.game.manager;

import com.badlogic.pacman.model.Pacman;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class PacmanMapManager {
    private Map<String, Pacman> pacmanMap = Maps.newHashMap();

    public Map<String, Pacman> getPacmanMap() {
        return pacmanMap;
    }

    public Optional<Pacman> findPacmanById(String pacmanId) {
        return Optional.ofNullable(pacmanMap.get(pacmanId));
    }

    public void updatePacmanLocation(String pacmanId, float x, float y) {
        Optional<Pacman> pacmanMb = findPacmanById(pacmanId);
        pacmanMb.ifPresent(pacman -> {
            pacman.setX(x);
            pacman.setY(y);
        });
    }
}
