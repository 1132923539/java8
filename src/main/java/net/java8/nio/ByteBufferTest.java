package net.java8.nio;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author eltons,  Date on 2018-08-16.
 */
public class ByteBufferTest {
    @Test
    public void bufferTset() throws UnsupportedEncodingException {
        byte[] bytes = new byte[1024];

        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put("哈哈哈哈a".getBytes());
//        bb.put("嘿嘿嘿".getBytes());

        System.out.println(bb.position());
        bb.get(bytes, 0, 6);
        bb.rewind();
        bb.get(bytes, 0, 6);

        System.out.println(new String(bytes));

    }

    @Test
    public void test2() {
        ByteBuffer b = ByteBuffer.allocate(1024);
        b.put("abcdefgh".getBytes());

        b.flip();
        byte[] bytes = new byte[b.limit()];
        b.get(bytes, 0, 2);
        System.out.println(new String(bytes));

        b.mark();
        b.get(bytes, 3, 2);
        System.out.println(new String(bytes, 3, 2).toString());

        b.reset();
        b.get(bytes, 3, 2);
        System.out.println(new String(bytes, 4, 2));
    }

    @Test
    public void test3() {
        System.out.println(System.currentTimeMillis());
    }
}
