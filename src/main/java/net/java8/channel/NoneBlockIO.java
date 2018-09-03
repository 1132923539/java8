package net.java8.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

public class NoneBlockIO {

    @Test
    public void client() throws IOException {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8899));

        //切换非阻塞模式
        socketChannel.configureBlocking(false);

        //分配指定大小缓冲区
        ByteBuffer buf=ByteBuffer.allocate(1024);

        //发送数据到服务端
        ByteBuffer put = buf.put(LocalDateTime.now().toString().getBytes());
        buf.flip();
        socketChannel.write(put);
        buf.clear();

        //关闭通道
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        //1.获取通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();

        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8899));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器上，并指定监听事件，（接收，发送，连接，监听等）
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        //6.轮训式的获取选择器上已经 “准备就绪”的事件
        while (selector.select()>0){
            //获取当前选择器中所有已就绪的监听事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();

                //判断就绪事件的种类并作出相应处理
                if(selectionKey.isAcceptable()){
                    //若"接收就绪" ，则获取客户端连接通道
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //切换客户端通道为非阻塞
                    socketChannel.configureBlocking(false);

                    //将通道绑定到选择器上面
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                if(selectionKey.isReadable()){
                    //获取当前选择器上“读就绪状态”
                    SocketChannel socketChannel= (SocketChannel) selectionKey.channel();

                    //读数据
                    ByteBuffer buf=ByteBuffer.allocate(1024);

                    int len=0;
                    while ((len=socketChannel.read(buf))>0){
                        buf.flip();
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }
                }
                //取消已完成的选择键 SelectionKey，否则会空指针异常
                iterator.remove();
            }
        }
    }
}
