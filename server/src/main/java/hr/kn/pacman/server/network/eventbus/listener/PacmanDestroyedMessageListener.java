package hr.kn.pacman.server.network.eventbus.listener;

import com.badlogic.pacman.client.eventbus.DestroyedPacmanResponseListener;
import com.badlogic.pacman.dto.request.PacmanDestroyedMessage;
import com.badlogic.pacman.dto.response.DestroyedMessageResponse;
import com.badlogic.pacman.dto.response.PacmanMoveMessageResponse;
import com.badlogic.pacman.dto.serialization.GameMessageSerde;
import com.google.common.eventbus.Subscribe;
import hr.kn.pacman.server.game.GameState;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

public class PacmanDestroyedMessageListener implements ServerMessageListener<PacmanDestroyedMessage> {

    @Subscribe
    @Override
    public void handle(PacmanDestroyedMessage message) {
        GameState.pacmanMapManager.getPacmanMap().remove(message.getPacmanId());

        DestroyedMessageResponse response = new DestroyedMessageResponse(message.getPacmanId());
        ByteBuf bufResponse = Unpooled.copiedBuffer(GameMessageSerde.serialize((response)));
        GameState.channelManager.getChannels().forEach((s, channelAndSender) -> {
            bufResponse.retain();
            channelAndSender.getChannel().writeAndFlush(new DatagramPacket(bufResponse, channelAndSender.getSender()));
        });
    }

}
