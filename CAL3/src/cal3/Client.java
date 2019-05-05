package cal3;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.*;
public class Client {
    private static String IP;
    private static int Puerto;
    private BufferedReader entrada;
    private Socket client;
    private boolean connected=false;
    private DataInputStream  input;
    private DataOutputStream output;
    public Client() throws IOException {
        entrada=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduzca la IP del sistema al que conectarse: ");
        IP=entrada.readLine();
        System.out.print("Introduzca el Puerto del sistema al que conectarse: ");//create a socket bound to the port selected by the user
        try
        {
        Puerto=Integer.valueOf(entrada.readLine());
        while(Puerto<=1023)  //if the customer has chosen a privilege port, try again
        {
            System.out.println("try with a non privileged port (>=1024): ");
            Puerto=Integer.valueOf(entrada.readLine());
        }
        while(Puerto>49151)
        {
            System.out.println("try with a port in the TCP range: ");
            Puerto=Integer.valueOf(entrada.readLine());
        }
        }catch(java.lang.NumberFormatException e)
        {
            System.out.println("The port must be a number");
        }
        
    }
    
    public synchronized void stop(String name) throws IOException
    {
        do
        {
        try
        {
        client = new Socket(IP,Puerto); 
        input = new DataInputStream(client.getInputStream());  
        output = new DataOutputStream(client.getOutputStream());
        output.writeUTF("Stop "+name);
        }
        catch(java.net.SocketException e) //if the bind process fails, try again with another port/ip
        {
            System.out.println("There was an error in connecting the socket, please try again: ");
            System.out.print("Introduzca la IP del sistema al que conectarse: ");
            IP=entrada.readLine();
            System.out.print("Introduzca el Puerto del sistema al que conectarse: ");
            try
            {
            Puerto=Integer.valueOf(entrada.readLine());
            }catch(java.lang.NumberFormatException ex)
            {
                System.out.println("The port must be a number");
            }
            System.out.println();
            connected=false;
        }
        catch(java.net.UnknownHostException e)
        {
            System.out.println("the ip format was incorrect");
            System.out.print("Introduzca la IP del sistema al que conectarse: ");
            IP=entrada.readLine();
            System.out.print("Introduzca el Puerto del sistema al que conectarse: ");
            try
            {
            Puerto=Integer.valueOf(entrada.readLine());
            }catch(java.lang.NumberFormatException ex)
            {
                System.out.println("The port must be a number");
            }
            System.out.println();
            connected=false;
        }
        }while(!connected);
        client.close();
    }
    public synchronized void resume(String name) throws IOException
    {
        do
        {
        try
        {
        client = new Socket(IP,Puerto); 
        input = new DataInputStream(client.getInputStream());  
        output = new DataOutputStream(client.getOutputStream());
        output.writeUTF("Resume "+name);
        }
        catch(java.net.SocketException e) //if the bind process fails, try again with another port/ip
        {
            System.out.println("There was an error in connecting the socket, please try again: ");
            System.out.print("Introduzca la IP del sistema al que conectarse: ");
            IP=entrada.readLine();
            System.out.print("Introduzca el Puerto del sistema al que conectarse: ");
            try
            {
            Puerto=Integer.valueOf(entrada.readLine());
            }catch(java.lang.NumberFormatException ex)
            {
                System.out.println("The port must be a number");
            }
            System.out.println();
            connected=false;
        }
        catch(java.net.UnknownHostException e)
        {
            System.out.println("the ip format was incorrect");
            System.out.print("Introduzca la IP del sistema al que conectarse: ");
            IP=entrada.readLine();
            System.out.print("Introduzca el Puerto del sistema al que conectarse: ");
            try
            {
            Puerto=Integer.valueOf(entrada.readLine());
            }catch(java.lang.NumberFormatException ex)
            {
                System.out.println("The port must be a number");
            }
            System.out.println();
            connected=false;
        }
        }while(!connected);
        client.close();
    }
}
