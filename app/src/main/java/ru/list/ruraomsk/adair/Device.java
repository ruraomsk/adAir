package ru.list.ruraomsk.adair;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import ru.list.ruraomsk.adair.slot.ReadData;
import ru.list.ruraomsk.adair.slot.Slot;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("adAirDebug", "OnStartCommand Device" );

        return super.onStartCommand(intent, flags, startId);
    }

    public void connect(){
        if(slot!=null) return;
        Thread sec=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    slot=new Slot(host,port);
                    slot.start();
                    if(!slot.isWork()) return;
                    slot.writeMessage("login="+login+" password="+password);
                    while (slot.isWork()&&!slot.isMessage()){
                        sleep(100);
                    }
                    if (slot.getMessage().contains("OK")&&slot.isWork()) {
                        Log.d("adAirDebug","Прошел верификацию");
                        work=true;
                    } else return;
                    while (slot.isWork()){
                        slot.writeMessage("message");
                        while (slot.isWork()&&!slot.isMessage()){
                            sleep(100);
                        }
                        Log.d("adAirDebug",slot.getMessage());
                        sleep(2000);
                    }
                } catch (InterruptedException e) {
                    Log.d("adAirDebug",e.getMessage());
                }
            }
        });
        sec.start();
    }
    public void run(){
        second=new ReadData(slot,this);
        second.start();
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
        return slot.isWork()&&work;
    }

    @Override
    public void onDestroy() {
        disconnect();
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("adAirDebug", "OnCrete Device" );
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("adAirDebug", "OnBind Device" );
        return binder;
    }

    public class DeviceBinder extends Binder{
        public Device getService(){
            return Device.this;
        }
    }
}
