import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Cliente{

    public static void main(String[] args) throws Exception{

        Socket conexion = null;
        
        for(;;)
            try {
                conexion = new Socket("localhost", 50000); // Servidor | puerto
                break;
            } catch (Exception e) {
                Thread.sleep(500);
            }
        
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream()); // Para enviar al servidor   
        DataInputStream entrada = new DataInputStream(conexion.getInputStream()); // Para recibir del servidor

        salida.writeInt(123);
        

        byte[] buffer = new byte[4];
        read(entrada, buffer, 0, 4);
        System.out.println(new String(buffer, "UTF-8"));


        Thread.sleep(1000); // Permite que el servidor tenga tiempo de recibir los datos.
        conexion.close();
    }

    static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception{
        /**
         * El método read podría obtener solo una parte del mensaje enviado, y para solucionarlo creamos este método donde
         * se invoca repetidamente el método read hasta recibir el mensaje completo.
         */
        while(longitud > 0){
            int n = f.read(b, posicion, longitud);
            posicion += n;
            longitud -= n;
        }
    }
}
