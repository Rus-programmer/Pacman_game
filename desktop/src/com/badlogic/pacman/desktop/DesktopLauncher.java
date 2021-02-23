package com.badlogic.pacman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.pacman.MyPacmanGame;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DesktopLauncher {
    public static void main(String[] arg) throws UnknownHostException {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 506;
        config.height = 484;
        String server = arg.length == 1 ? arg[0] : InetAddress.getLocalHost().getHostAddress();
        new LwjglApplication(new MyPacmanGame(server), config);
    }
}
