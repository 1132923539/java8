package net.java8.channel;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeTest {
    @Test
    public void pipeTest() throws IOException {
        //1.获取管道
        Pipe pipe=Pipe.open();

        //2.将缓冲区数据写入管道,即发送
        ByteBuffer buf=ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel=pipe.sink();
        buf.put("这是通过管道发送的数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);
        buf.clear();

        //另一个线程来接受
        new Thread(new Runnable() {
            @Override
            public void run() {
                Pipe.SourceChannel sourceChannel=pipe.source();
                int len=0;
                try {
                    len=sourceChannel.read(buf);
                    System.out.println(new String(buf.array(),0,len));
                    sourceChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
