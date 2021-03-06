package hr.kn.pacman.server;

import com.google.common.eventbus.EventBus;
import hr.kn.pacman.server.game.ConfigParser;
import hr.kn.pacman.server.network.codec.GameClientMessageDecoder;
import hr.kn.pacman.server.network.codec.GameClientMessageHandler;
import hr.kn.pacman.server.network.eventbus.listener.PacmanDestroyedMessageListener;
import hr.kn.pacman.server.network.eventbus.listener.PacmanMoveMessageListener;
import hr.kn.pacman.server.network.eventbus.listener.JoinGameMessageListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.AttributeKey;

import java.net.InetAddress;

public class UdpServer {

    private EventBus eventBus = new EventBus();


    private final Integer SERVER_PORT = 9956;
    private final Integer MAX_PACKET_SIZE = 10_000;


    public void run() throws Exception {
        eventBus.register(new PacmanMoveMessageListener());
        eventBus.register(new JoinGameMessageListener());
        eventBus.register(new PacmanDestroyedMessageListener());


        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_RCVBUF, MAX_PACKET_SIZE)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(MAX_PACKET_SIZE))
                    .attr(AttributeKey.newInstance("eventBus"), eventBus)
                    .handler(channelInitializer());

            InetAddress address = InetAddress.getLocalHost();
            System.out.printf("Waiting for messages [%s:%d]\n", String.format(address.toString()), SERVER_PORT);
            b.bind(address, SERVER_PORT).sync().channel().closeFuture().await();
        } finally {
        }
    }

    private ChannelInitializer<NioDatagramChannel> channelInitializer() {
        return new ChannelInitializer<NioDatagramChannel>() {
            @Override
            public void initChannel(final NioDatagramChannel ch) {
                ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(2048));
                ChannelPipeline p = ch.pipeline();
                p.addLast(new GameClientMessageDecoder());
                p.addLast(new GameClientMessageHandler(eventBus));
            }
        };
    }

    public static void main(String[] args) throws Exception {
        new ConfigParser().readGameConfig();
        new UdpServer().run();
    }
}
