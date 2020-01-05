package com.example.agenda_salonvip.persistencias_fire;

import com.example.agenda_salonvip.Entidades_fire.Mensaje;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Mensajeria_DAO {
    private static Mensajeria_DAO mensajeria_dao;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference_mensajeria;
    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes


    public static Mensajeria_DAO getInstance(){


        if(mensajeria_dao==null) mensajeria_dao= new Mensajeria_DAO();
        return mensajeria_dao;
      /*  if (usuario_dao==null){ //si el usuario no ha creado su etancia

            usuario_dao=new Usuario_DAO();
            return usuario_dao;
        }

        return getInstance(); */
    }
    private Mensajeria_DAO(){ //inicia la estancia de firebase

        database=FirebaseDatabase.getInstance();
        databaseReference_mensajeria=database.getReference(Constantes.Nodo_chat);
       // storage_fotos_de_Oerfuk= FirebaseStorage.getInstance();
        //reference_perfil_foto=storage_fotos_de_Oerfuk.getReference("Fotos/foto_perdiles"+getKeyUsuario()); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos
        //referenceUsuariosss=database_f.getReference(Constantes.Nodo_Usuario);//el nodo esta en constantedes java alli puse el codigo


    }
    //mensajeria
         //usuario1
             //usuario2
         //usuario2
            //usuario1


    //el siguiente envia el mensaje a ambos emisor y recetro
    public void  nuevomensaje(String key_emisor, String key_receptor, Mensaje mensaje){

        DatabaseReference referenceEmisore=databaseReference_mensajeria.child(key_emisor).child(key_receptor);
        DatabaseReference referenceReceptor=databaseReference_mensajeria.child(key_receptor).child(key_emisor);
        referenceEmisore.push().setValue(mensaje);
        referenceReceptor.push().setValue(mensaje);


    }

}
