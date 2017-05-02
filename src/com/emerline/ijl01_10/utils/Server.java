package com.emerline.ijl01_10.utils;


import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * Created by dzmitry.karayedau on 28-Apr-17.
 */
public class Server {
    private ArrayList<Message> messages = new ArrayList<Message>();
    private MulticastSocket multicastSocket;
    private InetAddress group;
    private int port;
    private boolean infinite = true;

    public Server(int port, String inetAddress) throws IOException, ClassNotFoundException {
        this.multicastSocket = new MulticastSocket(port);
        this.port = port;
        this.group = InetAddress.getByName(inetAddress);
        multicastSocket.joinGroup(group);
        receiveMessages();
    }

    private void receiveMessages() throws IOException, ClassNotFoundException {
        byte buf[] = new byte[1024];
        DatagramPacket data = new DatagramPacket(buf, buf.length);
        while (infinite) {
            this.multicastSocket.receive(data);
            ByteArrayInputStream bais = new ByteArrayInputStream(data.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Message receiveMessage = (Message) ois.readObject();
            processMessage(receiveMessage);
        }
        this.multicastSocket.close();
    }

    private void processMessage(Message message) throws IOException {
        switch (message.getCommand()) {
            case "getLastMessages": {
                sendLastTenMessages(message.getSender());
                break;
            }
            case "send": {
                messages.add(message);
                System.out.println(message.getAuthor() + ": " + message.getMessageBody());
                break;
            }
        }
    }

    private void sendLastTenMessages(String destination) throws IOException {
        if (messages.size() < 10) {
            for (Message mes : messages) {
                Message sMes = new Message("returnLastMessages", "Server", destination, mes.getAuthor(), mes.getMessageBody());
                sendMessage(sMes);
            }
        } else {
            for (int i = messages.size() - 10; i <= messages.size() - 1; i++) {
                Message sMes = new Message("returnLastMessages", "Server", destination, messages.get(i).getAuthor(), messages.get(i).getMessageBody());
                sendMessage(sMes);
            }
        }
    }

    private void sendMessage(Message message) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        byte[] sdata = baos.toByteArray();
        DatagramPacket data = new DatagramPacket(sdata, 0 , sdata.length ,  group , port);
        multicastSocket.send(data);
    }

}
