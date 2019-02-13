package io.github.giovana_morais.intergeracoes;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ListItemDetail extends AppCompatActivity {
    private Button btnstop, btnplay;
    private VideoView vv;
    private MediaController mediacontroller;
    private Uri uri;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        int position = intent.getIntExtra("position", 0);
        progressBar = (ProgressBar) findViewById(R.id.progrss);
        vv = (VideoView) findViewById(R.id.vv);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);

        // Firebase
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        File localFile = null;

        try {
            localFile = File.createTempFile("videos", "mp4", getCacheDir());
            progressBar.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Sustenta", "erro criando arquivo temporário");
        }

        final File finalLocalFile = localFile;
        gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
                Log.d("Sustenta", "Arquivo baixado com sucesso!");
                String uriPath = finalLocalFile.getAbsolutePath();
                uri = Uri.parse(uriPath);
                Log.d("Sustenta: Download", "uriPath: " + uriPath);
                progressBar.setVisibility(View.GONE);
                vv.setMediaController(mediacontroller);
                vv.setVideoURI(uri);
                vv.requestFocus();
                vv.start();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("Sustenta", "Deu pau na hora de baixar o vídeo para o arquivo temporário");
            }
        });
    }
}