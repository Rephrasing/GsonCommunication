package io.github.rephrasing.gsoncommunication.sender;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.*;
import java.net.*;

public class SocketDataSender {
    private final String address;
    private final int port;
    private Socket socket;
    private DataOutputStream out;
    private final Gson gson = new Gson();

    public SocketDataSender(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void send(JsonElement element) {
        String toSend = gson.toJson(element);
        try {
            out.writeUTF(toSend);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() {
        try {
            this.socket = new Socket(address, port);
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
