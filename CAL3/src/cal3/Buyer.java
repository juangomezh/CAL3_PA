package cal3;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buyer extends Thread {
    private int id;
    private SuperMarket ex;
    private long t0, t1, total;
    private CountDownLatch finish;
    public Buyer(CountDownLatch finish, int id, SuperMarket ex){
        this.finish=finish;
        this.id=id;
        this.ex=ex;
        this.start();
    }
    public void run(){
        try {
            try {
                ex.enter(id); //enter the supermatket
            } catch (InterruptedException ex) {
                Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
            }
            t0 = (new Date()).getTime(); //as it has entered, start to count the time it is inside
            ex.shop(id); //select shelves, fisher, butcher
            ex.pay(id); //select cahsier
        } catch (InterruptedException ex){Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
        }
        t1 = (new Date()).getTime();
        total=t1-t0;
        ex.totalTime(total);
        finish.countDown();
        try {
            ex.leave(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Buyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
