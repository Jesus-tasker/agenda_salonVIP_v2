package com.example.agenda_salonvip.persistencias_fire;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_fire.Mensaje;
import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_logicas.LServicio;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Servicios_DAO {
    public interface  IDdeviolverServicio{
//en las interface se pone el tipo de dato que va a infresar que es vdevolverusuario

        public void devolverUsuario(LServicio lServicio); //usuario d emensajeria_Activity //nota2 en as interfaces el dato que se retorna es este
        public void debolvererror(String eror); //puede surgir que se valla el internet asi rtornaremos en caso de error


    }
    public  String getKeyservicio(){//solo obtiene el uid del la autenticacion del susuario

        return FirebaseAuth.getInstance().getUid();
    }

    //nota ar alas interfaces se recomienda que inicien conID ya que son los puntos de conexion , se usa en subirfotoUri() mas abajo escrito
    public interface IDdevolverURLfoto{

        //aqui ira el methodo que conecta  nuestra url de la foto de perfil con nuestro activity registro
        public void urlfoto(String url);
    }

    private static Servicios_DAO servicios_dao;

    private FirebaseDatabase database;
    private FirebaseDatabase database_para_producto;

    private DatabaseReference databaseReference_servicios;
    private DatabaseReference databaseReference_general;

    private FirebaseStorage storage_servicio; //usado para enviar imagenes
    private StorageReference storageReference_servicio_foto_producto; //usado para enviar imagenes


    public static Servicios_DAO getInstance(){


        if(servicios_dao==null) servicios_dao= new Servicios_DAO();
        return servicios_dao;

    }
    private Servicios_DAO(){ //inicia la estancia de firebase

        database=FirebaseDatabase.getInstance();
        database_para_producto=FirebaseDatabase.getInstance();



        storage_servicio=FirebaseStorage.getInstance();
        storageReference_servicio_foto_producto=storage_servicio.getReference("foto__servicio"); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos

        databaseReference_servicios=database.getReference(Constantes.Nodo_servicios);
        databaseReference_general=database_para_producto.getReference(Constantes.Nodo_venta_general);


        //reference_perfil_foto=storage_fotos_de_Oerfuk.getReference("Fotos/foto_perdiles"+getKeyUsuario()); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos
        //referenceUsuariosss=database_f.getReference(Constantes.Nodo_Usuario);//el nodo esta en constantedes java alli puse el codigo


    }

    public  String getKeycliente(){

        return FirebaseAuth.getInstance().getUid();
    }


    public void subirfotoUri(final Uri uri, final Servicios_DAO.IDdevolverURLfoto iDdevolverURLfoto){


        String nombre_defoto="";
        Date date=new Date(); //agarra la fecha del telefono
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("$$$.ss-mm-hh-dd-MM-YYYY", Locale.getDefault()); //este codigo me almacenara las imagenes por su fecha, em este momento lo trabajo para foto de perfil
        nombre_defoto=simpleDateFormat.format(date); //agrega a los valores de la foto la fecha de creacion de la imagen
        final StorageReference foto_referencia=storageReference_servicio_foto_producto.child(nombre_defoto); //esste obtiene la llave de nuestra foto en este caso U para que todas las fotos sean diferentes a las demas
        //

        //el siguientee codigo indica que si se selecciono una foto y se selecciono correctamenet se envie a la nuve y se obtenga su uri

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

                    Uri uri1=task.getResult(); //eSTE ES EL URL DE LA FOTO DE PERFIL
                    //  Toast.makeText(Mensajeria.this, "Subida exitosamente", Toast.LENGTH_SHORT).show();
                    iDdevolverURLfoto.urlfoto(uri.toString());//aqui almacenamos el url
                }
            }
        });
    }


    //el siguiente envia el mensaje a ambos emisor y recetro
    public void servicio_ofrece(String key_emisor, Servicios servicios){
        //la idea es crear el fragmento y qeu se vea en una lisa personal y una lista para que todos la ven  cuando es creada

        DatabaseReference referenceEmisor=databaseReference_servicios.child(key_emisor); //nodo servicios

        StorageReference storageReference_servicio_foto= storageReference_servicio_foto_producto.child(key_emisor); //probando almacenar en esta ruta

       // StorageReference referenceFotocliente=reference_perfil_foto.child(key_emisor);

        //crea un nodo con el valor key  para los mensajes podre usarlo para una lista en general mia y de otros usuarios
        DatabaseReference reference_paratodo=databaseReference_general;
        referenceEmisor.push().setValue(servicios);
        reference_paratodo.setValue(servicios);



    }

    public void actualizar_foto_servicio_usuario(String key_emisor, final Servicios servicios){
        //la idea es crear el fragmento y qeu se vea en una lisa personal y una lista para que todos la ven  cuando es creada


        final DatabaseReference reference_paratodo=databaseReference_general.child(key_emisor);
       Query query01 = reference_paratodo.child(key_emisor);
        /*
        Query query01 = myRef01.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fotodeperfilurlvieja");
        query01.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef01.setValue("nuevaurldeperfil.jpg");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        reference_paratodo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // myRef01.setValue("nuevaurldeperfil.jpg");




                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    // aqui uso  un bucle for entre Usuariouid/ ser1,ser2,ser3

                  //  reference_paratodo.setValue(servicios);
                    // myRef01.setValue("nuevaurldeperfil.jpg");

                    //reference_paratodo.setValue(servicios);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

      reference_paratodo.setValue(servicios);
    }


    public void  obtenerinformacionDeUsuarioporLLaveUID (final String key, final Servicios_DAO.IDdeviolverServicio iDdeviolverServicio){ //se usa en mensajeria.java para obetener la infor del ususario en firebase

        //este obtiene el nodo de usaruarios y accedemos a al su usuraio hijo con al key
        //adquiere informacion del nodo de l hijo y toma esa info
        databaseReference_servicios.child(key).addListenerForSingleValueEvent(new ValueEventListener() { //vamos al hijo osea los que esta dentro de lso nodods
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //sse ontubo la informacion
                //hasta a qui dice ya obtube la informacion ahora quiero regresarte el usuario
                //ddat snap shop es para usar objetos de base de usuario

                Servicios serviciosdata=dataSnapshot.getValue(Servicios.class);

               // Clientes clientedata=dataSnapshot.getValue(Clientes.class); //obtiene el valor snapshap,es un objeto de base de datos

                LServicio lServicio=new LServicio(key,serviciosdata);

               // Lclientes lclientesd=new  Lclientes(key,clientedata);

                iDdeviolverServicio.devolverUsuario(lServicio);



                // lUsuario.setUsuarioss();
                // usuariosuario.getUsuario_nombre();
                //String n= usuariosuario.getUsuario_nombre();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error ejemplos e callo el internet
                iDdeviolverServicio.debolvererror(databaseError.getMessage());

            }
        });
    }

}
