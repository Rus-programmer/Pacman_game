package com.badlogic.pacman.client.network;

import com.badlogic.pacman.MyPacmanGame;
import com.badlogic.pacman.dto.serialization.GameMessageSerde;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

// TODO bolje codece sloziti za klijenta
public class ClientPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        int readable = buf.readableBytes();
        byte[] bytes = new byte[readable];
        buf.readBytes(bytes);
        Object object = GameMessageSerde.deserialize(bytes);
        MyPacmanGame.eventBus.post(object);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
