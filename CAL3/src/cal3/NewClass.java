/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cal3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dcc
 */
public class NewClass extends Thread {
        private Socket connection;
        private DataOutputStream output;
        private DataInputStream  input;
        private SuperMarket mercadona;
        public NewClass(Socket connection, SuperMarket mercadona)
        {
            this.mercadona=mercadona;
            this.connection=connection;
            start();
        }
    public void run()
    {
            try {
                //Open input-output channels
                input = new DataInputStream(connection.getInputStream());  
                output  = new DataOutputStream(connection.getOutputStream()); 
                //Read message from the client
                String mensaje = input.readUTF();
                System.out.println("el mensaje ha sido leido: "+mensaje);
                //Interpret the messages read
                if(mensaje.equals("Stop Butcher"))
                {
                    mercadona.getButcherAtt().setS(true);
                    mercadona.getWaitingButcher().setS(true);
                }
                if(mensaje.equals("Stop FishMonger"))
                {
                    mercadona.getFishAtt().setS(true);
                    mercadona.getWaitingFish().setS(true);
                }
                if(mensaje.equals("Stop SuperMarket"))
                {
                    mercadona.getButcherAtt().setS(true);
                    mercadona.getWaitingButcher().setS(true);
                    mercadona.getFishAtt().setS(true);
                    mercadona.getWaitingFish().setS(true);
                    mercadona.getWaitingPay().setS(true);
                    mercadona.getWaitingSuperMarket().setS(true);
                    mercadona.getPayCash1().setS(true);
                    mercadona.getPayCash2().setS(true);
                    mercadona.getOnShelves().setS(true);
                }
                if(mensaje.equals("Resume Butcher"))
                {
                    mercadona.getButcherAtt().setS(false);
                    mercadona.getWaitingButcher().setS(false);
                    mercadona.getWaitingButcher().signal();
                    mercadona.getButcherAtt().signal();
                }
                if(mensaje.equals("Resume FishMonger"))
                {
                    mercadona.getFishAtt().setS(false);
                    mercadona.getWaitingFish().setS(false);
                    mercadona.getFishAtt().signal();
                    mercadona.getWaitingFish().signal();
                }
                if(mensaje.equals("Resume SuperMarket"))
                {
                    mercadona.getButcherAtt().setS(false);
                    mercadona.getWaitingButcher().setS(false);
                    mercadona.getFishAtt().setS(false);
                    mercadona.getWaitingFish().setS(false);
                    mercadona.getWaitingPay().setS(false);
                    mercadona.getWaitingSuperMarket().setS(false);
                    mercadona.getPayCash1().setS(false);
                    mercadona.getPayCash2().setS(false);
                    mercadona.getOnShelves().setS(false);
                    mercadona.getButcherAtt().signal();
                    mercadona.getWaitingButcher().signal();
                    mercadona.getFishAtt().signal();
                    mercadona.getWaitingFish().signal();
                    mercadona.getWaitingPay().signal();
                    mercadona.getWaitingSuperMarket().signal();
                    mercadona.getPayCash1().signal();
                    mercadona.getPayCash2().signal();
                    mercadona.getOnShelves().signal();
                }
                //Close connection
                connection.close();       
            } catch (IOException ex) {
            } 
            
    }
}
