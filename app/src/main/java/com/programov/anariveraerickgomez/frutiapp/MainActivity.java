package com.programov.anariveraerickgomez.frutiapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_bestScore, tv_nombre, tv_score;
    private MediaPlayer mp;
    TableLayout TlScores;

    int num_aleatorio = (int) (Math.random() * 10);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText)findViewById(R.id.txt_nombre);
        iv_personaje = (ImageView)findViewById(R.id.imageView_Personaje);
        tv_bestScore = (TextView)findViewById(R.id.textView_BestScore);
        tv_score =(TextView) findViewById(R.id.txtScore);
        tv_nombre = (TextView) findViewById(R.id.txtNombre);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        int id;
        if(num_aleatorio == 0 || num_aleatorio == 10){
            id = getResources().getIdentifier("mango", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if(num_aleatorio == 1 || num_aleatorio == 9){
            id = getResources().getIdentifier("fresa", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if(num_aleatorio == 2 || num_aleatorio == 8){
            id = getResources().getIdentifier("manzana", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if(num_aleatorio == 3 || num_aleatorio == 7){
            id = getResources().getIdentifier("sandia", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if(num_aleatorio == 4 || num_aleatorio == 5 || num_aleatorio == 6){
            id = getResources().getIdentifier("uva", "drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max(score) from puntaje)", null);
        Cursor consulta2 = BD.rawQuery(
                "select * from puntaje", null
        );

        if(consulta.moveToFirst()){
            String nombre = consulta2.getString(0);
            String score = consulta2.getString(1);
            tv_nombre.setText(nombre);
            tv_score.setText(score);
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);
            tv_bestScore.setText("Record: " + temp_score + " de " + temp_nombre);


            BD.close();

        } else {
            BD.close();
        }

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);

    }




    public void Jugar(View view){
        String nombre = et_nombre.getText().toString();

        if(!nombre.equals("")){
            mp.stop();
            mp.release();

            Intent intent = new Intent(this, Main2Activity_Nivel1.class);

            intent.putExtra("jugador", nombre);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Primero debes escribir tu nombre", Toast.LENGTH_SHORT).show();

            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onBackPressed(){

    }
}
