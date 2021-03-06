package hr.kn.pacman.server.network.codec;

import com.badlogic.pacman.dto.request.GameClientMessage;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GameClientMessageHandler extends SimpleChannelInboundHandler<GameClientMessage> {

    private EventBus eventBus;

    public GameClientMessageHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameClientMessage msg) {
        msg.setCtx(ctx);
        eventBus.post(msg);
    }
}
