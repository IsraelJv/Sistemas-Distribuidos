import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class ServidorMulticast {
    
    /**
     * Recibe como parámetros: 
     * -> un arreglo de bytes(el mensaje)
     * -> la dirección IP clase D que identifica el grupo al cual se enviará el mensaje
     * -> el número de ṕuerto
     */
    static void enviaMensaje(byte[] buffer, String ip, int puerto) throws IOException{
        DatagramSocket socket = new DatagramSocket();
        InetAddress grupo = InetAddress.getByName(ip);
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length, grupo, puerto);
        socket.send(paquete);
        socket.close();
    }

    public static void main(String[] args) throws IOException{
        System.setProperty("java.net.preferIPv4Stack", "true");

        enviaMensaje("hola".getBytes(), "230.0.0.0", 50000);

        ByteBuffer b = ByteBuffer.allocate(5*8);
        for(int i=0; i<5; i++)
            b.putDouble(i);
        enviaMensaje(b.array(), "230.0.0.0", 50000);
    }
}
