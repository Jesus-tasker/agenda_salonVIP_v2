package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import clientes_BBDD.clientes_BBDD;

public class cita extends AppCompatActivity {

    ImageView nueva_cita;
    ImageButton guardarcita;
    TextView  nombreusu,teleuso,observacionusu,ultimotrabajo,fecha;
    Calendar c;
    DatePickerDialog Date_PD;
    Spinner pelu_date;


    clientes_BBDD helper=new clientes_BBDD(this,"base_clients",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        nombreusu=(TextView)findViewById(R.id.muestraclient);
        teleuso=(TextView) findViewById(R.id.muestratel);
        ultimotrabajo=(TextView) findViewById(R.id.muestraTrab);
        observacionusu=(TextView) findViewById(R.id.ultimo_trabajo);
        nueva_cita=(ImageView)findViewById(R.id.cita);
        fecha=(TextView)findViewById(R.id.date);
        pelu_date=(Spinner)findViewById(R.id.citas_trabajo);
        guardarcita=(ImageButton)findViewById(R.id.guardar_cita);


        String[] letra = {"Seleccionar","Corte","Barberia","Color","Promo","Balayague","Hidratacion","otro"};
        pelu_date.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

            // String selecciona;
        pelu_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // String tx=trabajo.getSelectedItem().toString(); //obtiene el string seleccionado
            // int valie=(int)trabajo.getSelectedItem();//numero del valor seleccionado


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {


                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
              String  selecciona=pelu_date.getSelectedItem().toString();
                //servicio_realizado.setText(pelu_date.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });


        login();


        nueva_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c=Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                Date_PD=new DatePickerDialog(cita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int nyear, int nMonth, int nYear) {

                        fecha.setText(nyear+"/"+(nMonth+1)+"/"+nYear);

                    }
                },day,month,year);
                Date_PD.show();

            }
        });

        guardarcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String v1=fecha.getText().toString(),
                        v2=nombreusu.getText().toString()
                        ,v3=teleuso.getText().toString()
                        ,v4=pelu_date.getSelectedItem().toString();
                //asi retorna a la interfax 1 tras guardar el registro
                Intent i=new Intent(getApplicationContext(),proxima_cita.class);
                i.putExtra("fecha",v1);
                i.putExtra("nom",v2);
                i.putExtra("tel",v3);
                i.putExtra("serv",v4);
                startActivity(i);
            }
        });


    }


    public void login() { //intetno crear un login que cree la Sqlexception que me pide para no fallar

        String clienteahora=getIntent().getExtras().getString("usuarioT");
        String telcliente=getIntent().getExtras().getString("telusuT");
        String compara1=clienteahora;
        String comapra2=telcliente;
        Cursor cursor1 = helper.cargar_usuarios(compara1, comapra2); //comprara los valores de ser ciertos carga valores
        cursor1.moveToFirst();
        nombreusu.setText(cursor1.getString(1));
        teleuso.setText(cursor1.getString(2));
        ultimotrabajo.setText(cursor1.getString(3));
        observacionusu.setText(cursor1.getString(4));




    }

 
}
