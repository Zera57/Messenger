import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements EventListener{

    public static void main(String[] args) {
        new ChatServer();
    }

    ArrayList<Connection> connectionList;

    private ChatServer() {
        connectionList = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("Server " + InetAddress.getLocalHost().getHostAddress() + " : 8000" + " running....");
            while (true) {
                try {
                    new Connection(serverSocket.accept(), this);
                } catch (IOException e) {
                    System.out.println("Connection: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public synchronized void connectEvent(Connection connection) {
        System.out.println("Connected: " + connection);
        connectionList.add(connection);
        sendAll("Connected: " + connection);
    }

    @Override
    public synchronized void disconnectEvent(Connection connection) {
        System.out.println("Disconnected: " + connection);
        connectionList.remove(connection);
        sendAll("Disconnected: " + connection);
    }

    @Override
    public synchronized void sendEvent(Connection connection, String value) {
        System.out.println(connection + " msg: " + value);
//        sendAll(connection + " msg: " + value);
        sendAll(value);
    }

    @Override
    public synchronized void exceptionEvent(Connection connection, Exception e) {
        System.out.println("Connection: " + e);
    }

    private void sendAll(String value) {
        for (var connection : connectionList) { connection.sendMsg(value); }
    }
}
