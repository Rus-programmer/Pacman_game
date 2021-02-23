package hr.kn.pacman.server.network.eventbus.listener;

public interface ServerMessageListener<T> {
    void handle(T message);
}