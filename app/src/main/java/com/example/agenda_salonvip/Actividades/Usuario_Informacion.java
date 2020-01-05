package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Servicios_DAO;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import button_navegation.Navegador;
import de.hdodenhof.circleimageview.CircleImageView;

public class Usuario_Informacion extends AppCompatActivity {

    CircleImageView foto_usuario_perfil;
    String profesion_seleccionada,nombreusuario;
    TextView tv_profesion_seleccionada,nombreusuario_texview;
    Spinner sp_profesion;
    EditText descipcion_trabajo,telefono_usuario;
    ImageView guardar;
    private final static String[] profesiones = { "Estilista Interal", "Colorista capilar", "Especialista en Manicure",
            "Podologia Especializada", "Asesor de Imagen", "Esteticista","Peluqueria Interal ","Profesional Integral" };

    private int PICK_IMAGE_REQUEST = 1; //para foto perfil
    private Uri foto_de_perfilURI_enviar;
    private  String foto_perfil_string_URI_enviada;
    private String nombre_uso_de_firebase;

    FirebaseAuth mAuth;//methodo onresume
    FirebaseUser firebaseUser;
    DatabaseReference reference_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario__informacion);

        foto_usuario_perfil=(CircleImageView)findViewById(R.id.usuario_foto_perfil_circulo);
        nombreusuario_texview=findViewById(R.id.nombre_usu_perfil_tx);
        telefono_usuario=(EditText)findViewById(R.id.telefono_usu_Actual);
        sp_profesion=findViewById(R.id.spiner_usuario_dato);
        tv_profesion_seleccionada=findViewById(R.id.profe_seleccionada);
        descipcion_trabajo=findViewById(R.id.descripcion_profesional);
        guardar=findViewById(R.id.guardar_info_usuario);
        mAuth=FirebaseAuth.getInstance();


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();//aqui obtenermos realmente nuestro usuario con autenticacion de usuario
        reference_data=FirebaseDatabase.getInstance().getReference(Constantes.Nodo_Usuario).child(firebaseUser.getUid()); //aqui trabajamos con el uid d no se si es el mismo Usuario_DAO.getInstance().getKeyUsuario();
        final DatabaseReference reference_data_ventas = FirebaseDatabase.getInstance().getReference(Constantes.Nodo_venta_general).child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //aqui trabajamos con el uid d no se si es el mismo Usuario_DAO.getInstance().getKeyUsuario();


        foto_usuario_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, profesiones);

        sp_profesion.setAdapter(adapter);
        profesion_seleccionada = sp_profesion.getSelectedItem().toString();//texto seleccionado
       // Toast.makeText(Usuario_Informacion.this, profesion_seleccionada, Toast.LENGTH_LONG).show();



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String profesion = profesion_seleccionada;
                final String descripcion_usuario=descipcion_trabajo.getText().toString().trim();
                final  String telefono_actual=telefono_usuario.getText().toString().trim();

                if (TextUtils.isEmpty(descripcion_usuario)){

                    Toast.makeText(Usuario_Informacion.this, "describe tu trabajo", Toast.LENGTH_LONG).show();

                }



                if ( valida_descripcion_usuario(descripcion_usuario)&& valida_telefono(telefono_actual)){


                    if (foto_de_perfilURI_enviar!=null){ //si el usuario no sube foto de perfil se cargara na por defecto tipo uri
                        //aqui llamamos a la ubicacion  y conextion e tiempo real de el almacenamiento de la foto de perfil su URI se almacenrara

                        //aqui trabaja con el methodo uri como foto de perfil y trabaja con una interfaz interna para conectarse


                        Usuario_DAO.getInstance().subirfotoUri(foto_de_perfilURI_enviar, new Usuario_DAO.IDdevolverURLfoto() {
                            @Override
                            public void urlfoto(String url) {

                                Toast.makeText(Usuario_Informacion.this, "usuario actualizado", Toast.LENGTH_LONG).show();

                                Usuario usuario =new Usuario();
                                usuario.setUsuario_nombre(nombre_uso_de_firebase);
                                usuario.setFotodePerfilURI(foto_perfil_string_URI_enviada);
                                usuario.setTeleono_usuario(telefono_actual);
                                usuario.setProfesion(profesion);
                                usuario.setHabilidad_profesional(descripcion_usuario);


                                Usuario_DAO.getInstance().actualizar_info_usuario_y_foto(FirebaseAuth.getInstance().getCurrentUser().getUid(),usuario);




                                Servicios servicios=new Servicios();
                                servicios.setFotodePerfilURI_Servicio(foto_perfil_string_URI_enviada);
                                Map<String,Object> mapaensuso=new HashMap<>();
                                mapaensuso.put("fotodePerfilURI_Servicio",foto_perfil_string_URI_enviada);
                                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(Constantes.Nodo_venta_general).child(FirebaseAuth.getInstance().getCurrentUser().getUid());//aqui esta mi teoria para actualizar la foto del usuario de venta general
                                //Query query = databaseReference.;
                             //   databaseReference.setValue(mapaensuso);
                                Query query = databaseReference.orderByChild("key_servicio").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().trim());

                                query.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Servicios m = dataSnapshot.getValue(Servicios.class);
                                        m.setFotodePerfilURI_Servicio(foto_perfil_string_URI_enviada);
                                        databaseReference.setValue(m);
                                        for (DataSnapshot ds:dataSnapshot.getChildren()){

                                           // myRef01.setValue("nuevaurldeperfil.jpg");

                                          //Servicios m = dataSnapshot.getValue(Servicios.class);
                                          //m.setFotodePerfilURI_Servicio(foto_perfil_string_URI_enviada);
                                          //databaseReference.setValue(m);



                                           //m.setFotodePerfilURI_Servicio(foto_perfil_string_URI_enviada);


                                            //Aqui ya tengo la referencia usuario/venta1,venta2,venta3..

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });




                                //databaseReference.updateChildren(mapaensuso);

                               // Servicios_DAO.getInstance().actualizar_foto_servicio_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid(),mapaensuso);




                                /*
                                FirebaseDatabase database01 = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef01 = database01.getReference();
                                Query query01 = myRef01.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fotodeperfilurlvieja");
                                query01.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        myRef01.setValue("nuevaurldeperfil.jpg");
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });   */

                                // Servicios_DAO.getInstance().actualizar_foto_servicio_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid(),mapaensuso);
                                //con servicios solo logre crar un nuevo servicio pero no funciona necesito es que sobreesciba el methodo




                                //Servicios_DAO.getInstance().actualizar_foto_servicio_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid(),servicios);
                               startActivity(new Intent(Usuario_Informacion.this, Navegador.class));


                                finish(); //con esto cada vez que termine se cierra y retorna al login


                            }
                        });


                    } else {

                        Toast.makeText(Usuario_Informacion.this, "registro correctamente", Toast.LENGTH_LONG).show();
                        Usuario usuario =new Usuario();

                        usuario.setFotodePerfilURI(Constantes.URL_foto_por_defecto_usuario);
                        usuario.setUsuario_nombre(nombre_uso_de_firebase);
                        usuario.setTeleono_usuario(telefono_actual);
                        usuario.setProfesion(profesion);
                        usuario.setHabilidad_profesional(descripcion_usuario);

                        Usuario_DAO.getInstance().actualizar_info_usuario_y_foto(FirebaseAuth.getInstance().getCurrentUser().getUid(),usuario);


                        startActivity(new Intent(Usuario_Informacion.this, Navegador.class));

                        finish(); //con esto cada vez que termine se cierra y retorna al login



                    }



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
            foto_de_perfilURI_enviar=uri;//uri
            FirebaseStorage storage_foto_cliente=FirebaseStorage.getInstance();
            StorageReference   storage_Reference_cliente=storage_foto_cliente.getReference("fotos_perfil_USuario").child(Usuario_DAO.getInstance().getKeyUsuario()); //nombre de la carpeta en firebase que almacenara todas las imagenes

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

                        foto_perfil_string_URI_enviada=downloadUrl.toString();

                    }
                }
            });


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                foto_usuario_perfil.setImageBitmap(bitmap);
                //ImageView imageView = findViewById(R.id.imageView2);
                //  imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //validar si seleccionamos una foto de la galeria , nota podemos ahcerlo como en el viedeo o usando un archivo aparte

        }
    }

    @Override
    public void onResume() { //aqui recuperamos los datos de firebase
        super.onResume();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //  enviar.setEnabled(false); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

        if (currentUser!=null){
            // enviar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

            FirebaseDatabase database=FirebaseDatabase.getInstance();
            reference_data=FirebaseDatabase.getInstance().getReference(Constantes.Nodo_Usuario).child(firebaseUser.getUid()); //aqui trabajamos con el uid d no se si es el mismo Usuario_DAO.getInstance().getKeyUsuario();
            //DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
            //con el anterior referencia podemos reclamar el nombre y el correo de firebase de el path usuarios
            FirebaseStorage storage_usu_foto=FirebaseStorage.getInstance();
            //StorageReference storageReference2=storage_usu_foto.getReference("Foto_Ususarios").child(Usuario_DAO.getInstance().getKeyUsuario());
           // StorageReference storageReference3=storage_usu_foto.getReference("fotos_perfil_USuario").child(firebaseUser.getUid());




            reference_data.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos

                    //String value = dataSnapshot.getValue(String.class);
                    Usuario usu=dataSnapshot.getValue(Usuario.class);//pone la referencia
                   /*if (usu.getFotodePerfilURI().equals("defaul")){


                      foto_usuario_perfil.setImageResource(R.mipmap.ic_perfil);
                    }else {

                        Glide.with(Usuario_Informacion.this).load(usu.getFotodePerfilURI()).into(foto_usuario_perfil);

                    }*/
                    String usu2=usu.getUsuario_nombre();
                    String foto_ussss=usu.getFotodePerfilURI();
                    //FirebaseUser currentUser = mAuth.getCurrentUser();


                    nombre_uso_de_firebase=usu.getUsuario_nombre();
                    nombreusuario_texview.setText(nombre_uso_de_firebase);

                    profesion_seleccionada=usu.getProfesion();


                    telefono_usuario.setText(usu.getTeleono_usuario());
                    descipcion_trabajo.setText(usu.getHabilidad_profesional());

                   // Glide.with(Usuario_Informacion.this).load(usu.getFotodePerfilURI()).into(foto_usuario_perfil);
                   // Glide.with(getApplicationContext()).load(usu.getFotodePerfilURI()).into(foto_usuario_perfil);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            //returnLogin();
        }

    }



    public boolean valida_telefono(String telefono){
        return  !telefono.isEmpty();
    }
    public boolean valida_descripcion_usuario(String descripcion_usaurio){
        return  !descripcion_usaurio.isEmpty();
    }


}
