package com.example.agenda_salonvip.persistencias_fire;

import androidx.annotation.NonNull;

import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_logicas.Lcitas;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Citas_DAO {

    public interface  IDdeviolvercliente_cita{
//en las interface se pone el tipo de dato que va a infresar que es vdevolverusuario

        public void devolverUsuario(Lcitas lcitas); //usuario d emensajeria_Activity //nota2 en as interfaces el dato que se retorna es este
        public void debolvererror(String eror); //puede surgir que se valla el internet asi rtornaremos en caso de error


    }



    private static Citas_DAO citas_df;
    private FirebaseDatabase database_f;
    private DatabaseReference reference_clientes;

    private FirebaseStorage storage_fotos_de_clientes;
    private StorageReference reference_perfil_foto;

    public interface IDdevolverURLfoto{

        //aqui ira el methodo que conecta  nuestra url de la foto de perfil con nuestro activity registro
        public void urlfoto(String url);
    }

    public static Citas_DAO getInstance(){

        //si el objeto esta creado retornar su estancia

        if(citas_df==null)  citas_df= new Citas_DAO();
        return citas_df;

    }
    private Citas_DAO(){ //inicia la estancia de firebase

        database_f=FirebaseDatabase.getInstance();
        storage_fotos_de_clientes=FirebaseStorage.getInstance();
        reference_perfil_foto=storage_fotos_de_clientes.getReference("Fotos/foto__clientes"); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos
        reference_clientes=database_f.getReference(Constantes.Nodo_cias);//el nodo esta en constantedes java alli puse el codigo


    }

    public  String getKeycliente(){

        return FirebaseAuth.getInstance().getUid();
    }

    public Long fechadeCreacionCuenta(){ //fiirebase retorna eos valores de tipo long

        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp(); //esta es la ruta para acceder a datos directos en firebase

    }

    public  Long ultimaFechaConexion(){ //firebase retorna el valor de tipo Long

        return FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();

    }


    public void  obtenerinformacionDeUsuarioporLLaveUID (final String key, final Citas_DAO.IDdeviolvercliente_cita iDdeviolvercliente){ //se usa en mensajeria.java para obetener la infor del ususario en firebase

        //este obtiene el nodo de usaruarios y accedemos a al su usuraio hijo con al key
        //adquiere informacion del nodo de l hijo y toma esa info
        reference_clientes.child(key).addListenerForSingleValueEvent(new ValueEventListener() { //vamos al hijo osea los que esta dentro de lso nodods
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //sse ontubo la informacion
                //hasta a qui dice ya obtube la informacion ahora quiero regresarte el usuario
                //ddat snap shop es para usar objetos de base de usuario

                CitaEnt  clientedata_cita=dataSnapshot.getValue(CitaEnt.class); //obtiene el valor snapshap,es un objeto de base de datos

               // Lcitas lcitas=new Lcitas(key,clientedata_cita);
              //  Lcitas lcitas1=new Lcitas(key,clientedata_cita)


                //iDdeviolvercliente.devolverUsuario(lcitas);



                // lUsuario.setUsuarioss();
                // usuariosuario.getUsuario_nombre();
                //String n= usuariosuario.getUsuario_nombre();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error ejemplos e callo el internet
                iDdeviolvercliente.debolvererror(databaseError.getMessage());

            }
        });
    }
    public void cita_nuevo(String key_emisor, CitaEnt clientes){
        //la idea es crear el fragmento y qeu se vea en una lisa personal y una lista para que todos la ven  cuando es creada

        DatabaseReference referenceEmisor=reference_clientes.child(key_emisor); //nodo clintes con llave uid
        StorageReference referenceFotocliente=reference_perfil_foto.child(key_emisor);

        // StorageReference storageReference_servicio_foto_producto=storage_servicio.getReference("Fotos/foto__servicio/"+key_emisor); //probando almacenar en esta ruta


        //crea un nodo con el valor key  para los mensajes podre usarlo para una lista en general mia y de otros usuarios
        // DatabaseReference reference_paratodo=databaseReference_general.child(key_emisor);
        referenceEmisor.push().setValue(clientes);
        // reference_paratodo.push().setValue(servicios);



    }
}
