import java.io.IOException;
import java.net.Socket;

class ClientModel {
    private final Socket socket;
    private String name;

    ClientModel(String ip, int port, String name) throws IOException {
        this.socket = new Socket(ip, port);
        this.name = name;
    }


    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
