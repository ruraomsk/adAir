package ru.list.ruraomsk.adair;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;

import ru.list.ruraomsk.adair.DB.DB;
import ru.list.ruraomsk.adair.DB.UpdateDb;

public class Common {
    static public String HostMain;
    static public int PortMain;
    static public DB db;
    static public UpdateDb updateDb;
    static public Set<String> hosts;
    static public String host;
    public static void run(Context ctx){
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
}
