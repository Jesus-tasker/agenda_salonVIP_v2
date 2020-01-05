package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.agenda_salonvip.Adaptadores.Adaptador_Clientes_list;
import com.example.agenda_salonvip.Adaptadores.Adapter_cliente3;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Clientes_DF;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_clientes3 extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Adapter_cliente3 adapter_cliente3;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes
    private  static final  int foto_send=1; //valor requerido para enviar fotos un valor unico que no vambie
    private  static final int foto_de_perfil=2; //entero usado para validar la accion d ebusqueda =2
    private String fotoperfil_cadena; //la variable que se sussa para agregar la foto por el Usuario

    private FirebaseAuth mAuth;


    private String nombre_uso_de_firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes3);

        recyclerView=(RecyclerView)findViewById(R.id.clientes_3);

        //conectamos a firebase parte 2 consejo 11
        database=FirebaseDatabase.getInstance();//crea la intancia
        databaseReference=database.getReference(Constantes.Nodo_clientes);//esta es la sala de chat en este caso se lalamra chat y el NODO QUE ENVIA LA INFORMACION



        mAuth=FirebaseAuth.getInstance(); //esta es usada para el nombre que tenemos almacennado en firebase


        storage=FirebaseStorage.getInstance();//instanciamos storage

        adapter_cliente3=new Adapter_cliente3(this);
         LinearLayoutManager i=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(i);
        recyclerView.setAdapter(adapter_cliente3);

        databaseReference.addChildEventListener(new ChildEventListener() {

            Map<String, Lclientes> mapusuarioTemporales_lista=new HashMap<>(); //este sera  lal lista de nodos de firebase para el chat los deja y los deja como un seguimiento d enosotros


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Clientes m=dataSnapshot.getValue(Clientes.class); //se crea el valor de tipo Mensaje a enviar y la clase de la que este es



                final Lclientes lclientes=new Lclientes(dataSnapshot.getKey(),m); //el datasnpshop almacena la key que es el uid  del mensaje en turno, en otras palabras llamo a un methodo que crga la informacion de dichi nodo

                final int posicion_Lista= adapter_cliente3.AddMenssnaje(lclientes); //se crea el adaptador del Mensaje y se agrega como una constante m del Mensaje  que se vera en la lisata de a sala de chat
                //addmensaje se encarga de retornar la posicion dle emnsaje pues cambiamos el codigo

                Clientes_DF.getInstance().obtenerinformacionDeUsuarioporLLaveUID(m.getKey_cita(), new Clientes_DF.IDdeviolvercliente() {
                    @Override
                    public void devolverUsuario(Lclientes lclientes) {

                        mapusuarioTemporales_lista.put(m.getKey_cita(),lclientes); //obtenemos la key del usuario temporal, y guarda un usuario temporalmetn


                        //lMensajes.setlUsuario(lUsuario); //esta linea de codigo es la que hace que caiga cuando cargo al app

                        adapter_cliente3.actualizarmensajee(posicion_Lista,lclientes);

                    }

                    @Override
                    public void debolvererror(String eror) {
                        Toast.makeText(Lista_clientes3.this,"Errror "+eror,Toast.LENGTH_SHORT).show();

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        generateStoragePermissions(this);


        }
        public  static  boolean  generateStoragePermissions (Activity activity) { //este es codigo extra para que el Mensaje se pueda usar en celulares con rango alto de api y permisos mas avanzados en el manifest
            String [] PERMISSIONS_STORAGE  = {
                    Manifest.permission. READ_EXTERNAL_STORAGE ,
                    Manifest.permission . WRITE_EXTERNAL_STORAGE
            };
            int  REQUEST_EXTERNAL_STORAGE  =  1 ;
            int permission =  ActivityCompat. checkSelfPermission (activity, Manifest.permission. WRITE_EXTERNAL_STORAGE );
            if (permission !=  PackageManager. PERMISSION_GRANTED ) {
                ActivityCompat . requestPermissions (
                        activity,
                        PERMISSIONS_STORAGE ,
                        REQUEST_EXTERNAL_STORAGE
                );
                return   false ;
            } else  {
                return true;
            }
        }



    }
