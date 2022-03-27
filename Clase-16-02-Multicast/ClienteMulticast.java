import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;

public class ClienteMulticast {
    
    static byte[] recibeMensaje(MulticastSocket socket, int longitudMensaje) throws IOException{
        byte[] buffer = new byte[longitudMensaje];
        DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
        socket.receive(paquete);
        return paquete.getData();
    }


    public static void main(String[] args) throws Exception{

        System.setProperty("java.net.preferIPv4Stack", "true");

        // Se utiliza para obtener el grupo, en este caso se obtiene el grupo identificado por la IP 230.0.0.0
        InetAddress grupo = InetAddress.getByName("230.0.0.0"); 
        // Obtenemos un socket asociado al puerto 50000
        MulticastSocket socket = new MulticastSocket(50000);
        // Para que el cliente pueda recibir los mensajes, unimos el socket al grupo
        socket.joinGroup(grupo);

        // Se recibe una cadena de caracteres
        byte[] a = recibeMensaje(socket, 4);
        System.out.println(new String(a, "UTF-8"));

        // SE recibe 5 números punto flotante empacados como un arreglo de bytes
        byte[] buffer = recibeMensaje(socket, 5*8);
        ByteBuffer b = ByteBuffer.wrap(buffer);
        for(int i=0; i<5; i++)
            System.out.println(b.getDouble());

        // Invocamos el método leaveGroup para que el socket abandone el grupo y cerramos el socket
        socket.leaveGroup(grupo);
        socket.close();


        /*
        MulticastSocket socket = new MulticastSocket(50000);
        InetSocketAddress grupo = new InetSocketAddress(InetAddress.getByName("230.0.0.0"), 50000);
        NetworkInterface netInter = NetworkInterface.getByName("em1"); // network interface indica el driver que usa
        // Las interfaces em0 y bge0 sonutilizadas para WAN y las interfaces em1 y bge1 son utilizadas para LAN
        socket.joinGroup(grupo, netInter); 

        byte[] a = recibeMensaje(socket, 4);
        System.out.print(new String(a, "UTF-8"));

        byte[] buffer = recibeMensaje(socket, 5*8);
        ByteBuffer b = ByteBuffer.wrap(buffer);
        for(int i=0; i<5; i++)
            System.out.println(b.getDouble());

        socket.leaveGroup(grupo, netInter);
        socket.close();
        */
    }
}
