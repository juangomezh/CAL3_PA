/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cal3;

/**
 *
 * @author jgome
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextField;

public class NQueue {
    private int[] content;
    private boolean stop=false;
    private int ptr;
    private String escribir;
    public boolean isEmpty()
    {
        return ptr==0;
    }

    public void setS(boolean c)
    {
        stop=c;
    }
    public NQueue(int capacity){
        content = new int[capacity+1];
        ptr=0;

    }
    public synchronized int push(int n) throws InterruptedException{
        if(stop) wait();
        content[ptr]=n;
        ptr++;
        return n;
    }
    public synchronized void pop(int n) throws InterruptedException{
        if(stop) wait();
        boolean flag=false;
            for (int i=0;i<ptr-1;i++) {
                if (n==content[i]) flag=true;
                if (flag) content[i]=content[i+1];
            }
           ptr--;
    }
    public synchronized int pop() throws InterruptedException
    {
        if(stop) wait();
        int num=content[0];
            for (int i=0;i<ptr-1;i++) {
                content[i]=content[i+1];
            }
           ptr--;
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
    public synchronized void signal()
    {
        if(!stop)    notifyAll();
    }
}

