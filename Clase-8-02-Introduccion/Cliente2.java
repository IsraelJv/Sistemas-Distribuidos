import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Cliente2 {
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

        
        long Tinicial = System.currentTimeMillis();
        for(int i=0; i<10000; i++)
            salida.writeDouble(i);
        long Tfinal = System.currentTimeMillis();
        System.out.println("El tiempo tardado utilizando el metodo writeDouble es de " + (Tfinal-Tinicial));


        Tinicial = 0;
        Tfinal = 0;


        Tinicial = System.currentTimeMillis();
        ByteBuffer b = ByteBuffer.allocate(10000*8); // 5 números double, y cada uno de ellos ocupa 8 bytes.
        for(int i=0; i<10000; i++)
            b.putDouble(i);
        byte[] a = b.array();
        salida.write(a);
        Tfinal = System.currentTimeMillis();
        System.out.println("El tiempo tardado utilizando el metodo putDouble es de " + (Tfinal-Tinicial));


        Thread.sleep(1000); // Permite que el servidor tenga tiempo de recibir los datos.
        conexion.close();
    }

    static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception{
        while(longitud > 0){
            int n = f.read(b, posicion, longitud);
            posicion += n;
            longitud -= n;
        }
    }

}
