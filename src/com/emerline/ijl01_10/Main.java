package com.emerline.ijl01_10;

import com.emerline.ijl01_10.utils.Server;

import java.io.*;

public class Main {

    final static int PORT = 8885;
    final static String INET_ADDRES = "224.2.2.5";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server(PORT , INET_ADDRES);
    }
}
