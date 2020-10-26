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
    private Thread thread=null;
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
        try {
            slot=new Slot(host,port);
            slot.start();
            sleep(1000);
            if(!slot.isWork()) {
                Log.d("adAirDebug","Slot не запустился");
                return;
            }
            slot.writeMessage("login="+login+" password="+password);
            while (slot.isWork()&&!slot.isMessage()){
                sleep(100);
            }
            String message=slot.getMessage();
            if (message==null) return;
            Log.d("adAirDebug","Получил:"+message+":");
            if (message.contains("OK")&&slot.isWork()) {
                Log.d("adAirDebug","Прошел верификацию");
                work=true;
            } else {
                Log.d("adAirDebug","Верификацию не прошел");
                return;
            }
        } catch (InterruptedException e) {
            Log.d("adAirDebug",e.getMessage());
        }
        second=new ReadData(slot,this);
        second.start();
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    //TODO
                } catch (InterruptedException e) {
                    Log.d("adAirDebug",e.getMessage());
                }

            }
        });
        thread.start();
        slot.writeMessage("#DBG.MODE:000");
    }
    public void disconnect(){
        if(slot==null) return;
        slot.writeMessage("exit");
        try {
            sleep(1000);
            second.interrupt();
            thread.interrupt();
            while(slot.isWork()){
                sleep(500);
            }
            work=false;
            slot=null;
            thread=null;
            second=null;
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
