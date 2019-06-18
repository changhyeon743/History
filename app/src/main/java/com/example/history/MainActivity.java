package com.example.history;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ItemTouchHelperCallBack.OnItemMoveListener {


    ListView listView;
    ListAdapter listAdapter;

    List<Question> questions;
    TextView titleView;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        questions = new ArrayList<>();

        for (String[] i : readerCSV(getResources().openRawResource(R.raw.data))) {
            Question question = new Question(Integer.parseInt(i[0]),i[1],Integer.parseInt(i[2]),Integer.parseInt(i[3]),i[8]);
            question.setExample(i);
            questions.add(question);
        }

        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        //Log.e("size: ",""+questions.size());
        Random random = new Random();
        setDatas(random.nextInt(questions.size()));

    }

    public void setDatas(int index) {
        Question current = questions.get(index);

        listAdapter = new ListAdapter(current.example);
        
        //ItemTouchHelperCallBack callBack = new ItemTouchHelperCallBack(this);
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);
        //itemTouchHelper.att

        listView = findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        titleView = findViewById(R.id.title);
        titleView.setText(questions.get(index).title);
    }

    //csv 파일을 읽어 들여 List로 리턴

    public List<String[]> readerCSV(InputStream filePath){ //파라미터 : 읽어들일 파일경로+파일명

        List<String[]> content = new ArrayList<String[]>();

        CSVReader reader = null;

        try {

            reader = new CSVReader(new InputStreamReader(filePath, "euc-kr"));

            content = reader.readAll(); //전체 데이터를 가져옴.

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {  if(reader != null) reader.close(); } catch (IOException e) {}

        }

        return content;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onItemMove(int from, int to) {
        Question temp = questions.get(from);
        questions.set(from,questions.get(to));
        questions.set(to,temp);

        listAdapter.notifyDataSetChanged();
        return false;
    }
}
