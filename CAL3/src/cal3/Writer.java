/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cal3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgome
 */
public class Writer extends Thread{
    //Supermarket
    private SuperMarket mercadona;
    //the unlimited buffer, and the values appended to it
    private String buffer, valor;
    //to check if it has been called to write or not
    private boolean escrito=false;
    //file and tools to write
    private File archivo;
    private FileWriter fr;
    private PrintWriter pr;
    public Writer(SuperMarket goya) throws IOException
    {
        this.mercadona=goya;
        buffer="";
        valor="";
        archivo = new File("src/Archivo/evolucionSupermercado.txt");
        try {
            fr=new FileWriter(archivo);
        } catch (FileNotFoundException ex) {
            System.out.println("the file wasn't found");
        }
        pr = new PrintWriter(fr);
        start();
    }
    /**
     * method to appedn the values needed
     * @param valor 
     */
    public synchronized void append(String valor)
    {
        this.valor+=valor+"\n";
    }
    /**
     * method of the thread to wait until another thread has written, thed append the value to the buffer
     * @throws InterruptedException 
     */
    public synchronized void write() throws InterruptedException
    {
        if(escrito)
        {
        buffer+=valor;
        valor=""; //restart the value to append
        escrito=false;
        }
        else
        {
            wait(100);
        }
    }
    /**
     * Last method to write the final things in the buffer
     */
    public synchronized void writelast()
    {
        if(escrito)
        {
        buffer+="\n"+valor;
        escrito=false;
        }
    }
    public void run()
    {
        //perform the writting in the file until the supermarket has been closed
        while(!mercadona.isClosed())
        {
            try {
                write();
            } catch (InterruptedException ex) {
                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        writelast();
        pr.println(buffer);
        System.out.println("The text has been printed on the log");
        pr.close();
    }

    public SuperMarket getMercadona() {
        return mercadona;
    }

    public void setMercadona(SuperMarket mercadona) {
        this.mercadona = mercadona;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isEscrito() {
        return escrito;
    }

    public void setEscrito(boolean escrito) {
        this.escrito = escrito;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public FileWriter getFr() {
        return fr;
    }

    public void setFr(FileWriter fr) {
        this.fr = fr;
    }

    public PrintWriter getPr() {
        return pr;
    }

    public void setPr(PrintWriter pr) {
        this.pr = pr;
    }
    
}
