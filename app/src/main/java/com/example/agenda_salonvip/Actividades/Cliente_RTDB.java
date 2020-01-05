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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_fire.Mensaje;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Clientes_DF;
import com.example.agenda_salonvip.persistencias_fire.Mensajeria_DAO;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cliente_RTDB extends AppCompatActivity {

    CircleImageView foto_cliente;

    EditText nombre_cliente,correo_cliente,telefono_cliente;

    private FirebaseDatabase database;
    private DatabaseReference referenceCliente_tadabase;//Usuario firebase



    private  Long fechaCalendar;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri foto_de_perfilURI;
    private  String foto_perfil_string_URI;

    public ImageView fotocamera_perfil,guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_firebase__rtdb);

        foto_cliente=findViewById(R.id.foto_usu_client);
        nombre_cliente=findViewById(R.id.nombre_client);
        correo_cliente=findViewById(R.id.correo_cliente);
        telefono_cliente=findViewById(R.id.telefono_cliente);
        guardar=findViewById(R.id.guardar_cliente_firebasea);

         database = FirebaseDatabase.getInstance();
         final DatabaseReference myRef = database.getReference("Clientes");


        foto_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chooseImage();
            }
        });



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String corro_c = correo_cliente.getText().toString().trim();
                final String nombre_c=nombre_cliente.getText().toString().trim();
                final String telefono_c=telefono_cliente.getText().toString().trim();



                if (TextUtils.isEmpty(corro_c)){

                    Toast.makeText(Cliente_RTDB.this, "ingresar correo", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(nombre_c)){

                    Toast.makeText(Cliente_RTDB.this, "ingresar nombre", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(telefono_c)){

                    Toast.makeText(Cliente_RTDB.this, "ingresar telefono", Toast.LENGTH_LONG).show();

                }

                if (isValidEmail(corro_c)&& valida_nombre(nombre_c)&& valida_telefono(telefono_c)){


                    if (foto_de_perfilURI!=null){ //si el usuario no sube foto de perfil se cargara na por defecto
                        //aqui llamamos a la ubicacion  y conextion e tiempo real de el almacenamiento de la foto de perfil su URI se almacenrara

                        //aqui trabaja con el methodo uri como foto de perfil y trabaja con una interfaz interna para conectarse
                        Usuario_DAO.getInstance().subirfotoUri(foto_de_perfilURI, new Usuario_DAO.IDdevolverURLfoto() {
                            @Override
                            public void urlfoto(String url) {

                                Toast.makeText(Cliente_RTDB.this, "registro guardado", Toast.LENGTH_LONG).show();
                                Clientes clientes=new Clientes();
                                clientes.setUsuario_cliente(nombre_c);
                                clientes.setCorreo_clilente(corro_c);
                                clientes.setTelefono_cliente(telefono_c);
                                clientes.setFotodePerfilURI(foto_perfil_string_URI);


                               // Usuario_DAO.getInstance().getKeyUsuario(),cliebtes;
                              //  myRef.push().setValue(clientes);
                                Clientes_DF.getInstance().cliente_nuevo(Clientes_DF.getInstance().getKeycliente(),clientes);


                                //DatabaseReference reference=database.getReference("Clientes/");
                                //reference.setValue(clientes);
                                //referenceCliente_tadabase.push().setValue(clientes);
                               // startActivity(new Intent(Cliente_RTDB.this,Lista_clientes2.class));

                                finish(); //con esto cada vez que termine se cierra y retorna al login



                                // FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                               // DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id

                               // reference.setValue(usuario);
                                //referenceUsuario.push().setValue(usuario);


                               // startActivity(new Intent(Registro_Usuario.this, Login.class));
                                //finish(); //con esto cada vez que termine se cierra y retorna al login


                            }
                        });


                    } else {

                        Toast.makeText(Cliente_RTDB.this, "registro correctamente", Toast.LENGTH_LONG).show();
                        Clientes clientes1=new Clientes();
                        clientes1.setUsuario_cliente(nombre_c);
                        clientes1.setCorreo_clilente(corro_c);
                        clientes1.setTelefono_cliente(telefono_c);
                        clientes1.setFotodePerfilURI(Constantes.URL_foto_por_defecto_usuario);

                        Clientes_DF.getInstance().cliente_nuevo(Clientes_DF.getInstance().getKeycliente(),clientes1);



                        startActivity(new Intent(Cliente_RTDB.this,Lista_clientes2.class));

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
            foto_de_perfilURI=uri;
            FirebaseStorage storage_foto_cliente;
            StorageReference storage_Reference_cliente;
            storage_foto_cliente=FirebaseStorage.getInstance();
            storage_Reference_cliente=storage_foto_cliente.getReference("fotos_perfil_clientes").child(Usuario_DAO.getInstance().getKeyUsuario()); //nombre de la carpeta en firebase que almacenara todas las imagenes
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

                        foto_perfil_string_URI=downloadUrl.toString();

                    }
                }
            });


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                foto_cliente.setImageBitmap(bitmap);
                //ImageView imageView = findViewById(R.id.imageView2);
                //  imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //validar si seleccionamos una foto de la galeria , nota podemos ahcerlo como en el viedeo o usando un archivo aparte

        }
    }

    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }



    public boolean valida_nombre(String nombre){
        return  !nombre.isEmpty();
    }
    public boolean valida_correo(String correo){
        return  !correo.isEmpty();
    }
    public boolean valida_telefono(String telefono){
        return  !telefono.isEmpty();
    }


}




