package com.iotcup.hodor.doorian;

import static com.iotcup.hodor.doorian.Constants.FIRST_COLUMN;
import static com.iotcup.hodor.doorian.Constants.SECOND_COLUMN;
import static com.iotcup.hodor.doorian.Constants.THIRD_COLUMN;
import static com.iotcup.hodor.doorian.Constants.DATABASE_NAME;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.iotcup.hodor.doorian.doorlist.ListViewAdapter;
import com.iotcup.hodor.doorian.fmhttp.Client;
import com.iotcup.hodor.doorian.fmhttp.HttpRequest;
import com.iotcup.hodor.doorian.fmhttp.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageButton mainDoor;
    private boolean mainDoorSate;
    private ListView doorListView;
    private ArrayList<HashMap<String, String>> doorList;
    private Button propertiesButton;
    private Button manageKeysButton;
    private Server server;
    //private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.doorList = new ArrayList<>();
        this.mainDoor = (ImageButton) findViewById(R.id.doorStateButton);
        this.mainDoorSate = true;
        this.doorListView = (ListView) findViewById(R.id.doorListView);
        this.propertiesButton = (Button) findViewById(R.id.propertiesButton);
        this.manageKeysButton = (Button) findViewById(R.id.manageKeysButtom);

        //try {
        //    this.server = Server.deserialize(this.getPackageResourcePath());
        //} catch (Exception e) {
        //    if(e instanceof FileNotFoundException){
        //        this.server = new Server();
                //this.server.setIP();
        //    }
        //}

        this.getDataFromServer();
        //this.database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        HashMap<String, String> temp = new HashMap<>();
        temp.put(FIRST_COLUMN, "Main");
        temp.put(SECOND_COLUMN, "LOCK");
        temp.put(THIRD_COLUMN, "03.10.1995");
        this.doorList.add(temp);

        HashMap<String, String> temp2 = new HashMap<>();
        temp2.put(FIRST_COLUMN, "Bath");
        temp2.put(SECOND_COLUMN, "LOCK");
        temp2.put(THIRD_COLUMN, "24.05.2016");
        this.doorList.add(temp2);

        HashMap<String, String> temp3 = new HashMap<>();
        temp3.put(FIRST_COLUMN, "Kids");
        temp3.put(SECOND_COLUMN, "OPEN");
        temp3.put(THIRD_COLUMN, "22.12.2015");
        this.doorList.add(temp3);

        ListViewAdapter doorListAdaptor = new ListViewAdapter(this, this.doorList);
        this.doorListView.setAdapter(doorListAdaptor);
        this.doorListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                HashMap<String, String> item = (HashMap<String, String>)parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, item.get(FIRST_COLUMN)+" Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromServer(){
        //String allDoorJson = HttpRequest.sendRequest("getAllDoor");
    }

    public void manageKeysButonAction(View view){
        Intent newIntent = new Intent(this, ManageKeysActivity.class);
        startActivity(newIntent);
    }

    public void historyButtonAction(View view){
        System.out.println("---Farzin---  HistoryButton Clicked");
        HashMap<String, String> temp = new HashMap<>();
        temp.put(FIRST_COLUMN, "Kids2");
        temp.put(SECOND_COLUMN, "OPENED");
        temp.put(THIRD_COLUMN, "22.12.2000");
        this.doorList.add(temp);
        ((ListViewAdapter) this.doorListView.getAdapter()).notifyDataSetChanged();
    }

    public void changeDoorStateAction(View view){
        if(this.mainDoorSate) {
            this.mainDoor.setImageResource(R.drawable.closedoorbuttom);
            this.mainDoorSate = false;
        }
        else{
            this.mainDoor.setImageResource(R.drawable.opendoorbuttom);
            this.mainDoorSate = true;
        }

    }
}
