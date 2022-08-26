package com.example.grupo2_lab2_iniciosesionfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registros extends AppCompatActivity {
    DatabaseReference db_reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupos").child("Grupo5");
        leerRegistros();
    }
    public void leerRegistros(){
        db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    mostrarRegistrosPorPantalla(snapshot1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.toException());
            }
        });
    }
    public void mostrarRegistrosPorPantalla(DataSnapshot snapshot){
        LinearLayout contTemp = (LinearLayout) findViewById(R.id.ContenedorTemp);
        LinearLayout contHum = (LinearLayout) findViewById(R.id.ContenedorHum);
        String tempVal = String.valueOf(snapshot.child("uplink_message").child("decoded_payload").child("temp").getValue());
        String humVal = String.valueOf(snapshot.child("uplink_message").child("decoded_payload").child("humedad").getValue());

        /*double tempDouble = Math.round(Double.parseDouble(tempVal)*100)/100d;
        double humDouble = Math.round(Double.parseDouble(humVal)*100)/100d;
        String tempValue = String.valueOf(tempDouble);
        String humValue = String.valueOf(humDouble);*/
        TextView temp = new TextView(getApplicationContext());
        temp.setText(tempVal+" °C");
        contTemp.addView(temp);

        TextView hum = new TextView(getApplicationContext());
        hum.setText(humVal+" °C");
        contHum.addView(hum);
    }
}