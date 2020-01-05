package com.example.agenda_salonvip.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Citas_DAO;
import com.example.agenda_salonvip.persistencias_fire.Clientes_DF;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class Crear_cita extends AppCompatActivity {

    EditText fecha_cita,servicio_cita,tiempo_proceso;
    CircleImageView guardar_cita;

    private Long fechaCalendar2;

    FirebaseStorage storage_para_cliente;
    StorageReference storageReference_foto_cliente_para_cita;
    String fotoreferencia;

    private FirebaseDatabase database_cita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cita);

        fecha_cita=(EditText) findViewById(R.id.fecha_citafire);
        servicio_cita=(EditText) findViewById(R.id.servicio_cita_fire);
        tiempo_proceso=(EditText) findViewById(R.id.tiempo_estimado);

        final String nomm= getIntent().getStringExtra("nombre");

        final String tell= getIntent().getStringExtra("telefono");

        final String foto_cliente=getIntent().getStringExtra("foto");

        storage_para_cliente=FirebaseStorage.getInstance();
        storageReference_foto_cliente_para_cita=storage_para_cliente.getReference("fotos_perfil_clientes").child(foto_cliente); //nombre de la carpeta en firebase que almacenara todas las imagenes


        //final  StorageReference foto_referencia=storageReference.child(u.getLastPathSegment()); //esste obtiene la llave de nuestra foto en este caso U para que todas las fotos sean diferentes a las demas



        guardar_cita=findViewById(R.id.guardar_boton_cita);

        database_cita = FirebaseDatabase.getInstance();
        final DatabaseReference myRef_cita = database_cita.getReference("Citas");

        fecha_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog datePickerDialog= new DatePickerDialog(Crear_cita.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar can=Calendar.getInstance();
                        can.set(calendar.YEAR,year);
                        can.set(Calendar.MONTH,month);
                        can.set(calendar.DAY_OF_MONTH,dayOfMonth);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/YYYY");
                        Date date=can.getTime() ;

                        String fechaCalendarrrr=simpleDateFormat.format(date);
                        //Long fechalong=simpleDateFormat.format(date);//es string reune los datos
                        fechaCalendar2=date.getTime(); //con eto obtenermos el archivo de topo long a strig

                        fecha_cita.setText(fechaCalendarrrr);


                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });






        guardar_cita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fecha = fecha_cita.getText().toString().trim();
                final String servicio=servicio_cita.getText().toString().trim();
                final String tiempo=tiempo_proceso.getText().toString().trim();

                if (TextUtils.isEmpty(fecha)){

                    Toast.makeText(Crear_cita.this, "fecha", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(servicio)){

                    Toast.makeText(Crear_cita.this, "servicio", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(tiempo)){

                    Toast.makeText(Crear_cita.this, "ingresar tiempo del servicio", Toast.LENGTH_LONG).show();

                }




                CitaEnt citaEnt=new CitaEnt();

               citaEnt.setFotodePerfilURI(foto_cliente);
               //citaEnt.setFotodePerfilURI(Constantes.URL_foto_por_defecto_usuario);
                citaEnt.setFotodePerfilURI(foto_cliente);
                citaEnt.setNombre_cliente_cita(nomm);
                citaEnt.setTelefono_cliente_cita(tell);
                citaEnt.setFecha_de_cita(fechaCalendar2);
                citaEnt.setServicio(servicio);
                citaEnt.setTiempo_proceso(tiempo);

                Citas_DAO.getInstance().cita_nuevo(Citas_DAO.getInstance().getKeycliente(),citaEnt);




                //myRef_cita.push().setValue(citaEnt);




                finish(); //con esto cada vez que termine se cierra y retorna al login








            }










    });
}
}