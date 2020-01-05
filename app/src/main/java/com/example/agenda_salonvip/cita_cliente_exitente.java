package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import clientes_BBDD.clientes_BBDD;

public class cita_cliente_exitente extends AppCompatActivity {

   AutoCompleteTextView busca_nombre,busca_telefono;
    ListView resultado_nusqueda;

    clientes_BBDD helper=new clientes_BBDD(this,"base_clients",null,1);

    ArrayList<String>usuario_nombre;
    ArrayList<String>busca_telofonossss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_cliente_exitente);

        busca_nombre=(AutoCompleteTextView) findViewById(R.id.buscar_persona);
        busca_telefono=(AutoCompleteTextView) findViewById(R.id.buscar_telefono);

        resultado_nusqueda=(ListView)findViewById(R.id.resultado_busqueda);

        clietesbbdd();
        telefonobbdd();


        ArrayAdapter<String> adaptador =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,usuario_nombre);

        busca_nombre.setAdapter(adaptador);

        ArrayAdapter<String> adaptadorius =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,busca_telofonossss);

        busca_telefono.setAdapter(adaptadorius);





    }

    private void telefonobbdd() {
        helper.abri();
        String persona;
        busca_telofonossss=new ArrayList<String>();
        Cursor cursor=helper.lista_clientes_telefono();
        //retorna un cursos SELECT telefono FROM clientes

        while (cursor.moveToNext()){

            persona=cursor.getString(0);

            busca_telofonossss.add(persona);
        }
    }

    private void clietesbbdd() {
        //aqui intentare descargar la lista de los nombres de los usuarios y almacenarlos al edit tex
        helper.abri();
       // datos_usuarios persona=null;
        String persona;
       usuario_nombre=new ArrayList<String>();
        Cursor cursor=helper.lista_clientes_nombre();
        //retorna un cursos SELECT * FROM clientes

        while (cursor.moveToNext()){
          /*  persona=new datos_usuarios();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2)); */

          persona=cursor.getString(0);

          usuario_nombre.add(persona);
        }

       // nuevo=this.getReadableDatabase().rawQuery("SELECT * FROM clientes",null);

    }
}
