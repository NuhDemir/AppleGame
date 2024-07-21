
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
}
