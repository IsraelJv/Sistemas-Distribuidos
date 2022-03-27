import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Servidor{

    static class Worker extends Thread{
        
        Socket conexion;
        
        Worker(Socket conexion){
            this.conexion = conexion;
        }

        public void run(){
            try{
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());

                // Entrada al servidor
                int n = entrada.readInt();
                System.out.println(n);

                // Salida del servidor
                salida.write("HOLA".getBytes());

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception{
        while(longitud > 0){
            int n = f.read(b, posicion, longitud);
            posicion += n;
            longitud -= n;
        }
    }

    public static void main(String[] args) throws Exception{
        
        ServerSocket servidor = null;
        for(;;){
            servidor = new ServerSocket(50000);
            Socket conexion = servidor.accept();
            Worker w = new Worker(conexion);
            w.start();
            w.join(); // El hilo principal necesita espera a que termine de ejecutarse
            servidor.close();
        }
    }
}