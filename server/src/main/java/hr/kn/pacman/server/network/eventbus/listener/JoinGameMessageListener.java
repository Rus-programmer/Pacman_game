package hr.kn.pacman.server.network.eventbus.listener;

import com.badlogic.pacman.dto.request.JoinGameMessage;
import com.badlogic.pacman.dto.response.JoinGameMessageResponse;
import com.badlogic.pacman.dto.response.NewPlayerJoinedResponse;
import com.badlogic.pacman.dto.serialization.GameMessageSerde;
import com.badlogic.pacman.model.Pacman;
import com.google.common.eventbus.Subscribe;
import hr.kn.pacman.server.game.ConfigParser;
import hr.kn.pacman.server.game.GameState;
import hr.kn.pacman.server.game.manager.ChannelAndSender;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.DatagramPacket;

import java.util.Map;
import java.util.Random;

public class JoinGameMessageListener implements ServerMessageListener<JoinGameMessage> {

    private Random r = new Random();

    @Subscribe
    @Override
    public void handle(JoinGameMessage message) {
        Map<String, Integer> configMap = ConfigParser.getConfigMap();

        Pacman pacman = new Pacman(message.getDesiredPacmanId(), ConfigParser.getConfigMap().get("pacmanX"), ConfigParser.getConfigMap().get("pacmanY"));
        JoinGameMessageResponse response = new JoinGameMessageResponse("SUCCESS", GameState.pacmanMapManager.getPacmanMap(),
                pacman, configMap, ConfigParser.getFiled());

        ByteBuf bufResponse = Unpooled.copiedBuffer(GameMessageSerde.serialize((response)));
        ChannelFuture f1 = message.getCtx().channel().writeAndFlush(new DatagramPacket(bufResponse, message.getSender()));

        f1.addListener(f -> {
            if (f1.isSuccess()) {
                broadcastNewPlayerInfo(pacman);
                GameState.pacmanMapManager.getPacmanMap().put(pacman.getPacmanId(), pacman);
                GameState.channelManager.getChannels().put(
                        pacman.getPacmanId(), new ChannelAndSender(message.getCtx().channel(), message.getSender())
                );
            }
        });
    }

    private void broadcastNewPlayerInfo(Pacman pacman) {
        NewPlayerJoinedResponse newPlayerMessage = new NewPlayerJoinedResponse(pacman);
        ByteBuf newPlayerBuf = Unpooled.copiedBuffer(GameMessageSerde.serialize((newPlayerMessage)));
        GameState.channelManager.getChannels().forEach((s, channelAndSender) -> {
                    newPlayerBuf.retain();
                    channelAndSender.getChannel().writeAndFlush(new DatagramPacket(newPlayerBuf, channelAndSender.getSender()));
                }
        );
    }
}
