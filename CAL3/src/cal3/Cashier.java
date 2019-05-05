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
public class Cashier extends Thread{
    private int id;
    private SuperMarket ex;
    private long t0, t1, total=0;
    private CountDownLatch finish;
    public Cashier(CountDownLatch finish, int id, SuperMarket ex){
        this.finish=finish;
        this.id=id;
        this.ex = ex;
        start();
    }

    public void setEx(SuperMarket ex) {
        this.ex = ex;
    }
    public void run()
    {
        t0 = (new Date()).getTime();
        while(!ex.isFinished())
        {
            if(id==1) //check the id of the cashier, to select its position in the supermarket
            {
                try {
                    ex.cash1Att();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    ex.Cash2Att();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cashier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        t1= (new Date()).getTime();
        total+=t1-t0; //add the accumulated time in the supermarket
        System.out.println("Cashier "+id+" Finished");
        finish.countDown();
    }
}
