package net.java8.channel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChannelTest {

    @Test
    public void test1() throws IOException {
        long startTime = System.currentTimeMillis();
        try (
                FileInputStream fis = new FileInputStream("K:\\系统\\rhel-server-7.2-x86_64-dvd.iso");
                FileOutputStream fos = new FileOutputStream("K:\\系统\\511.iso");
                FileChannel inChannel = fis.getChannel();
                FileChannel outChannel = fos.getChannel()
        ) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                //这句清除缓冲区必须加上，否则while判断将死循环
                byteBuffer.clear();
            }
        }

        System.out.println("花费时间"+(System.currentTimeMillis()-startTime));
    }

    @Test
    public void test2() throws IOException {
        long startTime = System.currentTimeMillis();

        //获得通道
        FileChannel inChannel = FileChannel.open(Paths.get("K:\\系统", "RedFlagin_V8.0.iso"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("K:\\系统", "511.iso"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //获得内存映射读写文件,有大小限制，最大为2G
        MappedByteBuffer inMapedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

//        byte[] dst = new byte[inMapedBuf.limit()];
//        inMapedBuf.get(dst);
        outMappedBuf.put(inMapedBuf);

        inChannel.close();
        outChannel.close();

//        byte[] bytes = Files.readAllBytes(Paths.get("K:\\系统", "CentOS-7-x86_64-DVD-1511.iso"));
        System.out.println("花费时间"+(System.currentTimeMillis()-startTime));
    }

    @Test
    public void test3() throws IOException {
        long startTime = System.currentTimeMillis();

        //获得通道
        FileChannel inChannel = FileChannel.open(Paths.get("K:\\系统", "win8.1中文版（CoreCountrySpecific）-  x64.iso"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("K:\\系统", "511.iso"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

//        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.transferFrom(inChannel,0,inChannel.size());

        inChannel.close();
        outChannel.close();

        System.out.println("花费时间"+(System.currentTimeMillis()-startTime));
    }
}
