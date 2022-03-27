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
        salida.writeDouble(12345.12345);
        salida.write("Hola".getBytes()); // write envía un arreglo de bytes y para eso ocupamos el método getBytes(). getBytes utiliza UTF-8

        ByteBuffer b = ByteBuffer.allocate(5*8); // 5 números double, y cada uno de ellos ocupa 8 bytes.
        b.putDouble(1.1);
        b.putDouble(1.2);
        b.putDouble(1.3);
        b.putDouble(1.4);
        b.putDouble(1.5);
        byte[] a = b.array();
        salida.write(a);


        byte[] buffer = new byte[4]; // Aquí se van a guardar los bytes recibidos del servidor.
        /* El método read tiene 3 parametros: 
            -> Arreglo de bytes con una longitud suficiente para contener los bytes a recibir
            -> Indica la posición, dentro del arreglo de bytes, donde se pondrán los bytes a recibir
            -> Indica el número de bytes a recibir
        */
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