package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import button_navegation.Navegador;
import clientes_BBDD.clientes_BBDD;

public class proxima_cita extends AppCompatActivity {
    TextView v1,v2,v3,v4;
    ImageView guardar_cita;

    clientes_BBDD helper=new clientes_BBDD(this,"base_citas",null,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxima_cita);
        v1=(TextView)findViewById(R.id.nom_cita);
        v2=(TextView)findViewById(R.id.fecha_cita);
        v3=(TextView)findViewById(R.id.tel_cita);
        v4=(TextView)findViewById(R.id.serv_cita);
        guardar_cita=(ImageView)findViewById(R.id.guardar_cita);

        String v11= getIntent().getExtras().getString("fecha");
        String v21= getIntent().getExtras().getString("nom");
        String v31= getIntent().getExtras().getString("tel");
        String v41= getIntent().getExtras().getString("serv");

        v1.setText(v11);
        v2.setText(v21);
        v3.setText(v31);
        v4.setText(v41);

        final String a=v1.getText().toString(),
                b=v2.getText().toString(),
                c=v3.getText().toString(),
                d=v4.getText().toString();





        //medidas de la ventana
        DisplayMetrics medidas_ventana=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidas_ventana);

        int ancho=medidas_ventana.widthPixels;
        int alto=medidas_ventana.heightPixels;

        getWindow().setLayout((int)(ancho*0.85),(int)(alto*0.6));//tamaño d ela pantalla emergente


        guardar_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.abri();//para abrir la base de datos

                helper.nuevacita(a,b,c,d);

                helper.cerrar();

                String c=v1.getText().toString();




                Toast.makeText(getApplicationContext(),"proxima cita"+a,Toast.LENGTH_LONG).show();



                //asi retorna a la interfax 1 tras guardar el registro
                Intent i=new Intent(getApplicationContext(), Navegador.class);
                startActivity(i);
            }
        });

    }

}
