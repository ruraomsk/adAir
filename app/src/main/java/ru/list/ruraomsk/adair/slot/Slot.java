package ru.list.ruraomsk.adair.slot;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Slot extends Thread {
    private String address;
    private int port;
    private Socket socket=null;
    private boolean work;
    private PrintWriter bufferOut=null;
    private BufferedReader bufferIn=null;
    private ConcurrentLinkedQueue<String> toWrite;
    private ConcurrentLinkedQueue<String> toRead;
    private String readMessage() throws IOException, InterruptedException {
        if(!work) return null;
        while (!bufferIn.ready()) sleep(100);
        return bufferIn.readLine();
    }
    private void sendMessage(String message){
        if(!work) return;
        bufferOut.println(message);
        bufferOut.flush();
    }
    private void close(){
        if(socket!=null){
            try {
                socket.close();
                if (bufferOut!=null){
                    bufferOut.close();
                }
                if (bufferIn!=null){
                    bufferIn.close();
                }
            } catch (IOException e) {
                Log.d("adAir",e.getMessage());
            }
        }

    }
    public Slot(String address,int port){
        try {
        this.address=address;
        this.port=port;
        socket=new Socket();
        socket.connect(new InetSocketAddress(InetAddress.getByName(address),port),1000);
            socket.setKeepAlive(true);
        bufferIn=new BufferedReader((new InputStreamReader(socket.getInputStream())));
        bufferOut=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),64*1024));
        work=true;
        } catch (IOException e) {
            close();
            Log.d("adAir",e.getMessage());
        }
    }
    @Override
    public void run(){
        try {
            while(work){
                if(toWrite.isEmpty()){
                    sleep(100);
                    continue;
                }
                sendMessage(toWrite.poll());
                String message=readMessage();
                if (message==null) break;
                toRead.add(message);
            }
        } catch (IOException | InterruptedException e) {
            Log.d("adAir",e.getMessage());
        }
        close();
    }
    public boolean isWork (){
        return work;
    }
    public void writeMessage(String message){
        toWrite.add(message);
    }
    public String getMessage(){
        if(toRead.isEmpty()) return null;
        return toRead.poll();
    }
    public boolean isMessage(){
        return toRead.isEmpty();
    }
}
