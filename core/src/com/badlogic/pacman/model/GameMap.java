package com.badlogic.pacman.model;

import java.util.Map;

public class GameMap {

    public int[][] getSkeleton(String field, Map<String, Integer> config) {
        int countWidth = config.get("countWidth");
        int countHeight = config.get("countHeight");
        int i = 0;
        char[] map = field.toCharArray();
        int[][] skeleton = new int[countHeight][countWidth];
        for (int y = 0; y < countHeight; y++) {
            for (int x = 0; x < countWidth; x++) {
                char c = map[i];
                if (c == '\r' || c == '\n') {
                    i++;
                    x--;
                    continue;
                }
                skeleton[y][x] = Character.getNumericValue(c);
                i++;
            }
        }
        return skeleton;
    }
}
