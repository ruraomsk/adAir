package ru.list.ruraomsk.adair.slot;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static java.lang.Thread.sleep;

public class Device extends Service {
    private String login;
    private String password;
    private String host;
    private int port;
    private Slot slot=null;
    private boolean work=false;
    private DeviceBinder binder=new DeviceBinder();
    private ReadData second=null;

    public void setDevice(String host,int port,String login,String password){
        this.host=host;
        this.port=port;
        this.login=login;
        this.password=password;
    }
    public void connect(){
        try {
            slot=new Slot(host,port);
            if(!slot.isWork()) return;
            slot.run();
            slot.writeMessage("login="+login+" password="+password);
            while (slot.isWork()&&!slot.isMessage()){
                sleep(100);
            }
            if (slot.getMessage().contains("Ok")&&slot.isWork()) {
                work=true;
            }
        } catch (InterruptedException e) {
            Log.d("adAir",e.getMessage());
        }
    }
    public void run(){
        second=new ReadData(slot,this);
        second.run();
    }
    public void disconnect(){
        slot.writeMessage("exit");
        try {
            sleep(1000);
            second.interrupt();
            work=false;
        } catch (InterruptedException e) {
            Log.d("adAir",e.getMessage());
        }
    }
    public boolean isWork(){
        if (slot==null) return false;
        return slot.isWork();
    }

    @Override
    public void onDestroy() {
        disconnect();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    class DeviceBinder extends Binder{
        Device getService(){
            return Device.this;
        }
    }
}
