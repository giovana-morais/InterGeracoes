package io.github.giovana_morais.intergeracoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DatabaseReference mainDB;
    private FirebaseHelper helper;
    private List<VideoAula> videoList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainDB = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(mainDB);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ListView videoList = (ListView) findViewById(R.id.lista);

        ArrayAdapter<VideoAula> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, helper.retrieve());

        videoList.setAdapter(adapter);
        videoList.setOnItemClickListener(this);

    }

    public void onItemClick(AdapterView lista, View v, int position, long id) {
        VideoAula va = new VideoAula();
        va = (VideoAula) lista.getItemAtPosition(position);

        Log.d("Sustenta: onItemClick", "O Objeto clicado é " + va.getNome() + ". A URL é " + va.getUrl());

        Intent intent = new Intent();

        // isso aqui vai ser alterado pro resto lá
        intent.setClass(this, ListItemDetail.class);
        intent.putExtra("position", position);
        intent.putExtra("url", va.getUrl());
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if(!isChangingConfigurations()) {
            deleteTempFiles(getCacheDir());
        }
    }

    private boolean deleteTempFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteTempFiles(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
        return file.delete();
    }

}



