
package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro_USU_fire extends AppCompatActivity {

    private CircleImageView foto_de_perfil;
    private EditText crear_usuario;
    private EditText crear_correo;
    private EditText crerar_contrasena, contrasena2;
    private  EditText fecha_de_nacimiento;

    private RadioGroup radioGroupGenero;
    private RadioButton rdhombre,rdmujer;


    private Button registrar, atras_crear_cuetna;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference referenceUsuario;//Usuario firebase



    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes

    private  Long fechaCalendar;
    private int PICK_IMAGE_REQUEST = 1;
    private  static final int foto_de_perfil2=2; //entero usado para validar la accion d ebusqueda =2

    private Uri foto_de_perfilURI;

    public ImageView fotocamera_perfil;
    private String foto_perfil_string_URI;
    private String getFoto_perfil_string_URI_uso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usu_fire);



        foto_de_perfil=(CircleImageView)findViewById(R.id.foto_usu);

        crear_usuario = (EditText) findViewById(R.id.nombre_usuario);
        crear_correo = (EditText) findViewById(R.id.correo_usuario);
        crerar_contrasena = (EditText) findViewById(R.id.contraasena);
        contrasena2 = (EditText) findViewById(R.id.contraasena_repite);
        fecha_de_nacimiento=(EditText)findViewById(R.id.fecha_nacimiento);
        rdhombre=(RadioButton) findViewById(R.id.hombre);
        rdmujer=(RadioButton) findViewById(R.id.mujer);

        radioGroupGenero=findViewById(R.id.rdGenero); //el radio group d ehombre y mujer


        progressDialog=new ProgressDialog(this);//barra de progreso

        firebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        referenceUsuario=database.getReference(Constantes.Nodo_Usuario) ;//nodo principal donde almacena los usuarios

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference("Foto_perfil_usuarios");

        registrar = (Button) findViewById(R.id.guardar_cuenta_nueva);




        foto_de_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chooseImage();


                /* //este codigo es para elegir entre galeria y tomr foto
                AlertDialog.Builder builder_dialog=new AlertDialog.Builder(Registro_Usuario.this); //aqui creamos el alert dialog para elaegir entre la camara o tomar una foto
                builder_dialog.setTitle("Foto de Perfil");

                String[] items={"Galeria", "Camara" };

                builder_dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        //ahora debemos elegir el suceso dependiendo de la variable que s elija
                        switch (i){

                            case 0: //click en galeria
                                chooseImage();//ejecuta elmethodo para la galeria d ela camara
                                break;
                            case 1: //click en camara
                                shoosecamera();
                                break;
                        }

                    }
                });

                AlertDialog dialoConstruido=builder_dialog.create();
                dialoConstruido.show();  */
            }
        });


        fecha_de_nacimiento.setOnClickListener(new View.OnClickListener() { //usamos el eedit text pra que almacccene la informacion de tipo calendar
            //en la fecha se abre un dialoo donde podemos posner la fecha
            @Override
            public void onClick(View v) {

                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog datePickerDialog= new DatePickerDialog(Registro_USU_fire.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar can=Calendar.getInstance();
                        can.set(calendar.YEAR,year);
                        can.set(Calendar.MONTH,month);
                        can.set(calendar.DAY_OF_MONTH,dayOfMonth);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/YYYY");
                        Date date=can.getTime() ;

                        String fechaCalendarrrr=simpleDateFormat.format(date); //es string reune los datos
                        fechaCalendar=date.getTime(); //con eto obtenermos el archivo de topo long a strig

                        fecha_de_nacimiento.setText(fechaCalendarrrr);


                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String correo = crear_correo.getText().toString().trim();
                final String nombre=crear_usuario.getText().toString().trim();
                final String answ_1=contrasena2.getText().toString().trim();

                if (TextUtils.isEmpty(correo)){

                    Toast.makeText(Registro_USU_fire.this, "ingresar correo", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(nombre)){

                    Toast.makeText(Registro_USU_fire.this, "ingresar nombre", Toast.LENGTH_LONG).show();

                }
                if (TextUtils.isEmpty(answ_1)){

                    Toast.makeText(Registro_USU_fire.this, "ingresar contraseña", Toast.LENGTH_LONG).show();

                }

                if (isValidEmail(correo)&& validar_contrasena()&& valida_nombre(nombre)){

                    progressDialog.setMessage("realizando registro en linea... ");
                    progressDialog.show();



                    final   String contrasena = crerar_contrasena.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(correo, contrasena) //autentica correo y contrasena
                            .addOnCompleteListener(Registro_USU_fire.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        final  String genero;
                                        if (rdhombre.isChecked()){

                                            genero="hombre";
                                        }else  {

                                            genero="mujer";
                                        }

                                        /*
                                        Usuario usuario=new Usuario();
                                        usuario.setCorreo_usuario(correo);
                                        usuario.setUsuario_nombre(nombre);
                                        usuario.setFechaDeNacimiento( fechaCalendar);
                                        usuario.setGenero(genero);
                                        // usuario.setFotodePerfilURI(url);
                                        // usuario.setFotodePerfilURI(foto_perfil_string_URI);

                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        DatabaseReference reference=database.getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
                                        //StorageReference storageReference2=storage.getReference("Foto_perfil_usuarios/"+currentUser.getUid());


                                        referenceUsuario.push().setValue(usuario);
                                        //reference.setValue(usuario);
                                        //referenceUsuario.push().setValue(usuario);


                                        startActivity(new Intent(Registro_USU_fire.this, Usuario_Informacion.class));
                                        finish(); //con esto cada vez que termine se cierra y retorna al login
                                        */



                                        if (foto_de_perfilURI!=null){ //si el usuario no sube foto de perfil se cargara na por defecto
                                            //aqui llamamos a la ubicacion  y conextion e tiempo real de el almacenamiento de la foto de perfil su URI se almacenrara

                                            //aqui trabaja con el methodo uri como foto de perfil y trabaja con una interfaz interna para conectarse
                                            Usuario_DAO.getInstance().subirfotoUri(foto_de_perfilURI, new Usuario_DAO.IDdevolverURLfoto() {
                                                @Override
                                                public void urlfoto(String url) {



                                                    Toast.makeText(Registro_USU_fire.this, "registro correctamente", Toast.LENGTH_LONG).show();
                                                    Usuario usuario=new Usuario();
                                                    usuario.setCorreo_usuario(correo);
                                                    usuario.setUsuario_nombre(nombre);
                                                    usuario.setFechaDeNacimiento( fechaCalendar);
                                                    usuario.setGenero(genero);
                                                  // usuario.setFotodePerfilURI(url);
                                                   // usuario.setFotodePerfilURI(foto_perfil_string_URI);

                                                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                                    DatabaseReference reference=database.getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id


                                                  //  DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
                                                  // StorageReference storageReference2=storage.getReference("Foto_perfil_usuarios/"+currentUser.getUid());



                                                    reference.setValue(usuario);
                                                    referenceUsuario.push().setValue(usuario);


                                                    startActivity(new Intent(Registro_USU_fire.this, Usuario_Informacion.class));
                                                    finish(); //con esto cada vez que termine se cierra y retorna al login


                                                }
                                            });


                                        } else {

                                            Toast.makeText(Registro_USU_fire.this, "registro correctamente", Toast.LENGTH_LONG).show();
                                            Usuario usuario=new Usuario();
                                            usuario.setCorreo_usuario(correo);
                                            usuario.setUsuario_nombre(nombre);
                                            usuario.setFechaDeNacimiento( fechaCalendar);
                                            usuario.setGenero(genero);
                                           // usuario.setFotodePerfilURI(Constantes.URL_foto_por_defecto_usuario);

                                            referenceUsuario.push().setValue(usuario);

                                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                            DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id

                                            reference.setValue(usuario);
                                            referenceUsuario.push().setValue(usuario);



                                            finish(); //con esto cada vez que termine se cierra y retorna al login

                                        }



                                    } else {
                                        Toast.makeText(Registro_USU_fire.this, "error al registrarse", Toast.LENGTH_SHORT).show();


                                    }

                                }
                            });

                }else {
                    Toast.makeText(Registro_USU_fire.this, "la validacion del correo o nombre fallo", Toast.LENGTH_SHORT).show();
                }
                // String contrasena = crerar_contrasena.getText().toString();

                crear_usuario.getText().toString();
                crear_correo.getText().toString();


            }
        });

        //con esto cargaremos la foto de perfil por defecto
        Glide.with(this).load(Constantes.URL_foto_por_defecto_usuario).into(foto_de_perfil);





    }


    private void shoosecamera() { //usamos este llamado para la camara por ahora
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);


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



       if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {

            //imagepicked:submit(data);

            Uri uri = data.getData();
            foto_de_perfilURI=uri;
            foto_perfil_string_URI=foto_de_perfilURI.toString();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                foto_de_perfil.setImageBitmap(bitmap);
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

    public Boolean validar_contrasena () {

        String contra1, contrarepiteee;
        contra1 = crerar_contrasena.getText().toString();
        contrarepiteee = contrasena2.getText().toString();
        if (contra1.equals(contrarepiteee) && (contra1.length() >= 6 && contra1.length() <= 16)) {
            return  true;

        }else {return false;}


    }

    public boolean valida_nombre(String nombre){
        return  !nombre.isEmpty();
    }

}
