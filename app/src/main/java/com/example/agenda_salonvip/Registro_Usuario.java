package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import clientes_BBDD.clientes_BBDD;

public class Registro_Usuario extends AppCompatActivity {
    EditText crear_usuario, crerar_contrasena,crearrut;
    Button guarddar, atras_crear_cuetna;

    //pide contexto(valores),nombre d ela base de datos, v

    clientes_BBDD helper=new clientes_BBDD(this,"usuario_estilista",null,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuario);


        crearrut=(EditText)findViewById(R.id.rut_guardar);
        crear_usuario = (EditText) findViewById(R.id.nueo_usuario);
        crerar_contrasena = (EditText) findViewById(R.id.nueeva_contrasena);
        guarddar = (Button) findViewById(R.id.guardar_cuenta_nueva);


        guarddar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.abri();//para abrir la base de datos
                //llama al metodo insertar rtegistro donde tiene los paraetros de la tabala sqlite
          /*  helper.insertarRegistro(String.valueOf(crear_usuario.getText()),
                    String.valueOf(crerar_contrasena.getText()),
                    String.valueOf(crearrut.getText()));*/
                helper.insertarRegistro_usuario(crear_usuario.getText().toString(),
                        crerar_contrasena.getText().toString(),
                        crearrut.getText().toString());

                helper.cerrar();

                Toast.makeText(getApplicationContext(),"Registro almacenado con exito",Toast.LENGTH_LONG).show();

                //asi retorna a la interfax 1 tras guardar el registro
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);


            }


        });
    }
}

