package net.java8.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;

public class UDPNoneBlock {

    @Test
    public void send() throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();

        datagramChannel.configureBlocking(false);

        ByteBuffer buf=ByteBuffer.allocate(1024);

        buf.put(LocalDateTime.now().toString().getBytes());
        buf.flip();

        datagramChannel.send(buf,new InetSocketAddress("localhost",9988));
        datagramChannel.send(buf,new InetSocketAddress("localhost",9988));
        buf.clear();
    }

    @Test
    public void receive() throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();

        datagramChannel.configureBlocking(false);

        datagramChannel.bind(new InetSocketAddress(9988));

        Selector selector=Selector.open();

        datagramChannel.register(selector,SelectionKey.OP_READ);

        while (selector.select()>0){
            Iterator<SelectionKey> it= selector.selectedKeys().iterator();

            while (it.hasNext()){
                SelectionKey sk=it.next();

                //UDP连接没有accept步骤，因此可以不用判断isAccept
                if (sk.isReadable()){
                    ByteBuffer buf=ByteBuffer.allocate(1024);

                    datagramChannel.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(),0,buf.limit()));
                    buf.clear();
                }
            }
            //删除it.next所指向的最后一个元素，因此remove必须再next后面使用
            it.remove();

        }

    }
}
