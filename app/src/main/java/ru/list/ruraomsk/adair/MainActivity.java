package ru.list.ruraomsk.adair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int REQUEST_CODE_DEVICES=1;
    final int REQUEST_CODE_SETTING=2;
    private TextView tvDeviceName;

    private Button btnTraffic;
    private Button btnTech;
    private Button btnHard;
    private Button btnBind;


    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Common.run(this);
        Common.intent=new Intent(this, Device.class);
        ComponentName name=startService(new Intent(this, Device.class));
        if(name!=null) Log.d("adAirDebug", name.getPackageName()+""+name.getClassName() );

        Common.sconn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("adAirDebug", "onServerConnected" );
                Common.device=((Device.DeviceBinder) service).getService();
                Common.bound=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Common.bound=false;
            }
        };
        if(!bindService(Common.intent,Common.sconn,0)){
            Log.d("adAirDebug", "not binding" );

        };

        tvDeviceName=findViewById(R.id.device_name);
        btnTraffic=findViewById(R.id.cmd_traffic);
        btnTraffic.setOnClickListener(this);
        btnTech=findViewById(R.id.cmd_tech);
        btnTech.setOnClickListener(this);
        btnHard=findViewById(R.id.cmd_hard);
        btnHard.setOnClickListener(this);
        btnBind=findViewById(R.id.cmd_bind);
        btnBind.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cmd_traffic:
                startActivity(new Intent(this,Traffic.class));
                break;
            case R.id.cmd_tech:
                startActivity(new Intent(this,Tech.class));
                break;
            case R.id.cmd_hard:
                startActivity(new Intent(this,Hard.class));
                break;
            case R.id.cmd_bind:
                startActivity(new Intent(this,Bind.class));
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_update:
                //Обновляем БД устройств
                Common.db.clearAll();
                Common.run(this);
                break;
            case R.id.menu_choice:
                //выбираем устройство
                startActivityForResult(new Intent(this,ChoiceDevice.class),REQUEST_CODE_DEVICES);
                break;
            case R.id.menu_option:
                //выбираем устройство
                startActivityForResult(new Intent(this,Setting.class),REQUEST_CODE_SETTING);
                break;
            case R.id.menu_open:
                //Подключаем устройство
                if(!Common.bound){
                    Toast.makeText(this, "Сервис не запущен", Toast.LENGTH_LONG).show();
                    break;
                }
                Common.device.setDevice(Common.host,Common.db.getPort(Common.host),Common.db.getLogin(Common.host),Common.db.getPassword(Common.host));
                Common.device.connect();
                if(Common.device.isWork())  Toast.makeText(this, "Устанавлено соединение", Toast.LENGTH_LONG).show();
                else Toast.makeText(this, "Соединение не установлено", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_close:
                //Отключаем устройство
                if(!Common.bound){
                    Toast.makeText(this, "Сервис не запущен", Toast.LENGTH_LONG).show();
                    break;
                }
                Common.device.disconnect();
                Toast.makeText(this, "Отключаем соединение", Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("adAirDebug", "requestCode = " + requestCode + ", resultCode = " + resultCode);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_DEVICES:
                    Common.host=data.getStringExtra("host");
                    tvDeviceName.setText(Common.db.getName(Common.host));
                    break;
                case REQUEST_CODE_SETTING:
                    Common.run(ctx);
                    break;
            }
        }else {
            Toast.makeText(this, "Отмена", Toast.LENGTH_SHORT).show();
            Common.host="";
            tvDeviceName.setText("Устройство не выбрано");

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Log.d("adAirDebug", "OnStop" );
//        if(!Common.bound) return;
//        Common.stopDevice();
//        unbindService(Common.sconn);
    }
}