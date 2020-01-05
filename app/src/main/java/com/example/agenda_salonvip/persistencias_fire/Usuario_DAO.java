package com.example.agenda_salonvip.persistencias_fire;

import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Usuario_DAO {

    public interface  IDdeviolverUsuario{
//en las interface se pone el tipo de dato que va a infresar que es vdevolverusuario

       public void devolverUsuario(LUsuario lUsuario); //usuario d emensajeria_Activity //nota2 en as interfaces el dato que se retorna es este
        public void debolvererror(String eror); //puede surgir que se valla el internet asi rtornaremos en caso de error


    }

    //nota ar alas interfaces se recomienda que inicien conID ya que son los puntos de conexion , se usa en subirfotoUri() mas abajo escrito
    public interface IDdevolverURLfoto{

        //aqui ira el methodo que conecta  nuestra url de la foto de perfil con nuestro activity registro
        public void urlfoto(String url);
    }

    private static  Usuario_DAO usuario_dao;
    private FirebaseDatabase database_f;
    private DatabaseReference referenceUsuariosss;

    private FirebaseStorage storage_fotos_de_Oerfuk;
    private StorageReference reference_perfil_foto;

    public static Usuario_DAO getInstance(){

        //si el objeto esta creado retornar su estancia

        if(usuario_dao==null) usuario_dao= new Usuario_DAO();
        return usuario_dao;
      /*  if (usuario_dao==null){ //si el usuario no ha creado su etancia

            usuario_dao=new Usuario_DAO();
            return usuario_dao;
        }

        return getInstance(); */
    }

    private Usuario_DAO(){ //inicia la estancia de firebase

        database_f=FirebaseDatabase.getInstance();
        storage_fotos_de_Oerfuk=FirebaseStorage.getInstance();
        reference_perfil_foto=storage_fotos_de_Oerfuk.getReference("fotos_perfil_USuario"); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos
        referenceUsuariosss=database_f.getReference(Constantes.Nodo_Usuario);//el nodo esta en constantedes java alli puse el codigo


    }
    public  boolean usuarioLogueado(){

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser!=null; //si el usuario ha entrado logueado retorna un verdadero
    }







    //  private FirebaseAuth firebaseAuth;//obtiiene el UAD del suuario en los nodods

    //con este metodo obtenemos el uad del suusario
    public  String getKeyUsuario(){

        return FirebaseAuth.getInstance().getUid();
    }

    //con el fin d eobtener el daato de firebase donde esta la informacion de autencicacion del usuario creamos este methodo que nos da  el dato de la fecha que se creo

    public Long fechadeCreacionCuenta(){ //fiirebase retorna eos valores de tipo long

        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp(); //esta es la ruta para acceder a datos directos en firebase

    }

    public  Long ultimaFechaConexion(){ //firebase retorna el valor de tipo Long

        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();

    }


    public void  obtenerinformacionDeUsuarioporLLaveUID (final String key, final IDdeviolverUsuario iDdeviolverUsuario){ //se usa en mensajeria.java para obetener la infor del ususario en firebase

        //este obtiene el nodo de usaruarios y accedemos a al su usuraio hijo con al key
        //adquiere informacion del nodo de l hijo y toma esa info
        referenceUsuariosss.child(key).addListenerForSingleValueEvent(new ValueEventListener() { //vamos al hijo osea los que esta dentro de lso nodods
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //sse ontubo la informacion
                //hasta a qui dice ya obtube la informacion ahora quiero regresarte el usuario
                //ddat snap shop es para usar objetos de base de usuario

                Usuario usuariosuario=dataSnapshot.getValue(Usuario.class); //obtiene el valor snapshap,es un objeto de base de datos



                LUsuario lUsuariodata=new LUsuario(key,usuariosuario); //este tiene la llave del usuario en turno

                iDdeviolverUsuario.devolverUsuario(lUsuariodata);

                // lUsuario.setUsuarioss();
                // usuariosuario.getUsuario_nombre();
                //String n= usuariosuario.getUsuario_nombre();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error ejemplos e callo el internet
                iDdeviolverUsuario.debolvererror(databaseError.getMessage());

            }
        });
    }



    public void subirfotoUri(final Uri uri, final IDdevolverURLfoto iDdevolverURLfoto){


        String nombre_defoto="";
        Date date=new Date(); //agarra la fecha del telefono
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("$$$.ss-mm-hh-dd-MM-YYYY", Locale.getDefault()); //este codigo me almacenara las imagenes por su fecha, em este momento lo trabajo para foto de perfil
        nombre_defoto=simpleDateFormat.format(date); //agrega a los valores de la foto la fecha de creacion de la imagen
        final StorageReference foto_referencia=reference_perfil_foto.child(nombre_defoto); //esste obtiene la llave de nuestra foto en este caso U para que todas las fotos sean diferentes a las demas
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


    public  void  aanadirFotodeRerfil_usuarioSinFoto(){ //aqui obtinene el nodo de firebase usuario donde esta la imagen
        //kklamamos al nodo usuario

        referenceUsuariosss.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //vamos a manipular los objetos entonces usamos logica de usuario
                List<LUsuario> lusuario_Lista= new ArrayList<>();
                for (DataSnapshot ChildDataSnapShop:dataSnapshot.getChildren()){  //con este for recorremos todos los dattos de los usuarios la taba de firebase
                    //obtiene todos los hijos de la variable y recupera la lista de los ussuarios
                    //aqui se recuperan los nodos y en lista
                    Usuario usuario=ChildDataSnapShop.getValue(Usuario.class);//aqui recuperamos la lista de usuarios
                    LUsuario lUsuario=new LUsuario(ChildDataSnapShop.getKey(),usuario);//ontiene todos los datos de los usuarios
                    lusuario_Lista.add(lUsuario);

                }

                for (LUsuario lUsua: lusuario_Lista){ //este for nos pone en la lista y mira si tienen foto de perfil o no y que hacer si no tiene

                    if (lUsua.getUsuarioss().getFotodePerfilURI()==null){ //aqui estaria leyendo nombre y correo y foto de perfil del usuario en firebase ya almacenados y creados
                        //aqui accede al nodo del usuario, su nodo como el id de cada uno

                        referenceUsuariosss.child(lUsua.getKey()).child("fotodePerfilURI").setValue(Constantes.URL_foto_por_defecto_usuario);
                        //con child ponemos la nueva indicacion de la foto de perfil  ////luego enviamos la foto de perfil qeu elegimos por defecto


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void actualizar_info_usuario_y_foto(String key_emisor, final Usuario usuario){
        //la idea es crear el fragmento y qeu se vea en una lisa personal y una lista para que todos la ven  cuando es creada


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference referenceEmisor=referenceUsuariosss; //nodo clintes con llave uid
       // DatabaseReference referecnciaventagene=database.getReference(Constantes.Nodo_venta_general);

        //StorageReference referenceFotocliente=reference_perfil_foto;

        // StorageReference storageReference_servicio_foto_producto=storage_servicio.getReference("Fotos/foto__servicio/"+key_emisor); //probando almacenar en esta ruta


        //crea un nodo con el valor key  para los mensajes podre usarlo para una lista en general mia y de otros usuarios
        // DatabaseReference reference_paratodo=databaseReference_general.child(key_emisor);


        referenceEmisor.child(key_emisor).setValue(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        usuario.getFotodePerfilURI();

                      //  Servicios_DAO.getInstance().actualizar_foto_servicio_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid(),);
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });


       // referenceEmisor.push().setValue(usuario);
        // reference_paratodo.push().setValue(servicios);



    }
    public void actualizar_info_servicio(String key_emisor, final Servicios servicios){
        //la idea es crear el fragmento y qeu se vea en una lisa personal y una lista para que todos la ven  cuando es creada


        FirebaseDatabase database=FirebaseDatabase.getInstance();

        DatabaseReference referencia_ventas_servicio=database.getReference(Constantes.Nodo_venta_general);


        //DatabaseReference referenceEmisor=referenceUsuariosss; //nodo clintes con llave uid


       referencia_ventas_servicio.child(key_emisor).setValue(servicios)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!


                        //  Servicios_DAO.getInstance().actualizar_foto_servicio_usuario(FirebaseAuth.getInstance().getCurrentUser().getUid(),);
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });


        // referenceEmisor.push().setValue(usuario);
        // reference_paratodo.push().setValue(servicios);



    }


}



