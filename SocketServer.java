import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.charset.Charset;

public class SocketServer {

    public static void main(String[] args) throws Exception {
        Queue<String> quotes = new LinkedList<>();
        
        
        String[] record = {
            "Life is pain, Highness. Anyone who says differently is selling something.(Princess Bride)", 
            "So, Even Though You Have Broken My Heart Yet Again, I Wanted To Say, In Another Life, I Would Have Really Liked Just Doing Laundry And Taxes With You.(Everything Everywhere All At Once)"
            , "Memories can be painful. To forget may be a blessing!(Kung Fu Hustle(my fav))"
        };
        for (String i : record) {
            quotes.add(i);
        }
        

        ServerSocket server = new ServerSocket(17);
        System.out.println("Server on at: " + server.getInetAddress().getLocalHost());
        Socket tSocket = null;
        DatagramSocket dSocket = new DatagramSocket(17);
        while ((tSocket = server.accept()) != null) {
            OutputStream out = tSocket.getOutputStream();
            System.out.println(tSocket.getInetAddress()+"is now connected");
            String answer = quotes.remove();
            byte[] result = answer.getBytes(Charset.forName("US-ASCII"));
            quotes.add(answer);
            processRequest clientSock = new processRequest(tSocket, out, result);
            new Thread(clientSock).start();
        }
        server.close();
    }
}

    class processRequest extends Thread {
        
        final Socket s;
        final OutputStream out;
        final byte[] result;

        

        public processRequest(Socket s, OutputStream out, byte[] result)
        {
            this.s = s;
            this.out = out;
            this.result = result;
        }

        public void run() {
            try {
                
                OutputStream out = s.getOutputStream();
                out.write(result);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.out.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        
        }
    }




