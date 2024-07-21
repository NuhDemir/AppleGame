
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Multiplayer {
    private Field gameField;
    private int gamePort;
    private volatile boolean keepListening;
    private volatile boolean keepPlaying;
    private volatile boolean startNewGame;
    private volatile boolean disconnecting;
    private Server server;
    private Thread serverThread;
    private Thread clientThread;

    public Multiplayer(Field field){

    }

    public Multiplayer(Field field,int port){

    }
    public void startServer(){}
    public void joinGame(String otherServer){}
    public void startGame(){}
    public void disconnected(){}

    class Server implements Runnable {

        @Override
        public void run() {

        }
        public void stopListening(){

        }
    }
    class Client implements Runnable {
     String gameHost;
     boolean startNewGame;

        public Client(String host) {}


        @Override
        public void run() {

        }

    }

}
