package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import clientes_BBDD.clientes_BBDD;


public class interfaz2 extends AppCompatActivity {

    EditText nomclie,telclie,obserbacion;
    TextView servicio_realizado;
    ImageView camara,guardar;
    Spinner trabajo;



    clientes_BBDD helper=new clientes_BBDD(this,"base_clients",null,1);


  //  Sqlite_OpenHelper helper=new Sqlite_OpenHelper(this,"base_usuarios1",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interfaz2);

        nomclie=(EditText)findViewById(R.id.nombreclient);
        telclie=(EditText)findViewById(R.id.telclient);
        obserbacion=(EditText)findViewById(R.id.descripcion_trabajo);

        servicio_realizado=(TextView)findViewById(R.id.servicio2);

        camara=(ImageView)findViewById(R.id.FotoClient);
        guardar=(ImageView)findViewById(R.id.Guardar);

        trabajo=(Spinner)findViewById(R.id.servicios_realizado);



        //medidas de la ventana
        DisplayMetrics medidas_ventana=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidas_ventana);

        int ancho=medidas_ventana.widthPixels;
        int alto=medidas_ventana.heightPixels;

        //spiner
        String[] letra = {"Seleccionar","Corte","Barberia","Color","Promo","Balayague","Hidratacion","otro"};
        trabajo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        trabajo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        // String tx=trabajo.getSelectedItem().toString(); //obtiene el string seleccionado
        // int valie=(int)trabajo.getSelectedItem();//numero del valor seleccionado


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {


                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                servicio_realizado.setText(trabajo.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });
       //camara obtendra la direccion de la imagen y esta se almacenara en la base de datos
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.abri();//para abrir la base de datos
                //llama al metodo insertar rtegistro donde tiene los paraetros de la tabala sqlite
          /*  helper.insertarRegistro(String.valueOf(crear_usuario.getText()),
                    String.valueOf(crerar_contrasena.getText()),
                    String.valueOf(crearrut.getText()));*/
                helper.insertarRegistro_clientes(nomclie.getText().toString(),
                        telclie.getText().toString(),
                        servicio_realizado.getText().toString(),
                        obserbacion.getText().toString());

                helper.cerrar();

                String n=nomclie.getText().toString(),t=telclie.getText().toString();


                Toast.makeText(getApplicationContext(),"Registro almacenado con exito "+n,Toast.LENGTH_LONG).show();



                //asi retorna a la interfax 1 tras guardar el registro
                Intent i=new Intent(getApplicationContext(),cita.class);
                i.putExtra("usuarioT",n);
                i.putExtra("telusuT",t);
                startActivity(i);
                /*
                nomclie.setText("");
                telclie.setText("");
                servicio_realizado.setText("");
                obserbacion.setText(""); */


                //asi retorna a la interfax 1 tras guardar el registro
              //  Intent i=new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(i);



            }
        });







    }
}
