package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Servicios_DAO;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class Servicios_ofrece extends AppCompatActivity {
    Spinner spinner_servicios;
    String  servicio_balayague="balayague";
    ImageView fotoservicio;
    TextView ubicacion_texto;
    ImageView btn_guardar;
    EditText precio;

    private String nombre_uso_de_firebase; //aqui almacenamos el nombre desde el onresume que trae los datos de firebase
    private  String url_foto_perfil_usuario;

    private FirebaseAuth mAuth; //este lo usare para almacenar el nombre del usuario en turno

    private FirebaseDatabase database;
    private DatabaseReference referenceServicios;//Usuario firebase

    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes
    private StorageReference storageReference_serviciofoto;

   // private  Long fechaCalendar;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri foto_de_perfilURI;
   // private  Uri foto_servisio_uri_string;
    private   String foto_servicio_URI;
   public   String foto_emisor_servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_ofrece);

        fotoservicio=findViewById(R.id.foto_servicio);
        ubicacion_texto=findViewById(R.id.ubicacion_tx);
        precio=findViewById(R.id.precio_emisor);
        btn_guardar=(ImageView)findViewById(R.id.bt_guardar);
        //falta nombre,


        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        referenceServicios=database.getReference(Constantes.Nodo_servicios);
       // final DatabaseReference myRef_Servicos = database.getReference(Constantes.Nodo_venta_general);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference("foto__servicio");






        fotoservicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });




        btn_guardar.setOnClickListener(new View.OnClickListener() {

            String precio1=precio.getText().toString();


            public void onClick(View v) {

                final String precio1=precio.getText().toString();
                final String ubicacion="ñuñoa temporal";
                final  String nombre_intento2;

                Usuario_DAO.getInstance().getKeyUsuario();




                if (foto_de_perfilURI!=null){

                    Servicios_DAO.getInstance().subirfotoUri(foto_de_perfilURI, new Servicios_DAO.IDdevolverURLfoto() {
                        @Override
                        public void urlfoto(String url) {

                            Toast.makeText(Servicios_ofrece.this, "servicio guardado", Toast.LENGTH_LONG).show();

                            Servicios servicios=new Servicios();



                            servicios.setFotodePerfilURI_Servicio(foto_emisor_servicio);//foto del serviio

                            servicios.setNombre_servicio(servicio_balayague);
                            servicios.setUbicacion(ubicacion);
                            servicios.setPrecio(precio1);
                            servicios.setKey_servicio(Usuario_DAO.getInstance().getKeyUsuario());
                            servicios.setNombre_emisor(nombre_uso_de_firebase);


                            servicios.setFotode_servicio_URI(foto_servicio_URI);//foto del


                            Servicios_DAO.getInstance().servicio_ofrece(Servicios_DAO.getInstance().getKeyservicio(),servicios); //uso este codigo que almacena en 2 tabalas de nodos


                           // referenceServicios.push().setValue(servicios);



                            finish(); //con esto cada vez que termine se cierra y retorna al login




                        }
                    });


                } else {

                    Toast.makeText(Servicios_ofrece.this, "registro correctamente", Toast.LENGTH_LONG).show();


                    Servicios servicios=new Servicios();

                    servicios.setFotodePerfilURI_Servicio(Constantes.URL_foto_por_defecto_usuario);
                    servicios.setNombre_servicio(servicio_balayague);
                    servicios.setUbicacion(ubicacion);
                    servicios.setPrecio(precio1);
                    servicios.setKey_servicio(Usuario_DAO.getInstance().getKeyUsuario());
                    servicios.setNombre_emisor(nombre_uso_de_firebase);
                    //servicios.setFotode_servicio_URI(url);

                    Servicios_DAO.getInstance().servicio_ofrece(Servicios_DAO.getInstance().getKeyservicio(),servicios);

                    //Mensajeria_DAO.getInstance().nuevomensaje(Usuario_DAO.getInstance().getKeyUsuario(),KEY_RECEPROT,mensaje);  //String key_emisor, String key_receptor, Mensaje mensaje


                    //myRef_Servicos.push().setValue(servicios);





                    finish(); //con esto cada vez que termine se cierra y retorna al login



                }





            }
        });








    }

    public void chooseImage() { //selecciona una imagen del circleview para l fotod d e perfil
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  Bitmap bib=(Bitmap)data.getExtras().get("datacamera");
        //foto_de_perfil.setImageBitmap(bib);


        //comprovamos si se selecciono una foto de la galeria para el perfil
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {

            //imagepicked:submit(data);

            Uri uri = data.getData();
            foto_de_perfilURI=uri;
            FirebaseStorage storage_foto_cliente;
            StorageReference storage_Reference_cliente;
            storage_foto_cliente=FirebaseStorage.getInstance();
            storage_Reference_cliente=storage_foto_cliente.getReference("foto_servicio_generado_por_usuarios").child(Usuario_DAO.getInstance().getKeyUsuario()); //nombre de la carpeta en firebase que almacenara todas las imagenes
            final  StorageReference foto_referencia=storage_Reference_cliente.child(uri.getLastPathSegment()); //esste obtiene la llave de nuestra foto en este caso U para que todas las fotos sean diferentes a las demas

            foto_referencia.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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


                        foto_servicio_URI=downloadUrl.toString();

                    }
                }
            });

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                fotoservicio.setImageBitmap(bitmap);
                //ImageView imageView = findViewById(R.id.imageView2);
                //  imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //validar si seleccionamos una foto de la galeria , nota podemos ahcerlo como en el viedeo o usando un archivo aparte

        }
    }

    @Override
    protected void onResume() { //aqui recuperamos los datos de firebase del usuario, intento traer el nombre del usuario
        super.onResume();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        btn_guardar.setEnabled(false); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

        if (currentUser!=null){
            btn_guardar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado
            DatabaseReference reference_personal=database.getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid());

           // DatabaseReference reference_personal=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
            //con el anterior referencia podemos reclamar el nombre y el correo de firebase de el path usuarios

//            reference.setValue(Usuario.class);
            final String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getUid();

            reference_personal.addListenerForSingleValueEvent(new ValueEventListener() {// este hace que se llame una sola vez
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    //String value = dataSnapshot.getValue(String.class);
                    Usuario usu=dataSnapshot.getValue(Usuario.class);//pone la referencia

                   String usu2=usu.getUsuario_nombre();//aqui ya tengo el nombre ej jesus


                    String usu3=usu.getFotodePerfilURI();
                    foto_emisor_servicio=usu3;
                   nombre_uso_de_firebase=usu.getUsuario_nombre();



                   //nombre.setText(nombre_uso_de_firebase); //aqui lo envia a la pantalla de mensajeria donde esta nuestro nombre no el del chat
                    // usu.setUsuario_nombre(nombre_uso_de_firebase);
                    Toast.makeText(Servicios_ofrece.this, "nombre:"+usu2, Toast.LENGTH_SHORT).show(); //consirmado el nombre es correcto

                    btn_guardar.setEnabled(true); //y luego se habilita el boton para enviar la info con todos los datos cargados



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
        startActivity(new Intent(Servicios_ofrece.this,long.class));
        finish();
    }
}
