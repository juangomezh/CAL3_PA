package cal3;
import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
public class Server extends Thread{
    private int Puerto;
    private BufferedReader entrada;
    private boolean bound=false;
    private ServerSocket server;
    private Socket connection;
    private DataOutputStream output;
    private DataInputStream  input;
    private SuperMarket mercadona;
    public Server(Main main, CountDownLatch parar) throws IOException {
        entrada=new BufferedReader(new InputStreamReader(System.in));
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("System IP Address : " + (localhost.getHostAddress()).trim());
        System.out.println();
        mercadona=main.getMercadona();
        // try to find a non privileged port to connect the socket
        Puerto=1024;
        do
        {
        try
        {
        server = new ServerSocket(Puerto);
        bound=true;
        }catch(java.net.SocketException e)
        {
            bound=false;
            Puerto++;
            if(Puerto>=49151) //if it has reached this value, no more tcp ports are available in the system, impossible to execute
            {
                System.out.println("there is no port available in the system, program terminated");
                System.exit(0);
            }
        }
        }while(!bound && Puerto<=49151); //until no more ports, or the socket has been bound
        System.out.println("Socket port : " + Puerto);
        parar.countDown();
        start();
    }
    public void run()
    {
        try {
             // Create socket Port 5000
            System.out.println("Starting server...");
            Executor conjunto=new ThreadPoolExecutor(0,10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
            while (true) {
                // Wait for a connection
                this.connection = this.server.accept();     
                NewClass thread=new NewClass(connection, mercadona); 
                //if a client has connected, create a new thread to treat its necessities
                conjunto.execute(thread); //maximum of 10 simultaneous threads
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public static void main(String args[]) throws IOException, InterruptedException {
        //Create all he objects
        SuperMarket mercadona;
        Buyer v;
        int numcli=10;
        CountDownLatch parar=new CountDownLatch(1);
        CountDownLatch finish=new CountDownLatch(numcli+4);
        Main main = new Main();
        mercadona=new SuperMarket(finish, numcli, 20, main.getButcher(), main.getCashier1(), main.getCashier2(), 
                main.getFishmonger(), main.getWaitingButhcer(),
                main.getWaitingEnter(), main.getWaitingFish(), main.getWaitingLine(), main.getShelves());
        main.setMercadona(mercadona);
        Writer writer=new Writer(mercadona);
        mercadona.setWriter(writer);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    main.setVisible(true);
            }
        });
        Server server = new Server(main, parar);
        try {
            parar.await(); //wait until the server program has found a port to bind the socket
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        mercadona.start();
        
    }
}
