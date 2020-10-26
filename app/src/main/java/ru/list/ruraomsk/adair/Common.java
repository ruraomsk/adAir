package ru.list.ruraomsk.adair;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.list.ruraomsk.adair.DB.DB;
import ru.list.ruraomsk.adair.DB.UpdateDb;

public class Common {
    static public String HostMain;
    static public int PortMain;
    static public DB db;
    static public UpdateDb updateDb;
    static public Set<String> hosts;
    static public String host;
    static public ServiceConnection sconn;
    public static boolean bound=false;
    public static ConcurrentMap<String,String> values;
    public static Intent intent;
    public static Device device;
    public static ConcurrentMap<String,ViewController> fragments;
    private static Context ctx;

    static public void run(Context c){
        ctx=c;
        values=new ConcurrentHashMap<>();
        fragments=new ConcurrentHashMap<>();
        SharedPreferences sPref=ctx.getSharedPreferences("adAir",Context.MODE_PRIVATE);

        HostMain=sPref.getString("hostMain",ctx.getString(R.string.main_host));

        PortMain=sPref.getInt("portMain",Integer.parseInt(ctx.getString(R.string.main_port)));

        db=new DB(ctx);
        db.open();
//        db.clearAll();
        if (db.loadAll().isEmpty()){
            Log.d("adAirDebug", "DataTable is empty" );

            Thread second=new Thread(new Runnable(){
                @Override
                public void run() {
                    UpdateDb updateDb=new UpdateDb(db,HostMain,PortMain);
                    hosts=db.getHosts();
                }
            });
            second.start();
            try {
                second.join();
            } catch (InterruptedException e) {
                                    Log.d("adAirDebug", e.getMessage() );
            }
        }
        hosts=db.getHosts();
    }
    static public void stopDevice(){
        ctx.stopService(intent);
    }
    static public void RegitrationFragment(String name,ViewController ctx){
        Log.d("adAirDebug", "Добавили "+name );
        fragments.put(name,ctx);
    }
    static public void UnRegitrationFragment(String name){
        Log.d("adAirDebug", "Убрали "+name );
        fragments.remove(name,ctx);
    }
    static public void OneStepUI(){

        for (ViewController ctrl:fragments.values()) {

            ctrl.View();
        }
    }
    static public void ViewData(TextView tv,String code){
        if (values.containsKey(code)) {
            tv.setText(values.get(code));
        } else {
            Log.d("adAirDebug", "Нет "+code );
        }
    }

}
