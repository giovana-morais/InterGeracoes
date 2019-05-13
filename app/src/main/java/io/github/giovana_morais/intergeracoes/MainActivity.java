package io.github.giovana_morais.intergeracoes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ListView listaDeCursos = (ListView) findViewById(R.id.lista);
        List<VideoAula> cursos = todasAsAulas();
        ArrayAdapter<VideoAula> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cursos);
        listaDeCursos.setAdapter(adapter);
        listaDeCursos.setOnItemClickListener(this);
    }

    // TODO: tirar esses endereços hardcorded
    public List<VideoAula> todasAsAulas() {
        List aulas;
        aulas = new ArrayList();
        aulas.add(new VideoAula("Whatsapp", "Iniciar conversa", "gs://intergeracoes-2018.appspot.com/wpp_iniciar_conversa.mp4"));
        aulas.add(new VideoAula("Whatsapp", "Deletar mensagem", "gs://intergeracoes-2018.appspot.com/wpp_deletar_mensagem"));
        aulas.add(new VideoAula("Whatsapp", "Editar status", "gs://intergeracoes-2018.appspot.com/wpp_editar_status.mp4"));
        aulas.add(new VideoAula("Whataspp", "Encaminhar mensagem", "gs://intergeracoes-2018.appspot.com/wpp_encaminhar_mensagem.mp4"));
        aulas.add(new VideoAula("Whataspp", "Enviar áudio", "gs://intergeracoes-2018.appspot.com/wpp_enviar_audio.mp4"));
        aulas.add(new VideoAula("Whataspp", "Enviar emoji", "gs://intergeracoes-2018.appspot.com/wpp_enviar_emoji.mp4"));
        aulas.add(new VideoAula("Whataspp", "Trocar foto de perfil", "gs://intergeracoes-2018.appspot.com/wpp_foto_perfil.mp4"));
        aulas.add(new VideoAula("Whataspp", "Criar grupo", "gs://intergeracoes-2018.appspot.com/wpp_iniciar_grupo.mp4"));
        aulas.add(new VideoAula("Whataspp", "Fazer ligação", "gs://intergeracoes-2018.appspot.com/wpp_ligacao.mp4"));

        return aulas;
    }

    public void onItemClick(AdapterView lista, View v, int position, long id) {
        VideoAula va = new VideoAula();
        va = (VideoAula) lista.getItemAtPosition(position);

//        Log.d("Sustenta: onItemClick", "O Objeto clicado é " + va.getNome() + ". A URL é " + va.getUrl());

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
//                        Log.i("Sustenta", "Deletando diretório " + file.getName());
                        deleteTempFiles(f);
                    } else {
//                        Log.i("Sustenta", "Deletando arquivo " + file.getName());
                        f.delete();
                    }
                }
            }
        }
        return file.delete();
    }

}



