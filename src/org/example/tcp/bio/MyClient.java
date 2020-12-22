package org.example.tcp.bio;

import java.io.*;
import java.net.*;

public class MyClient {

    private static class ClientThread implements Runnable {

        // 该线程负责处理的Socket
        private Socket s;

        // 该线程所处理的Socket所对应的输入流
        BufferedReader br = null;

        public ClientThread(Socket s) throws IOException {
            this.s = s;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String content = null;
                // 不断读取Socket输入流中的内容，并将这些内容打印输出
                while ((content = br.readLine()) != null) {
                    System.out.println(content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 30000);
        // 客户端启动ClientThread线程不断读取来自服务器的数据
        new Thread(new ClientThread(s)).start();
        // 获取该Socket对应的输出流
        PrintStream ps = new PrintStream(s.getOutputStream());
        String line = null;
        // 不断读取键盘输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while ((line = br.readLine()) != null) {
            // 将用户的键盘输入内容写入Socket对应的输出流
            ps.println(line);
        }
    }

}

