package com.badlogic.pacman.client.network;

import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.dto.request.*;
import com.badlogic.pacman.dto.serialization.GameMessageSerde;
import com.badlogic.pacman.model.Direction;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UdpClient {

    public UdpClientState state = UdpClientState.DISCONNECTED;

    private Channel channel;
    private InetSocketAddress remoteAddress;

    private MyPacmanGame game;

    public UdpClient(MyPacmanGame game) {
        this.game = game;
    }

    public void start(String server) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .handler(channelInitializer(this));
        try {
            String host = server != null ? server : InetAddress.getLocalHost().getHostAddress();
            this.remoteAddress = new InetSocketAddress(host, 9956);
            this.channel = b.bind(0).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void joinGame(String pacmanId) {
        send(new JoinGameMessage(pacmanId));
    }

    public void movePacman(Direction direction, String pacmanId, float x, float y) {
        send(new PacmanMoveMessage(direction, pacmanId, x, y));
    }

    public void reportPacmanDestroyed(String pacmanId) { send(new PacmanDestroyedMessage(pacmanId)); }

    private void send(GameClientMessage message) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(GameMessageSerde.serialize(message));
        if (byteBuf.readableBytes() > 0) {
            this.channel.writeAndFlush(new DatagramPacket(byteBuf, this.remoteAddress));
        }
    }

    private ChannelInitializer<NioDatagramChannel> channelInitializer(UdpClient udpClient) {
        return new ChannelInitializer<NioDatagramChannel>() {
            @Override
            public void initChannel(final NioDatagramChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new ClientPacketHandler());
            }
        };
    }
}
