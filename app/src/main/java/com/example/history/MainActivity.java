package com.example.history;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerAdapter.Listener {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ItemTouchHelper itemTouchHelper;


    List<Question> questions;
    TextView titleView;
    Button confirmBtn;
    Button cancelBtn;
    String correctAnswer;

    SharedPreferences pref;

    NavigationView navigationView;
    View nav_header_view;

    TextView nav_header_text;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        nav_header_view = navigationView.getHeaderView(0);
        nav_header_text = (TextView) nav_header_view.findViewById(R.id.textView);

        confirmBtn = findViewById(R.id.confirmBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        //순서배열
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(recyclerAdapter.getAnswerText(),recyclerAdapter.getCommentary());
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffle();
            }
        });

        questions = new ArrayList<>();

        DataHelper dataHelper = new DataHelper(getResources().openRawResource(R.raw.data));

        for (String[] i : dataHelper.getDatas()) {
            Question question = new Question(Integer.parseInt(i[0]),i[1],Integer.parseInt(i[2]),Integer.parseInt(i[3]),i[8]);
            question.setExample(i);
            questions.add(question);
        }

        setRecyclerView();
        shuffle();

        pref = getSharedPreferences("history", MODE_PRIVATE);
        setNavigationText(String.valueOf(100* (float)pref.getInt("correct",0) / (float)pref.getInt("wrong",0)));

    }

    void setRecyclerView() {
        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ItemTouchHelperCallBack callBack = new ItemTouchHelperCallBack(recyclerAdapter);
        itemTouchHelper = new ItemTouchHelper(callBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(recyclerAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void shuffle() {
        Random random = new Random();

        ArrayList<Integer> arrayList = new ArrayList<>();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        for(int i=0;i<10;i++) {
            if (prefs.getBoolean(String.valueOf(i), false) == true ) {
                arrayList.add(i);

            }
        }
        int index = random.nextInt(questions.size());
        if (arrayList.contains(questions.get(index).getEra())) {
            setDatas(questions.get(index));
        } else {
            shuffle();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void setDatas(Question data) {
        recyclerAdapter.setExamples(data.getExamples());
        recyclerAdapter.setType(data.getType());
        recyclerAdapter.setCommentary(data.getCommentary());
        correctAnswer = "";

        switch (data.getType()) {
            case 0: //단답형
                correctAnswer = data.getExample(data.getAnswer());
                break;
            case 1: //순서
                List<String> datas = new ArrayList<>();
                String got = String.valueOf(data.getAnswer());
                for (int i = 0; i < got.length(); i++) {
                    //Log.e(":::"+Integer.parseInt(""+got.charAt(i)),"");
                    int pos = Integer.parseInt("0"+got.charAt(i));
                    datas.add(data.getExample(pos));
                }
                correctAnswer = String.join(" / ",datas);
                Toast.makeText(this, ""+correctAnswer, Toast.LENGTH_SHORT).show();

                break;
        }

        recyclerAdapter.notifyDataSetChanged();

        titleView = findViewById(R.id.title);
        titleView.setText(data.getTitle());
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingActivity.class);
            startActivity(intent);
            //shuffle();
            //Toast.makeText(this, ""+correctAnswer, Toast.LENGTH_SHORT).show();
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
    public void onStartDrag(RecyclerAdapter.ExampleViewHolder holder) {
        itemTouchHelper.startDrag(holder);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void answer(String text, String commentary) {
        //답하기
        if (text.equals(correctAnswer)) {
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show();
            correct();
            ///Intent
            Intent intent = new Intent(this,ExplainActivity.class);
            intent.putExtra("explain",commentary);
            startActivity(intent);
            shuffle();
        } else {
            Toast.makeText(this, "오답!", Toast.LENGTH_SHORT).show();
            wrong();
            Log.e("Your answer:",""+text);
            Log.e("Real answer:",""+correctAnswer);
            Log.e("Boolean","" + (text.equals(correctAnswer)));
        }
    }

    void wrong() {
        SharedPreferences.Editor editor = pref.edit();
        int current = pref.getInt("wrong",0);
        editor.putInt("wrong", current + 1 ); //키값, 저장값
        editor.commit();

        setNavigationText(String.valueOf(100* (float)current / (float)pref.getInt("wrong",0)));
    }

    void correct() {
        SharedPreferences.Editor editor = pref.edit();
        int current = pref.getInt("correct",0);
        editor.putInt("correct", current+ 1 ); //키값, 저장값
        editor.commit();

        setNavigationText(String.valueOf(100* (float)current / (float)pref.getInt("wrong",0)));


    }

    void setNavigationText(String str) {

        nav_header_text.setText("정답률: "+str+"%");
    }
}
