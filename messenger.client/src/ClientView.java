import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientView  extends JFrame {

    private ClientModel clientModel;
    private ClientController clientController;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private final JTextArea logs = new JTextArea();
    private final JTextField fieldMsg = new JTextField();
    private final JTextField fieldName = new JTextField();

    public ClientView(ClientController clientController, ClientModel clientModel) {
        this.clientModel = clientModel;
        this.clientController = clientController;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
                setSize(WIDTH, HEIGHT);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                logs.setEditable(false);
                logs.setLineWrap(true);
                logs.setAutoscrolls(true);
                add(logs, BorderLayout.CENTER);

                fieldName.setText(clientModel.getName());
                fieldName.addActionListener(new AlChangeName());
                add(fieldName, BorderLayout.NORTH);


                fieldMsg.addActionListener(new AlSendMsg());
                add(fieldMsg, BorderLayout.SOUTH);
            }
        });

    }

    public String GetMsgText() {
        String msg = fieldMsg.getText();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fieldMsg.setText(null);
            }
        });
        return msg;
    }

    public void PrintMsg(String value) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                logs.append(value + "\n");
                logs.setCaretPosition(logs.getDocument().getLength());
            }
        });
    }

    class AlSendMsg implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = GetMsgText();
            clientController.SendMsg(clientModel.getName() + ": " + msg);
        }
    }

    class AlChangeName implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientModel.setName(fieldName.getText());
        }
    }
}
