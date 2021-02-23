package com.badlogic.pacman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.pacman.model.Direction;
import com.badlogic.pacman.model.Pacman;
import com.badlogic.pacman.model.Skeleton;
import java.util.Map;
import static com.badlogic.pacman.model.Direction.LEFT;
import static com.badlogic.pacman.model.Direction.DOWN;
import static com.badlogic.pacman.model.Direction.UP;
import static com.badlogic.pacman.model.Direction.RIGHT;

public class PacmanMovementControls implements Skeleton {
    private final float DELTA = 0.01f;
    private Map<String, Integer> config;
    private final int stepX;
    private final int stepY;
    private int height;
    private int width;
    private int speed;
    private boolean justTurned;

    public PacmanMovementControls(Map<String, Integer> config) {
        this.config = config;
        this.stepY = config.get("height") / config.get("countHeight");
        this.stepX = config.get("width") / config.get("countWidth");
        this.width = config.get("width");
        this.height = config.get("height");
        this.speed = config.get("speed");
    }

    public void processPacmanControls(Pacman pacman, int[][] skeleton) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && pacman.getDirection() == LEFT) {
            pacmanDoubleMovement(LEFT, DOWN, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)
                && pacman.getDirection() == DOWN) {
            pacmanDoubleMovement(DOWN, LEFT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)
                && pacman.getDirection() == LEFT) {
            pacmanDoubleMovement(LEFT, UP, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)
                && pacman.getDirection() == UP) {
            pacmanDoubleMovement(UP, LEFT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && pacman.getDirection() == RIGHT) {
            pacmanDoubleMovement(RIGHT, DOWN, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                && pacman.getDirection() == DOWN) {
            pacmanDoubleMovement(DOWN, RIGHT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)
                && pacman.getDirection() == RIGHT) {
            pacmanDoubleMovement(RIGHT, UP, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                && pacman.getDirection() == UP) {
            pacmanDoubleMovement(UP, RIGHT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT)
                && pacman.getDirection() == RIGHT) {
            pacmanInverseMovement(RIGHT, LEFT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                && pacman.getDirection() == LEFT) {
            pacmanInverseMovement(LEFT, RIGHT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && pacman.getDirection() == UP) {
            pacmanInverseMovement(UP, DOWN, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.UP)
                && pacman.getDirection() == DOWN) {
            pacmanInverseMovement(DOWN, UP, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pacmanMovement(LEFT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pacmanMovement(RIGHT, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pacmanMovement(UP, pacman, skeleton);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pacmanMovement(DOWN, pacman, skeleton);
        } else {
            int nextBlock = getNextElement(pacman.getDirection(), skeleton, pacman);
            roundPosition(pacman.getDirection(), nextBlock == BLOCK, pacman);
        }
    }


    public void pacmanMovement(Direction direction, Pacman pacman, int[][] skeleton) {
        int nextBlock = getNextElement(direction, skeleton, pacman);
        if (nextBlock != BLOCK) {
            pacman.setDirection(direction);
            updatePosition(pacman);
        } else {
            roundPosition(direction, true, pacman);
        }
    }


    public void pacmanDoubleMovement(Direction direction, Direction addDirection,
                                     Pacman pacman, int[][] skeleton) {
        int nextBlock = getNextElement(direction, skeleton, pacman);
        int checkAddBlock = 0;
        if (!justTurned) {
            checkAddBlock = checkAdditionalElement(pacman, addDirection, pacman.getDirection(), skeleton);
        }
        if (checkAddBlock != BLOCK) {
            justTurned = true;
            pacman.setDirection(addDirection);
        } else if (nextBlock != BLOCK) {
            pacman.setDirection(direction);
            updatePosition(pacman);
        }
    }

    public void pacmanInverseMovement(Direction direction, Direction addDirection, Pacman pacman, int[][] skeleton) {
        int nextBlock = getNextElement(direction, skeleton, pacman);
        if (nextBlock != BLOCK) {
            pacman.setDirection(direction);
            updatePosition(pacman);
        } else {
            pacman.setDirection(addDirection);
        }
    }

    public void updatePosition(Pacman pacman) {
        switch (pacman.getDirection()) {
            case LEFT:
                if (pacman.getX() % stepX < speed * DELTA * 1.05 && pacman.getX() % stepX != 0) {
                    pacman.setX((float) (Math.floor(pacman.getX() / stepX) * stepX));
                    justTurned = false;
                } else {
                    pacman.setX(pacman.getX() - (speed * DELTA));
                }
                pacman.setY((float) Math.round(pacman.getY() / stepY) * stepY);
                break;
            case RIGHT:
                if (stepX - pacman.getX() % stepX < speed * DELTA * 1.05) {
                    pacman.setX((float) (Math.ceil(pacman.getX() / stepX) * stepX));
                    justTurned = false;
                } else {
                    pacman.setX(pacman.getX() + (speed * DELTA));
                }
                pacman.setY((float) Math.round(pacman.getY() / stepY) * stepY);
                break;
            case UP:
                if (stepY - pacman.getY() % stepY < speed * DELTA * 1.05) {
                    justTurned = false;
                    pacman.setY((float) (Math.ceil(pacman.getY() / stepY) * stepY));
                } else {
                    pacman.setY(pacman.getY() + (speed * DELTA));
                }
                pacman.setX((float) Math.round(pacman.getX() / stepX) * stepX);
                break;
            case DOWN:
                if (pacman.getY() % stepY < speed * DELTA * 1.05 && pacman.getY() % stepY != 0) {
                    justTurned = false;
                    pacman.setY((float) (Math.floor(pacman.getY() / stepY) * stepY));
                } else {
                    pacman.setY(pacman.getY() - (speed * DELTA));
                }
                pacman.setX((float) Math.round(pacman.getX() / stepX) * stepX);
                break;
        }
    }

    public void roundPosition(Direction direction, boolean block, Pacman pacman) {
        switch (direction) {
            case LEFT:
                if (block) pacman.setX((float) Math.ceil(pacman.getX() / stepX) * stepX);
                else pacman.setX((float) Math.floor(pacman.getX() / stepX) * stepX);
            case RIGHT:
                if (block) pacman.setX((float) Math.floor(pacman.getX() / stepX) * stepX);
                else pacman.setX((float) Math.ceil(pacman.getX() / stepX) * stepX);
            case UP:
                if (block) pacman.setY((float) Math.floor(pacman.getY() / stepY) * stepY);
                else pacman.setY((float) Math.ceil(pacman.getY() / stepY) * stepY);
            case DOWN:
                if (block) pacman.setY((float) Math.ceil(pacman.getY() / stepY) * stepY);
                else pacman.setY((float) Math.floor(pacman.getY() / stepY) * stepY);
        }
    }

    public int getNextElement(Direction direction, int[][] skeleton, Pacman pacman) {
        int x, y;
        switch (direction) {
            case LEFT:
                x = (int) Math.ceil(pacman.getX() / stepX) - 1;
                y = Math.round(pacman.getY() / stepY);
                return skeleton[y][x];
            case RIGHT:
                x = (int) Math.floor(pacman.getX() / stepX) + 1;
                y = Math.round(pacman.getY() / stepY);
                return skeleton[y][x];
            case UP:
                x = Math.round(pacman.getX() / stepX);
                y = (int) Math.floor(pacman.getY() / stepY) + 1;
                return skeleton[y][x];
            case DOWN:
                x = Math.round(pacman.getX() / stepX);
                y = (int) Math.ceil(pacman.getY() / stepY) - 1;
                return skeleton[y][x];
        }
        throw new IllegalArgumentException("Unrecognized Direction");
    }

    public int checkAdditionalElement(Pacman pacman, Direction addDirection, Direction currentDirection,
                                      int[][] skeleton) {
        switch (addDirection) {
            case LEFT:
                return getAddLeftElement(pacman, currentDirection, skeleton);
            case RIGHT:
                return getAddRightElement(pacman, currentDirection, skeleton);
            case UP:
                return getAddUpElement(pacman, currentDirection, skeleton);
            case DOWN:
                return getAddDownElement(pacman, currentDirection, skeleton);
        }
        throw new IllegalArgumentException("Unrecognized Direction.");
    }

    public int getAddDownElement(Pacman pacman, Direction currentDirection, int[][] skeleton) {
        int x, y;
        if (currentDirection == RIGHT) {
            x = (int) Math.floor(pacman.getX() / stepX);
        } else {
            x = (int) Math.ceil(pacman.getX() / stepX);
        }
        y = (int) (pacman.getY() / stepY) - 1;
        return skeleton[y][x];
    }

    public int getAddLeftElement(Pacman pacman, Direction currentDirection, int[][] skeleton) {
        int x, y;
        if (currentDirection == Direction.DOWN) {
            y = (int) Math.ceil(pacman.getY() / stepY);
        } else {
            y = (int) Math.floor(pacman.getY() / stepY);
        }
        x = (int) (pacman.getX() / stepX) - 1;
        return skeleton[y][x];
    }

    public int getAddRightElement(Pacman pacman, Direction currentDirection, int[][] skeleton) {
        int x, y;
        if (currentDirection == Direction.DOWN) {
            y = (int) Math.ceil(pacman.getY() / stepY);
        } else {
            y = (int) Math.floor(pacman.getY() / stepY);
        }
        x = (int) (pacman.getX() / stepX) + 1;
        return skeleton[y][x];
    }

    public int getAddUpElement(Pacman pacman, Direction currentDirection, int[][] skeleton) {
        int x, y;
        if (currentDirection == RIGHT) {
            x = (int) Math.floor(pacman.getX() / stepX);
        } else {
            x = (int) Math.ceil(pacman.getX() / stepX);
        }
        y = (int) (pacman.getY() / stepY) + 1;
        return skeleton[y][x];
    }
}
