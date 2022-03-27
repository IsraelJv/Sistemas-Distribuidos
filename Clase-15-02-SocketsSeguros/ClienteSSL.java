import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

public class ClienteSSL {
    
    public static void main(String[] args) throws Exception{
        SSLSocketFactory cliente = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket conexion = cliente.createSocket("localhost", 50000);

        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        salida.writeDouble(1234.5678);

        Thread.sleep(1000);
        conexion.close();
    }

}
