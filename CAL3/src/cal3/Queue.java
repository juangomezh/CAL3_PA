package cal3;

// The class Queue manages the waiting queues (actually lists, but it allow us 
// to represent the content of the process queues of the monitors with push and
// pop of integers. Every time that the queue is modified, it content is printed
// in the JTextField passed in the contructor as a parameter.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextField;

public class Queue {
    private int[] content;
    private JTextField tf;
    private String name;
    private boolean stop=false;
    private int ptr;
    public boolean isEmpty()
    {
        return ptr==0;
    }

    public void setS(boolean c)
    {
        stop=c;
    }
    public Queue(int capacity, JTextField tf, String name){
        content = new int[capacity+1];
        ptr=0;
        this.tf=tf;
        this.name=name;
    }
    public synchronized void push(int n) throws InterruptedException{
        if(stop) wait();
        content[ptr]=n;
        ptr++;
        print();
    }
    public synchronized void pop(int n) throws InterruptedException{
        if(stop) wait();
        boolean flag=false;
            for (int i=0;i<ptr-1;i++) {
                if (n==content[i]) flag=true;
                if (flag) content[i]=content[i+1];
            }
           ptr--;
        print();
    }
    public synchronized int pop() throws InterruptedException
    {
        if(stop) wait();
        int num=content[0];
            for (int i=0;i<ptr-1;i++) {
                content[i]=content[i+1];
            }
           ptr--;
        print();
        return num;
    }
    public synchronized void emptyQueue()
    {
        for(int i=ptr;i>0; i--)
        {
            content[i]=0;
        }
    }
    public synchronized int first(){return content[0];}
    public synchronized int noOfItems(){return ptr;}
    public synchronized String ptrString(){
        String str="";
        for (int i=0;i<ptr;i++) str=str+"-"+content[i];
        return str;
    }
    public synchronized void print(){ 
        tf.setText(ptrString());
    }
    public synchronized void signal()
    {
        if(!stop)    notifyAll();
    }
}
