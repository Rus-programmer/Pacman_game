package hr.kn.pacman.server.network.eventbus.listener;

import com.badlogic.pacman.dto.request.PacmanMoveMessage;
import com.badlogic.pacman.dto.response.PacmanMoveMessageResponse;
import com.badlogic.pacman.dto.serialization.GameMessageSerde;
import com.google.common.eventbus.Subscribe;
import hr.kn.pacman.server.game.ConfigParser;
import hr.kn.pacman.server.game.GameState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

public class PacmanMoveMessageListener implements ServerMessageListener<PacmanMoveMessage> {
    private int stepX;
    private int stepY;
    private StringBuilder sb;

    @Subscribe
    @Override
    public void handle(PacmanMoveMessage message) {
        GameState.pacmanMapManager.updatePacmanLocation(message.getPacmanId(), message.getX(), message.getY());
        stepX = ConfigParser.getConfigMap().get("width") / ConfigParser.getConfigMap().get("countWidth");
        stepY = ConfigParser.getConfigMap().get("height") / ConfigParser.getConfigMap().get("countHeight");
        if (message.getX() % stepX == 0 && message.getY() % stepY == 0) {
            sb = new StringBuilder(ConfigParser.getFiled());
            sb.setCharAt((int) (message.getY() / stepY * ConfigParser.getConfigMap().get("countWidth") + message.getX() / stepX + message.getY() / stepY), '1');
            ConfigParser.setFiled(sb.toString());
        }

        PacmanMoveMessageResponse response = new PacmanMoveMessageResponse(message.getDirection(), message.getPacmanId(),
                message.getX(), message.getY(), sb.toString());
        ByteBuf bufResponse = Unpooled.copiedBuffer(GameMessageSerde.serialize

                ((response)));
        GameState.channelManager.getChannels().forEach((s, channelAndSender) -> {
            bufResponse.retain();
            channelAndSender.getChannel().writeAndFlush(new DatagramPacket(bufResponse, channelAndSender.getSender()));
        });
    }
}
