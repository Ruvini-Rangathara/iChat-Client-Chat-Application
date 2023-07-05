package com.example.ichatclient.util;

import com.example.ichatclient.controller.MessageFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Client implements Runnable, Serializable {
    private final String name;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private MessageFormController messageFormController;

    public Client(String name) throws IOException {
        this.name = name;

        socket = new Socket("localhost", 1235);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        outputStream.writeUTF(name);
        outputStream.flush();

        try {
            loadScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("__________________________BYE_______________________");
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF(name + " joined to chat");
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (socket.isConnected()) {
            try {
                String message = inputStream.readUTF();
                if (message.equals("*image*")) {
                    receiveImage();
                } else {
                    messageFormController.writeMessage(message);
                }

            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        outputStream.writeUTF(msg);
        outputStream.flush();
    }

    public void sendImage(byte[] bytes) throws IOException {
        outputStream.writeUTF("*image*");
        outputStream.writeInt(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
    }

    private void loadScene() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/message-form.fxml"));
        Parent parent = loader.load();
        messageFormController = loader.getController();
        messageFormController.setClient(this);

        stage.setResizable(false);
        stage.setScene(new Scene(parent));
        stage.setTitle(name + "'s Chat");
        stage.show();

        stage.setOnCloseRequest(event -> {
            try {
                System.out.println(name + " closed");
                socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        });

    }

    public String getName() {
        return name;
    }

    private void receiveImage() throws IOException {
        String utf = inputStream.readUTF();
        int size = inputStream.readInt();
        byte[] bytes = new byte[size];
        inputStream.readFully(bytes);
        System.out.println(name + "- Image received: from " + utf);
        messageFormController.setImage(bytes, utf);
        // Handle the received image bytes as needed
    }
}
