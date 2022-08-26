package com.example.grupo2_lab2_iniciosesionfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
public class PerfilUsuario extends AppCompatActivity {
    TextView txt_id,txt_name,txt_email;
    ImageView imv_foto;
    DatabaseReference db_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent intent = getIntent();
        HashMap<String,String> info_user = (HashMap<String, String>) intent
                .getSerializableExtra("info_user");
        txt_id = findViewById(R.id.text_userId);
        txt_name = findViewById(R.id.txt_nombre);
        txt_email = findViewById(R.id.txt_correo);
        imv_foto = findViewById(R.id.imv_foto);

        String email = info_user.get("user_email");
        String provider = info_user.get("provider");
        System.out.println(email);
        System.out.println(provider);

        txt_id.setText(info_user.get("user_id"));
        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        String foto = info_user.get("user_foto");
        Picasso.with(getApplicationContext()).load(foto).into(imv_foto);

        iniciarBaseDeDatos();
        leerTweets();
    }
    public void iniciarBaseDeDatos(){
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupos");
    }
    public void leerTweets(){
        db_reference.child("Grupo2").child("tweets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            System.out.println(snapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println(error.toException());

                    }
                });
    }
    public void irRegistros(View v){
        Intent intent = new Intent(this, Registros.class);
        startActivity(intent);
    }

    public void cerrarSesion(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("msg","cerrarSesion");
        startActivity(intent);
    }

}