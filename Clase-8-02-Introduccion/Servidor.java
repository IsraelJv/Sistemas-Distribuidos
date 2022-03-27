import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Servidor {

    public static void main(String[] args) throws Exception{
        ServerSocket servidor = new ServerSocket(50000);
        Socket conexion = servidor.accept(); // accept espera hasta recibir una conexión del cliente.

        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        // Entrada al servidor
        int n = entrada.readInt();
        System.out.println(n);
        double x = entrada.readDouble();
        System.out.println(x);
        byte[] buffer = new byte[4];
        read(entrada, buffer, 0, 4);

        byte[] a = new byte[5*8];
        read(entrada, a, 0, 5*8);
        ByteBuffer b = ByteBuffer.wrap(a);
        for(int i=0; i<5; i++)
            System.out.println(b.getDouble());

        // Salida del servidor
        salida.write("HOLA".getBytes());

        
        servidor.close();
    }

    static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception{
        while(longitud > 0){
            int n = f.read(b, posicion, longitud);
            posicion += n;
            longitud -= n;
        }
    }
    

}
