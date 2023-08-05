package io.github.rephrasing.gsoncommunication.sender;

import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.io.*;
import java.net.*;

public class SocketDataSender {

    private final Socket socket;
    private final DataOutputStream out;

    @SneakyThrows
    public SocketDataSender(String address, int port) {
        this.socket = new Socket(address, port);
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @SneakyThrows
    public void send(JsonElement element) {
        String toSend = element.getAsString();
        out.writeUTF(toSend);
    }

    @SneakyThrows
    public void closeConnection() {
        out.close();
        socket.close();
    }
}
