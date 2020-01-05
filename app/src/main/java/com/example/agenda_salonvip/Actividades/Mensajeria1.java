package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Adaptadores.Adaptador_mensajes;
import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_fire.Mensaje;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.Entidades_logicas.LMensaje;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Citas_DAO;
import com.example.agenda_salonvip.persistencias_fire.Clientes_DF;
import com.example.agenda_salonvip.persistencias_fire.Mensajeria_DAO;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mensajeria1 extends AppCompatActivity {
    private CircleImageView foto_perfil;
    private TextView nombre;
    private RecyclerView recyclerView;
    private EditText txtmensaje;
    private Button enviar,cerrar_cession;
    private ImageButton btn_enviar_foto;
    //consejo 9, una vez creado el dapter _mensaje que carga los mensajes
    private Adaptador_mensajes adapter_mensajes;

    //conectar a firebase consejo como 10 solo sigue
    private FirebaseDatabase database;
    //private DatabaseReference databaseReference;

    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes
    private  static final  int foto_send=1; //valor requerido para enviar fotos un valor unico que no vambie
    private  static final int foto_de_perfil=2; //entero usado para validar la accion d ebusqueda =2
    private String fotoperfil_cadena; //la variable que se sussa para agregar la foto por el Usuario

    private FirebaseAuth mAuth;


    private String nombre_uso_de_firebase;

    private String KEY_RECEPROT;

    DatabaseReference databaseReference_clientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria1);



        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){ //si el bundle tiene los parametros del usuario que toco
            KEY_RECEPROT=bundle.getString("key_receptor"); //la llave lclientes


        }else {
            finish();
        }

        final String fotousu = getIntent().getStringExtra("foto");
        final String nombreusu = getIntent().getStringExtra("nombre");
        final String telusu = getIntent().getStringExtra("telefono");
        final String key_receptor = getIntent().getStringExtra("key_receptor");

        foto_perfil=(CircleImageView)findViewById(R.id.foto_perfil);
        nombre=(TextView)findViewById(R.id.nombre);
        recyclerView=(RecyclerView)findViewById(R.id.mensajes);
        txtmensaje=(EditText)findViewById(R.id.txtmensaje);
        enviar=(Button)findViewById(R.id.enviar);
        btn_enviar_foto=(ImageButton) findViewById(R.id.agrega_imagen);
        fotoperfil_cadena=""; //iniciamos con la foto d eperfil vacia


       // nombre.setText(nombreusu.trim().toString());

        databaseReference_clientes= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_clientes).child(Citas_DAO.getInstance().getKeycliente().trim()); //referencia del nodo clientes




        //conectamos a firebase parte 2 consejo 11
        //database=FirebaseDatabase.getInstance();//crea la intancia
        //databaseReference=database.getReference(Constantes.Nodo_chat+"/"+Usuario_DAO.getInstance().getKeyUsuario()+"/"+KEY_RECEPROT);//esta es la sala de chat en este caso se lalamra chat y el NODO QUE ENVIA LA INFORMACION
//nota importante para crear un subnodo se pone +"/"+ del nodo para crearlo en este caso el chat
        //mensajeria
           //usuario1
                    //usuario2
            //usuario2
                    //usuario1




        mAuth=FirebaseAuth.getInstance(); //esta es usada para el nombre que tenemos almacennado en firebase


        storage=FirebaseStorage.getInstance();//instanciamos storage


        adapter_mensajes=new Adaptador_mensajes(this);
        LinearLayoutManager i=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(i);
        recyclerView.setAdapter(adapter_mensajes);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menviar=txtmensaje.getText().toString();

                if (!menviar.isEmpty()){

                    Mensaje mensaje=new Mensaje();
                    mensaje.setMensaje(menviar);
                    mensaje.setContienefoto(false);
                    mensaje.setKeyemior(Usuario_DAO.getInstance().getKeyUsuario());
                    Mensajeria_DAO.getInstance().nuevomensaje(Usuario_DAO.getInstance().getKeyUsuario(),KEY_RECEPROT,mensaje);  //String key_emisor, String key_receptor, Mensaje mensaje
                    txtmensaje.setText("");

                }
                Mensaje mensaje=new Mensaje();




            }
        });

        btn_enviar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para agregar y enviar imagenes primero creamos un intent

                Intent i=new Intent(Intent.ACTION_GET_CONTENT);// intento de otener contenido sea fotos o videos
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"selecciona una foto"),foto_send); //este verifica lo qeu pones como resultado aunque podriamos leer ams core este me quedo dudas vistoa  lso 32.18 min del video




            }
        });

        /*foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para agregar y enviar imagenes primero creamos un intent
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);// intento de otener contenido sea fotos o videos
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"selecciona una foto"),foto_de_perfil); //este verifica lo qeu pones como resultado aunque podriamos leer ams core este me quedo dudas vistoa  lso 32.18 min del video

            }
        });*/


        adapter_mensajes.registerAdapterDataObserver(new  RecyclerView.AdapterDataObserver(){ //con este codigo al llenar el list view se mantiene en el ultimo chat inscrito por el Usuario

            //nota genera los ojetos de las librerias
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {//este methodo pasa a la ultima posiscion de la lista
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        FirebaseDatabase.getInstance().getReference(Constantes.Nodo_chat)//noso mensajes
                .child(Usuario_DAO.getInstance().getKeyUsuario())//usuario1
                .child(KEY_RECEPROT) //usuario 2
                .addChildEventListener(new ChildEventListener() {

            Map<String, Lclientes> mapusuarioTemporales_lista=new HashMap<>(); //este sera  lal lista de nodos de firebase para el chat los deja y los deja como un seguimiento d enosotros




            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                final Mensaje mensaje=dataSnapshot.getValue(Mensaje.class);

                final LMensaje lMensajes=new LMensaje(dataSnapshot.getKey(),mensaje);

                final int position=adapter_mensajes.AddMenssnaje(lMensajes);

                if (mapusuarioTemporales_lista.get(mensaje.getKeyemior())!=null){


                    lMensajes.setLclientes(mapusuarioTemporales_lista.get( mensaje.getKeyemior()));
                    adapter_mensajes.actualizarmensajee(position,lMensajes);


                }else {
                    Clientes_DF.getInstance().obtenerinformacionDeUsuarioporLLaveUID(mensaje.getKeyemior(), new Clientes_DF.IDdeviolvercliente() {
                        @Override
                        public void devolverUsuario(Lclientes lclientes) {
                            mapusuarioTemporales_lista.put(mensaje.getKeyemior(),lclientes);

                            //lMensajes.getlUsuario();

                            lMensajes.setLclientes(lclientes); //esta linea de codigo es la que hace que caiga cuando cargo al app

                            adapter_mensajes.actualizarmensajee(position,lMensajes);

                        }

                        @Override
                        public void debolvererror(String eror) {

                        }
                    });
                }

                /*
                else { //si el usuario no extite
                    Usuario_DAO.getInstance().obtenerinformacionDeUsuarioporLLaveUID(mensaje.getKeyemior(), new Usuario_DAO.IDdeviolverUsuario() {
                        @Override
                        public void devolverUsuario(LUsuario lUsuario) { //aui se ejecuta cuando ya tenemos la informacion del usuario en firebase methdo usuario_dao

                            mapusuarioTemporales_lista.put(mensaje.getKeyemior(),lUsuario);

                            //lMensajes.getlUsuario();

                            lMensajes.setlUsuario(lUsuario); //esta linea de codigo es la que hace que caiga cuando cargo al app

                            adapter_mensajes.actualizarmensajee(position,lMensajes);
                        }

                        @Override
                        public void debolvererror(String eror) {

                            Toast.makeText(Mensajeria1.this,"Errror "+eror,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                */

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
    private void setScrollBar(){ //esto es un adaptador para que al escribir elmesnaje se mantenga al final methodo traido de registerAdapterDAtaObserv

        recyclerView.scrollToPosition(adapter_mensajes.getItemCount()-1);
    }


    //este es codigo extra para que el Mensaje se pueda usar en celulares con rango alto de api y permisos mas avanzados en el manifest

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

    //el siguiente codigo se trae d ela actividad es para el usao d eimagenes, el caul selecciona las imagenes y laas almacena para luego verificar su tipo
    //este methodo se puede generar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //requearcode es el dato que usaremos para el methdodo obclic que rastrea la imagen
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==foto_send && resultCode==RESULT_OK){//SI LA IMAGEN SE HA SELECCIONADO Y EL RESULTADO SEA OK , fotosend es una variable estatica que esta privada al inicio
            Uri u=data.getData(); //cuando se cumple la funcion nos genera un cogido que se almacena en ury u y asi lo usamos
            storageReference=storage.getReference("imagenes_lol"); //nombre de la carpeta en firebase que almacenara todas las imagenes


            final  StorageReference foto_referencia=storageReference.child(u.getLastPathSegment()); //esste obtiene la llave de nuestra foto en este caso U para que todas las fotos sean diferentes a las demas
            //

            //el siguientee codigo indica que si se selecciono una foto y se selecciono correctamenet se envie a la nuve y se obtenga su uri

            foto_referencia.putFile(u).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            throw Objects.requireNonNull(task.getException());//atrapador de excepciones

                        }
                    }
                    return foto_referencia.getDownloadUrl();//obtene la url d ela foto que sube actilmentee
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() { //addonclick listener
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){

                        Uri downloadUrl = task.getResult(); //reconoce el ury
                        Mensaje mensaje=new Mensaje();

                        mensaje.setMensaje("el Usuario envio una foto"); //despues ponemos la direccion de lafoto
                        mensaje.setUrlfoto(downloadUrl.toString());
                        mensaje.setContienefoto(true);
                        mensaje.setKeyemior(Usuario_DAO.getInstance().getKeyUsuario()); //nota el codigo de pesristencias para obtener el key del emisor

                        fotoperfil_cadena=downloadUrl.toString();
                        Mensajeria_DAO.getInstance().nuevomensaje(Usuario_DAO.getInstance().getKeyUsuario(),KEY_RECEPROT,mensaje);
                        // databaseReference.push().setValue(mensaje);
                        //Glide.with(Mensajeria.this).load(downloadUrl.toString()).into(foto_perfil);
                        //  Toast.makeText(Mensajeria.this, "Subida exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }



    }

    @Override
    public void onResume() { //aqui recuperamos los datos de firebase
        super.onResume();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser!=null){
            // enviar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference(Constantes.Nodo_clientes+"/"+Usuario_DAO.getInstance().getKeyUsuario());

            FirebaseStorage storage_usu_foto=FirebaseStorage.getInstance();
            StorageReference storageReference3=storage_usu_foto.getReference("fotos_perfil_clientes").child(Usuario_DAO.getInstance().getKeyUsuario());





            reference.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos


                    //String value = dataSnapshot.getValue(String.class);


                    Clientes usu=dataSnapshot.getValue(Clientes.class);//pone la referencia

                    String usu2=usu.getUsuario_cliente();
                    String foto_ussss=usu.getFotodePerfilURI();



                    nombre_uso_de_firebase=usu.getUsuario_cliente();
                    Glide.with(getApplicationContext()).load(foto_ussss).into(foto_perfil);



                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                    // foto_perfil_usuario.setImageBitmap(bitmap);
                    //aqui lo envia a la pantalla de mensajeria donde esta nuestro nombre no el del chat
                    //  usu.setUsuario_nombre(nombre_uso_de_firebase);
                    //Toast.makeText(Mensajeria.this, "nombre:"+usu2, Toast.LENGTH_SHORT).show();

                    //enviar.setEnabled(true); //y luego se habilita el boton para enviar la info con todos los datos cargados



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            //returnLogin();
        }


    }

    /*
    @Override
    protected void onResume() { //aqui recuperamos los datos de firebase
        super.onResume();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        enviar.setEnabled(false); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

        if (currentUser!=null){
            enviar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

            DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
            //con el anterior referencia podemos reclamar el nombre y el correo de firebase de el path usuarios

//            reference.setValue(Usuario.class);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {// este hace que se llame una sola vez
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos


                    //String value = dataSnapshot.getValue(String.class);
                    Usuario usu=dataSnapshot.getValue(Usuario.class);//pone la referencia

                    String usu2=usu.getUsuario_nombre();
                    FirebaseUser currentUser = mAuth.getCurrentUser();


                    nombre_uso_de_firebase=usu.getUsuario_nombre();
                    nombre.setText(nombre_uso_de_firebase); //aqui lo envia a la pantalla de mensajeria donde esta nuestro nombre no el del chat
                    //  usu.setUsuario_nombre(nombre_uso_de_firebase);
                    //Toast.makeText(Mensajeria.this, "nombre:"+usu2, Toast.LENGTH_SHORT).show();

                    enviar.setEnabled(true); //y luego se habilita el boton para enviar la info con todos los datos cargados



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            returnLogin();
        }
    }

    private void returnLogin(){
        startActivity(new Intent(Mensajeria1.this,long.class));
        finish();
    }

     */
}
