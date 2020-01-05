package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class pagina_inicio extends AppCompatActivity {
    ImageView agregar_cliente,agregar_cita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicio);

        agregar_cliente=(ImageView)findViewById(R.id.nuevo_cliente);
        agregar_cita=(ImageView)findViewById(R.id.nueva_cita);


        agregar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),interfaz2.class);

                startActivity(i);

            }
        });

        agregar_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),cita_cliente_exitente.class);
                startActivity(i);
            }
        });
    }
}
