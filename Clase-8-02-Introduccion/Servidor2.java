import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Servidor2 {

    public static void main(String[] args) throws Exception{
        ServerSocket servidor = new ServerSocket(50000);
        Socket conexion = servidor.accept(); // accept espera hasta recibir una conexión del cliente.

        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        // Entrada al servidor
        long Tinicial = System.currentTimeMillis();
        for(int i=0; i<10000; i++)
            System.out.println(entrada.readDouble());
        long Tfinal = System.currentTimeMillis();
        long tiempo1 = Tfinal-Tinicial;

        Tinicial = 0;
        Tfinal = 0;


        Tinicial = System.currentTimeMillis();
        byte[] a = new byte[10000*8];
        read(entrada, a, 0, 10000*8);
        ByteBuffer b = ByteBuffer.wrap(a);
        for(int i=0; i<10000; i++)
            System.out.println(b.getDouble());
        Tfinal = System.currentTimeMillis();
        
        System.out.println("El tiempo tardado utilizando el metodo getDouble es de " + (Tfinal-Tinicial) + " y con el metodo readDouble es de " + tiempo1);
        
        Thread.sleep(1000);
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
