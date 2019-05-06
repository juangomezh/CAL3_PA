/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cal3;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgome
 */
public class Fisher extends Thread{
    private int id;
    private SuperMarket ex;
    private CountDownLatch finish;
    public Fisher(CountDownLatch finish, int id, SuperMarket ex){
        this.finish=finish;
        this.id=id;
        this.ex = ex;
        start();
    }
    public void run()
    {
        
        while(!ex.isFinished())
        {
            try {
                ex.fisherAtt(); //attend the clients
            } catch (InterruptedException ex) {
                Logger.getLogger(Fisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Fisher Finished");
        finish.countDown();
    }
    
}
