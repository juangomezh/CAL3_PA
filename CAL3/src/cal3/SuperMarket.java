package cal3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

public class SuperMarket extends Thread{
    //average time of the work of the butcher and fisher
    private float averagetimebutcher, averagetimefisher;
    //accumulated services of the butcher and fisher
    private int butcherservices, fisherservices;
    //the class that implements the thread that will write in the log
    private Writer writer;
    // capacity, numclients to enter the supermarket, and the total accumulated time of each client 
    private int capacity, numcli, totaltime;
    // acverage time of the clients inside the supermarket
    private float averagecli;
    private Butcher butcher;
    private Fisher fisher;
    private Cashier c1, c2;
    // countdown latch to wait all the threads to finish, and compute the values.
    private CountDownLatch finish;
    // boolean to check the state of the supermarket, or if the button closed have been pressed
    private boolean Finished=false, Closed=false, Terminated=false;
    // queues that perform the internal functionality without printing in the jtextfields
    // in order: people waiting for cashiers 1 and 2 , butcher and fisher
    private NQueue WaitingC1, WaitingC2, WaitingB, WaitingF;
    // queues that perform the printing in the jtextfields, they have the same values as the previous
    // Queue of the people waiting in the SP
    private Queue WaitingSuperMarket;
    // people on the shelves
    private Queue OnShelves;
    // poeple waiing to the butcher, and a queue of individual buyers that are in the butcher
    private Queue WaitingButcher, ButcherAtt;
    // people waiting to the fisher, and another with max capacity 1, for the buyer being attended
    private Queue WaitingFish, FishAtt;
    // people waiting in te line to pay, and attended by the cashiers
    private Queue WaitingPay, PayCash1, PayCash2; 
    // semaphores for awaiting the threads until the fisher, butcher or cashers have finished their job (waiting conditions)
    private Semaphore InsideS, FisherS, ButcherS, Cashier1S, Cashier2S;
    // locks for awaiting the threads
    private Lock Fish, Butcher, WaitCh2, WaitCh1;
    private Condition FishL, ButcherL, Cash1L, Cash2L;
    public SuperMarket(CountDownLatch parar, int numcli, int capacity, JTextField Butcher, JTextField Cashier1, JTextField Cashier2, 
                JTextField Fishmonger, JTextField WaitingButhcer,
                JTextField WaitingEnter, JTextField WaitingFish, JTextField WaitingLine, JTextField WShelves)
    {
        this.numcli=numcli;
        finish= parar;
        this.capacity=capacity;
        //semaphore for the entering people in the supermarket, with the maximum capacity
        InsideS=new Semaphore(capacity,true);
        //semaphores simulating waiting conditions -> capacity=0
        ButcherS=new Semaphore(0,true);
        FisherS=new Semaphore(0,true);
        Cashier1S=new Semaphore(0,true);
        Cashier2S=new Semaphore(0,true);
        //locks and conditions
        Fish=new ReentrantLock();
        FishL=Fish.newCondition();
        this.Butcher=new ReentrantLock();
        ButcherL=this.Butcher.newCondition();
        WaitCh2=new ReentrantLock();
        WaitCh1=new ReentrantLock();
        Cash1L=WaitCh1.newCondition();
        Cash2L=WaitCh2.newCondition();
        /**Queues Creations for the internal functionality*/
        WaitingC1=new NQueue(numcli);
        WaitingC2=new NQueue(numcli);
        WaitingB=new NQueue(numcli);
        WaitingF=new NQueue(numcli);
        // queues for printing the textfield//
        WaitingSuperMarket=new Queue(numcli, WaitingEnter, "People waiting to enter the supermarket: ");
        OnShelves=new Queue(capacity, WShelves, "People on the shelves");
        WaitingButcher=new Queue(capacity, WaitingButhcer, "People waiting for the Butcher: ");
        ButcherAtt=new Queue(1, Butcher,"The buthcer is attending: ");
        this.WaitingFish=new Queue(capacity, WaitingFish, "Waiting Fishmonger: ");
        FishAtt=new Queue(1, Fishmonger, "The Fishmonger is attending: ");
        WaitingPay=new Queue(capacity, WaitingLine, "People waiting for pay: ");
        PayCash1=new Queue(1, Cashier1,"the cahsier 1 is attending: ");
        PayCash2=new Queue(1, Cashier2, "the cashier 2 is attending: ");
       
    }
    /** 
     * checks all the buyers have finished
     * @return 
     */
    public boolean isFinished() {
        return Finished;
    }  
    /**Clients*/
    /**
     * method to enter the supermarket, checks the semaphore to enter
     * @param v
     * @throws InterruptedException 
     */
    public void enter(int v) throws InterruptedException{
        WaitingSuperMarket.push(v);
        try{ InsideS.acquire();} catch(InterruptedException e){}
    }
    /**
     * method used by the buyers when they finish, checks if there are more buyers inside
     */
    public synchronized void finish()
    {
        if(!Terminated) // if the button closed hasn' been pressed, no problem
        {
            if(finish.getCount()==4) //all the buyers have finished, only remains the fisher, butcher, cashiers
            {
                Finished=true;
            }
        }
        // if the button terminated have been pressed, check if all the queues are empty
        else if(WaitingC1.isEmpty() && WaitingC2.isEmpty() && WaitingB.isEmpty() && WaitingF.isEmpty() &&
                WaitingSuperMarket.isEmpty() && OnShelves.isEmpty() && WaitingButcher.isEmpty() &&
                ButcherAtt.isEmpty() && WaitingFish.isEmpty() && FishAtt.isEmpty() && WaitingPay.isEmpty()
                && PayCash1.isEmpty() && PayCash2.isEmpty())
        {    //if all are empty, as not all the buyers have performed the countdown, do it until it should accomplish the correct value
            while(finish.getCount()>4) finish.countDown();
            Finished=true;
        }
    }
    /**
     * release the permits so more threads can enter
     */
    public void leave(int v) throws InterruptedException{
        InsideS.release();
        finish();
    }
    /**
     *     when inside the supermarket, choose randomly, the fisher, butcher or shelves to go
     */
    public void shop(int v) throws InterruptedException
    {
        int rand=(int)(3*Math.random());
        if(rand==0)
        {
            waitButcher(v);
            tryButcher(v);
        }
        else if(rand==1)
        {
            waitFisher(v);
            tryFisher(v);
        }
        else
        {
            shelves(v);
        }
    }
    /**
     * method to wait in the shelves
     */
    public void shelves(int v) throws InterruptedException
    {
        WaitingSuperMarket.pop(v);
        writer.append("The client "+v+" enters SuperMarket");
        writer.setEscrito(true);
        OnShelves.push(v);
        writer.append("The client "+v+" goes to the Shelves");
        writer.setEscrito(true);
        Thread.sleep((int)(1000+(10000*Math.random())));
        OnShelves.pop(v);
    }
    /**
     * method to wait to the butcher
     */
    public void waitButcher(int v) throws InterruptedException
    {
        WaitingSuperMarket.pop(v);
        writer.append("The client "+v+" enters SuperMarket");
        writer.setEscrito(true);
        WaitingButcher.push(v);
        writer.append("The client "+v+" waits at the butcher");
        writer.setEscrito(true);
        Butcher.lock();
            if(WaitingB.isEmpty()) //if the queue was empty, signal the butcher
            {
                WaitingB.push(v);
                ButcherL.signal();
            }
            else WaitingB.push(v); //otherwise just enter the queue
        Butcher.unlock();
    }
    /**
     * method for the buther to attend the clients
     * @throws InterruptedException 
     */
    public void butcherAtt() throws InterruptedException
    {
        Butcher.lock();
        if(!Finished) 
            {
            if(WaitingB.isEmpty()) //if the queue is empyt, the thread must wait
            {
                ButcherL.await(1000, TimeUnit.MILLISECONDS);
            }
            else
            {
                butcherservices++; //add 1 to the number of services
                int client=WaitingB.pop(); // take it from the queues
                WaitingButcher.pop(client);
                writer.append("The client "+client+" is being attended by butcher");
                writer.setEscrito(true);
                ButcherAtt.push(client);
                Thread.sleep((int)(1500+1000*Math.random())); //tie to serve
                ButcherAtt.pop(client);
                ButcherS.release(); //release the buyer, as it as alreadey serve it
            }
            }
        Butcher.unlock();
    }
    /**
     * wait until the butcher has finished
     * @param v
     * @throws InterruptedException 
     */
    public void tryButcher(int v) throws InterruptedException
    {
        ButcherS.acquire();
    }
    /**
     * wait in the line for the fisher
     * @param v
     * @throws InterruptedException 
     */
    public void waitFisher(int v) throws InterruptedException
    {
        WaitingSuperMarket.pop(v);
        writer.append("The client "+v+" enters SuperMarket");
        writer.setEscrito(true);
        WaitingFish.push(v);
        writer.append("The client "+v+" waits at the fisher");
        writer.setEscrito(true);
        Fish.lock();
            if(WaitingF.isEmpty()) //if the queue is empty, push and signal
            {
                WaitingF.push(v);
                FishL.signal();
            }
            else WaitingF.push(v);
        Fish.unlock();
    }
    /**
     * wait until the fished has finished its job
     * @param v
     * @throws InterruptedException 
     */
    public void tryFisher(int v) throws InterruptedException
    {
        FisherS.acquire();
    }
    /**
     * method for the fisher to attend the buyers
     * @throws InterruptedException 
     */
    public void fisherAtt() throws InterruptedException
    {
        Fish.lock();
        if(!Finished)
            {
            if(WaitingF.isEmpty()) //wait until the queue isn't empty
            {
                FishL.await(1000, TimeUnit.MILLISECONDS);
            }
            else
            {
                fisherservices++;
                int client=WaitingF.pop(); //take the buyers from the queues
                WaitingFish.pop(client);
                writer.append("The client "+client+" is being attended by fisher");
                writer.setEscrito(true);
                FishAtt.push(client);
                Thread.sleep((int)(2000+1000*Math.random())); //time to serve
                FishAtt.pop(client);
                FisherS.release(); //release the buyer
            }
            }
        Fish.unlock();
    }
    /**
     * wait until the cashier has finished
     * @throws InterruptedException 
     */
    public void tryCashier1() throws InterruptedException
    {
        Cashier1S.acquire();
    }
    /**
     * wait until the cashier2 has finished
     * @throws InterruptedException 
     */
    public void tryCashier2() throws InterruptedException
    {
        Cashier2S.acquire();
    }
    /**
     * method performed by the clients once they have been served by the butcher/fisher/shelves
     * @param v
     * @throws InterruptedException 
     */
    public void pay(int v) throws InterruptedException
    {
        int line=(int)(2*Math.random()); //chose randomly the cashier 
        if(line==0)
        {
            waitPayC1(v);
            tryCashier1();
        }
        else    
        {
            waitPayC2(v);
            tryCashier2();
        }
    }
    //the methods for waiting for the cashiers are divided in two dedicated queues for each one, although it is printed as only one
    /**
     * Wait in the line for being attended
     * @param v
     * @throws InterruptedException 
     */
    public void waitPayC1(int v) throws InterruptedException
    {
        WaitingPay.push(v);
        writer.append("The client "+v+" is waits at the waiting line");
        writer.setEscrito(true);
        WaitCh1.lock();
            if(WaitingC1.isEmpty()) //if its the cashier1, internally wait for a queue for the cashier 1, signal if its empty
                {
                    WaitingC1.push(v);
                    Cash1L.signal();
                }
            else
                {
                    WaitingC1.push(v);
                }
            WaitCh1.unlock();
    }
    /**
     * wait in the line for being attended
     * @param v
     * @throws InterruptedException 
     */
    public void waitPayC2(int v) throws InterruptedException
    {
        WaitingPay.push(v);
        writer.append("The client "+v+" is waits at the waiting line");
        writer.setEscrito(true);
        WaitCh1.lock();
            if(WaitingC2.isEmpty()) //if its the cashier2, waits in its individually queue, if its empty, signal
                {
                    WaitingC2.push(v);
                    Cash1L.signal();
                }
            else
                {
                    WaitingC2.push(v);
                }
            WaitCh1.unlock();
    }
    /**
     * method for the cashier 1 to atted
     * @throws InterruptedException 
     */
    public void cash1Att() throws InterruptedException
    {
        WaitCh1.lock();
        if(!Finished)
            {
            if(WaitingC1.isEmpty()) //if its individual queue is emty, wait until its not
            {
                Cash1L.await(1000, TimeUnit.MILLISECONDS);
            }
            else
            {
                int client;
                client=WaitingC1.pop(); //perfrom the service
                writer.append("The client "+client+" is being attended by the cashier 1");
                writer.setEscrito(true);
                WaitingPay.pop(client);
                PayCash1.push(client);
                Thread.sleep((int)(3000+2000*Math.random())); //time to serve
                PayCash1.pop(client);
                Cashier1S.release();
            }
            }
        WaitCh1.unlock();
    }
    /**
     * method to the cashier 2
     * @throws InterruptedException 
     */
    public void Cash2Att() throws InterruptedException
    {
        WaitCh2.lock();
        if(!Finished)
            {
            if(WaitingC2.isEmpty()) //if its dedicated queue is empty, wait
            {
                Cash2L.await(1000, TimeUnit.MILLISECONDS);
            }
            else
            {
                int client;
                client=WaitingC2.pop();
                writer.append("The client "+client+" is being attended by the cashier 2");
                writer.setEscrito(true);
                WaitingPay.pop(client);
                PayCash2.push(client);
                Thread.sleep((int)(3000+2000*Math.random())); //time to serve
                PayCash2.pop(client);
                Cashier2S.release();
            }
            }
        WaitCh2.unlock();
    }
    /**
     * add the accumulated time of each client
     * @param t 
     */
    public void totalTime(long t)
    {
        totaltime+=t;
    }
    /**
     * run the thread
     */
    public void run()
    {
        Buyer v;
        butcher=new Butcher(finish, 0, this);
        fisher=new Fisher(finish, 0, this);
        c1=new Cashier(finish, 1, this);
        c2=new Cashier(finish, 2, this);
        int i=1; //if the program is not terminated, or the number of clients is not reached, continue creating them
        while(!Terminated && i<=numcli)
        {
            try {
                Thread.sleep((int)(200+(800*Math.random()))); //the time between the creation of each client=time to arrive
                v=new Buyer(finish, i, this);
            } catch (InterruptedException ex) {
                Logger.getLogger(SuperMarket.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
        }
        try {
            finish.await(); //wait until all the threads have finish, to compute the values
            System.out.println("The SP is closed, no more clients inside");
            averagecli=totaltime/numcli;
            //call the writer to add those values to the buffer
            writer.append("The number of accumulated services in the butcher are: "+this.butcherservices);
            writer.setEscrito(true);
            writer.append("The average time of work of the butcher is: "+this.averagetimebutcher/1000+" seconds");
            writer.setEscrito(true);
            writer.append("The number of accumulated services in the fisher are: "+this.fisherservices);
            writer.setEscrito(true);
            writer.append("The average time of work of the fisher is: "+this.averagetimefisher/1000+" seconds");
            writer.setEscrito(true);
            writer.append("The client average time of the clients inside the SP: "+averagecli/1000+" seconds");
            writer.setEscrito(true);
            writer.append("The number of clients that have passed through: "+numcli);
            writer.setEscrito(true);
            Closed=true;
        } catch (InterruptedException ex) {
            Logger.getLogger(SuperMarket.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    //getters and setters
    public void timefisher(long time)
    {
        this.averagetimefisher=time/numcli;
    }
    public void timebutcher(long time)
    {
        this.averagetimebutcher=time/numcli;
    }

    public void setButcherservices(int butcherservices) {
        this.butcherservices = butcherservices;
    }

    public void setFisherservices(int fisherservices) {
        this.fisherservices = fisherservices;
    }

    public void setFinished(boolean Finished) {
        this.Finished = Finished;
    }

    public void setTerminated(boolean Terminated) {
        this.Terminated = Terminated;
    }
    
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumcli() {
        return numcli;
    }

    public void setNumcli(int numcli) {
        this.numcli = numcli;
    }

    public float getAveragecli() {
        return averagecli;
    }

    public void setAveragecli(float averagecli) {
        this.averagecli = averagecli;
    }

    public Butcher getButcher() {
        return butcher;
    }

    public void setButcher(Butcher butcher) {
        this.butcher = butcher;
    }

    public Fisher getFisher() {
        return fisher;
    }

    public void setFisher(Fisher fisher) {
        this.fisher = fisher;
    }

    public Cashier getC1() {
        return c1;
    }

    public void setC1(Cashier c1) {
        this.c1 = c1;
    }

    public Cashier getC2() {
        return c2;
    }

    public void setC2(Cashier c2) {
        this.c2 = c2;
    }

    public CountDownLatch getFinish() {
        return finish;
    }

    public void setFinish(CountDownLatch finish) {
        this.finish = finish;
    }

    public Queue getWaitingSuperMarket() {
        return WaitingSuperMarket;
    }

    public void setWaitingSuperMarket(Queue WaitingSuperMarket) {
        this.WaitingSuperMarket = WaitingSuperMarket;
    }

    public Queue getWaitingButcher() {
        return WaitingButcher;
    }

    public void setWaitingButcher(Queue WaitingButcher) {
        this.WaitingButcher = WaitingButcher;
    }

    public Queue getButcherAtt() {
        return ButcherAtt;
    }

    public void setButcherAtt(Queue ButcherAtt) {
        this.ButcherAtt = ButcherAtt;
    }

    public Queue getWaitingFish() {
        return WaitingFish;
    }

    public void setWaitingFish(Queue WaitingFish) {
        this.WaitingFish = WaitingFish;
    }

    public Queue getFishAtt() {
        return FishAtt;
    }

    public Queue getOnShelves() {
        return OnShelves;
    }

    public void setOnShelves(Queue OnShelves) {
        this.OnShelves = OnShelves;
    }



    public Lock getWaitCh1() {
        return WaitCh1;
    }

    public void setWaitCh1(Lock WaitCh1) {
        this.WaitCh1 = WaitCh1;
    }

    public void setFishAtt(Queue FishAtt) {
        this.FishAtt = FishAtt;
    }

    public Queue getWaitingPay() {
        return WaitingPay;
    }

    public void setWaitingPay(Queue WaitingPay) {
        this.WaitingPay = WaitingPay;
    }

    public Queue getPayCash1() {
        return PayCash1;
    }

    public void setPayCash1(Queue PayCash1) {
        this.PayCash1 = PayCash1;
    }

    public Queue getPayCash2() {
        return PayCash2;
    }

    public void setPayCash2(Queue PayCash2) {
        this.PayCash2 = PayCash2;
    }

    public Semaphore getInsideS() {
        return InsideS;
    }

    public void setInsideS(Semaphore InsideS) {
        this.InsideS = InsideS;
    }

    public Semaphore getFisherS() {
        return FisherS;
    }

    public void setFisherS(Semaphore FisherS) {
        this.FisherS = FisherS;
    }

    public Semaphore getButcherS() {
        return ButcherS;
    }

    public void setButcherS(Semaphore ButcherS) {
        this.ButcherS = ButcherS;
    }

    public Semaphore getCashier1S() {
        return Cashier1S;
    }

    public void setCashier1S(Semaphore Cashier1S) {
        this.Cashier1S = Cashier1S;
    }

    public Semaphore getCashier2S() {
        return Cashier2S;
    }

    public void setCashier2S(Semaphore Cashier2S) {
        this.Cashier2S = Cashier2S;
    }

    public Lock getFish() {
        return Fish;
    }

    public void setFish(Lock Fish) {
        this.Fish = Fish;
    }


    public void setButcher(Lock Butcher) {
        this.Butcher = Butcher;
    }

    public Lock getWaitCh() {
        return WaitCh2;
    }

    public void setWaitCh(Lock WaitCh) {
        this.WaitCh2 = WaitCh;
    }

    public Condition getFishL() {
        return FishL;
    }

    public void setFishL(Condition FishL) {
        this.FishL = FishL;
    }

    public Condition getButcherL() {
        return ButcherL;
    }

    public void setButcherL(Condition ButcherL) {
        this.ButcherL = ButcherL;
    }

    public Condition getCash1L() {
        return Cash1L;
    }

    public void setCash1L(Condition Cash1L) {
        this.Cash1L = Cash1L;
    }

    public Condition getCash2L() {
        return Cash2L;
    }

    public void setCash2L(Condition Cash2L) {
        this.Cash2L = Cash2L;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public boolean isClosed() {
        return Closed;
    }

    public void setClosed(boolean Closed) {
        this.Closed = Closed;
    }



    
}
     