import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
public class Client  
{ 
    final static int ServerPort = 1234; 
  
    
    /**
	 * Metodo encargado de realizar la encriptacion de Cesar, sumando 2 posiciones al ASCII
	 * de cada caracter
	 * @param mensajeObtenidoCliente != Null && != ""
	 * @return
	 */
	private static String metodoServicioServer(String mensajeObtenidoCliente, int clave) {
		
		String respuesta = "";
		char caracter ;
		
		if(mensajeObtenidoCliente != "") {
			for (int i = 0; i < mensajeObtenidoCliente.length(); i++) {
				caracter = mensajeObtenidoCliente.charAt(i);
				caracter = (char) (caracter +clave);
				respuesta += Character.toString((caracter)) + "";
			}
		}
		else {
			respuesta = "::El cliente no envio texto para encriptar::";
		}
		return respuesta;
		
	}
    public static void main(String args[]) throws UnknownHostException, IOException  
    { 
        Scanner scn = new Scanner(System.in); 
          
        // getting localhost ip 
        InetAddress ip = InetAddress.getByName("localhost"); 
          
        // establish the connection 
        Socket s = new Socket(ip, ServerPort); 
          
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
  
       String clave = dis.readUTF();
        System.out.println("clave"+ clave);
        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
                while (true) { 
  
                    // read the message to deliver. 
                    String msg = scn.nextLine(); 
                      
                    try { 
                        // write on the output stream 
                        dos.writeUTF(msg); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        String mensajeEncriptado=metodoServicioServer(msg,Integer.parseInt(clave));
                        System.out.println(mensajeEncriptado); 
                        
                    } catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
  
        sendMessage.start(); 
        readMessage.start(); 
  
    } 
}