package io.github.giovana_morais.intergeracoes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import io.github.giovana_morais.intergeracoes.VideoAula;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    DatabaseReference db;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private String isAlive(){
        return "IT'S ALIVE!";
    }

//    recover data from DB
    public List<VideoAula> retrieve() {
        final List<VideoAula> classList;
        classList = new ArrayList<VideoAula>();

        Log.d("Sustenta", "criando lista");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classList.clear();

                //Loop 1 to go through all the child nodes of users
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    VideoAula aula = uniqueKeySnapshot.getValue(VideoAula.class);
                    if (aula != null) {
                        classList.add(aula);
                    }
                }
                for (int i = 0; i < classList.size(); i++) {
                    Log.d("Sustenta: ", "classList " + classList.get(i).getNome());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Sustenta", databaseError.getMessage());
            }
        });
       return classList;
    }
}