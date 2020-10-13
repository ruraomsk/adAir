package ru.list.ruraomsk.adair.slot;

import android.util.Log;

public class Device extends Thread{
    private String login;
    private String password;
    private String host;
    private int port;
    private Slot slot=null;
    private boolean work=false;

    public Device(String host,int port,String login,String password){
            try {
                this.host=host;
                this.port=port;
                this.login=login;
                this.password=password;

                slot=new Slot(host,port);
                if(!slot.isWork()) return;
                slot.writeMessage("login="+login+" password="+password);
                while (slot.isWork()&&!slot.isMessage()){
                sleep(100);
                }
                if (slot.getMessage().contains("Ok")) {
                    work=true;
                }
            } catch (InterruptedException e) {
                Log.d("adAir",e.getMessage());
            }

    }
    @Override
    public void run() {

    }

    public boolean isWork(){
        if (slot==null) return false;
        return slot.isWork();
    }
}
