package net.java8.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author eltons,  Date on 2018-08-20.
 */
public class ChannelBufferTest {
    @Test
    public void scatterTest() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("C:\\Users\\lenovo\\Desktop\\dic.sql", "rw");
        FileChannel channel = raf1.getChannel();

        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        ByteBuffer[] bufs = {buf1, buf2};

        channel.read(bufs);
        for (ByteBuffer buf : bufs
                ) {
            buf.flip();
            System.out.println(new String(buf.array(), 0, buf.limit()));
        }
    }
}
