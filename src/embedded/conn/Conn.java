package embedded.conn;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Conn {
    private InputStream serialIn;
    private Integer tax;
    private String portCOM;
    private BufferedReader reader;
    private InputStreamReader isr;
    private BufferedReader bufferedReader = null;

    SerialPort port;

    public Conn(Integer tax, String portCOM) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.tax = tax;
        this.portCOM = portCOM;
        this.initialize();
    }

    private void initialize() {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(this.portCOM);;
            if (portIdentifier.isCurrentlyOwned()){
                System.out.print("Error");
            } else {
                CommPort portId = portIdentifier.open(this.portCOM, this.tax);

                if (portId instanceof SerialPort){

                    port = (SerialPort) portId;
                    serialIn = port.getInputStream();
                    port.setSerialPortParams(this.tax,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);

                    isr = new InputStreamReader(serialIn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeIn() {
        try {
            serialIn.close();
            reader.close();
            isr.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public String receiveData() {
        String hex = null;

        if (bufferedReader == null) reader = new BufferedReader(isr);

        try {
            hex = reader.readLine();
            System.out.println(hex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hex;
    }
}
