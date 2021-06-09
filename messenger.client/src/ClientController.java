import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientController implements EventListener {

    private ClientView clientView;
    private ClientModel clientModel;
    private Connection connection;



    public ClientController(String name) {
        try {
            clientModel = new ClientModel("192.168.0.8", 8000, name);
            clientView = new ClientView(ClientController.this, clientModel);
            connection = new Connection(clientModel.getSocket(), this);
        } catch (IOException e) {
            exceptionEvent(connection, e);
        }
    }

    @Override
    public void connectEvent(Connection connection) {
        System.out.println("Connections is ready!");
        clientView.PrintMsg("Connections is ready!");
    }

    @Override
    public void disconnectEvent(Connection connection) {
        clientView.PrintMsg("Connections is lost!");
    }

    @Override
    public void sendEvent(Connection connection, String value) {
        clientView.PrintMsg(value);
    }

    @Override
    public void exceptionEvent(Connection connection, Exception e) {
        clientView.PrintMsg("Exception: " + e);
    }

    public void SendMsg(String value) {
        connection.sendMsg(value);
    }


}
