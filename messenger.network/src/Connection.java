import java.io.*;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Thread rxThread;
    private final EventListener eventListener;
    private final BufferedReader in;
    private final BufferedWriter out;


    public Connection(Socket socket, EventListener eventListener) throws IOException {
        this.socket = socket;
        this.eventListener = eventListener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.connectEvent(Connection.this);
                    while (!rxThread.isInterrupted())
                        eventListener.sendEvent(Connection.this, in.readLine());

                } catch (IOException e) {
                    eventListener.exceptionEvent(Connection.this, e);
                } finally {
                    eventListener.disconnectEvent(Connection.this);
                }
            }
        });
    }

    public synchronized void sendMsg(String msg) {
        try {
            out.write(msg + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListener.exceptionEvent(this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.exceptionEvent(this, e);
        }
    }

    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ":" + socket.getPort();
    }
}
