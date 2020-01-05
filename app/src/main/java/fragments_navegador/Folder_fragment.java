package fragments_navegador;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Login_USU_fire;
import com.example.agenda_salonvip.Actividades.Servicios_ofrece;
import com.example.agenda_salonvip.Actividades.Usuario_Informacion;
import com.example.agenda_salonvip.Adaptadores.Usuario_adapter;
import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.Entidades_logicas.LServicio;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.example.agenda_salonvip.Holder.Servicios_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.interfaz2;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Folder_fragment extends Fragment {
     CircleImageView cerrar;
     CircleImageView foto_perfil_usuario;
     TextView nombre_usu,profesion_usu;

    RecyclerView recyclerView_servicios_personales;
    View vista;
   FirebaseAuth mAuth;
   ImageView nuevo_servicio;



    DatabaseReference databaseReference_servicios;

    Servicios servicios;
    FirebaseRecyclerOptions<Servicios> options_Servicio;
    FirebaseRecyclerAdapter<Servicios, Servicios_View_holder> adapter_services1;

    private FirebaseStorage storage; //usado para enviar imagenes
    private StorageReference storageReference; //usado para enviar imagenes
    private  static final  int foto_send=1; //valor requerido para enviar fotos un valor unico que no vambie
    private  static final int foto_de_perfil=2; //entero usado para validar la accion d ebusqueda =2

    private int PICK_IMAGE_REQUEST = 1;

    private Uri foto_de_perfilURI;

    private   String foto_perfil_URI2,nombre_uso_de_firebase,cargo_usu_firebase;


    //estos son los elementos del reciclerdatosd del usuario nombre y foto
    private RecyclerView recyclerView;
    private Usuario_adapter usuario_adapter;
    private List<Usuario> usuarioList;



    public Folder_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View vista=inflater.inflate(R.layout.fragment_folder_fragment, container, false); //aqui activamos la vista del fragmento

        recyclerView_servicios_personales=vista.findViewById(R.id.rec_lista_misservicios); //referencia del recicler view
        // recyclerView_clientes.setLayoutManager(new LinearLayoutManager(getContext()));


        cerrar=(CircleImageView)vista.findViewById(R.id.cerrar);
        foto_perfil_usuario=(CircleImageView)vista.findViewById(R.id.foto_perfil_perfil);
        nuevo_servicio=(ImageView)vista.findViewById(R.id.nueva_promo_fragment);
        nombre_usu=(TextView)vista.findViewById(R.id.nombre_perefil);
        profesion_usu=(TextView)vista.findViewById(R.id.cargo);


        mAuth=FirebaseAuth.getInstance();
     //  String ketu= Usuario_DAO.getInstance().getKeyUsuario(); //en este codigo esta la llave dle usuario en turno



        databaseReference_servicios= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_servicios).child(Usuario_DAO.getInstance().getKeyUsuario().trim()); //referencia del nodo clientes
// databaseReference_clientes= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_clientes); //referencia del nodo clientes
        options_Servicio=new FirebaseRecyclerOptions.Builder<Servicios>().setQuery(databaseReference_servicios,Servicios.class).build();

        foto_perfil_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), Usuario_Informacion.class);

                startActivity(i);


            }
        });


        /*
        foto_perfil_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para agregar y enviar imagenes primero creamos un intent
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);// intento de otener contenido sea fotos o videos
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"selecciona una foto"),foto_de_perfil); //este verifica lo qeu pones como resultado aunque podriamos leer ams core este me quedo dudas vistoa  lso 32.18 min del video



            }
        });*/


        //codigo para el adapter usuario
        recyclerView=vista.findViewById(R.id.rec_usuarioinfo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usuarioList=new ArrayList<>();
        read_users();




        adapter_services1=new FirebaseRecyclerAdapter<Servicios, Servicios_View_holder>(options_Servicio) {


            @Override
            protected void onBindViewHolder(@NonNull Servicios_View_holder holder, int position, @NonNull final  Servicios servicios_model) {

               // loadImage(Glide.with(this), url, findViewById(R.id.image));
               //list.setAdapter(new MyAdapter(Glide.with(this), data));
                //   Glide.with(getActivity().getApplicationContext()).load(servicios_model.getFotode_servicio_URI()).into(holder.getFoto_emisor());

                Glide.with(getContext()).load(foto_perfil_URI2).into(holder.getFoto_emisor());
                Glide.with(getContext()).load(servicios_model.getFotode_servicio_URI()).into(holder.getFoto_servicio());
                 // Glide.with(getActivity().getApplicationContext()).load(servicios_model.getFotode_servicio_URI()).into(holder.getFoto_servicio());
                    holder.getNombre_emisor().setText(servicios_model.getNombre_emisor());
                    holder.getPrecio_Servicio().setText(servicios_model.getPrecio());
                    holder.getUbicacion().setText(servicios_model.getUbicacion());
               //holder.getNombre_emisor().setText(model.getNombre_servicio());


                    // Glide.with(clientes_inicioFragment).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //carga foto de perfil de los usualios a la lista
               // holder.getNombre_cliente_item().setText(model.getNombre_servicio());






            }

            @NonNull
            @Override
            public Servicios_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicios,parent,false);
                return new Servicios_View_holder(view);
            }
        };

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),1);
        recyclerView_servicios_personales.setLayoutManager(gridLayoutManager);
        adapter_services1.startListening();
        recyclerView_servicios_personales.setAdapter(adapter_services1);

        nuevo_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), Servicios_ofrece.class);

                startActivity(i);

            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), Login_USU_fire.class);

                startActivity(i);

            }
        });



        return vista;

    }



    private void read_users() { //methodo usado para el recicler view de los datos del usuario, despues descrubri que trae realmente lista d elos usuarios reistrados
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() { //nota es addvalue
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioList.clear();

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Usuario usuario=dataSnapshot.getValue(Usuario.class);
                   assert  usuario!=null;
                   assert  firebaseUser!=null;
                   if (!firebaseUser.getUid().equals(Usuario_DAO.getInstance().getKeyUsuario())){
                       usuarioList.add(usuario);
                   }


                }

                usuario_adapter=new Usuario_adapter(getContext(),usuarioList);
                recyclerView.setAdapter(usuario_adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();





        if (adapter_services1!=null){

            adapter_services1.startListening();
        }
        // adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter_services1!=null){

            adapter_services1.stopListening();
            super.onStop();
        }
        //adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }
    @Override
    public void onResume() { //aqui recuperamos los datos de firebase
        super.onResume();
        if (adapter_services1 != null) {

            adapter_services1.startListening();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //  enviar.setEnabled(false); //esto dice que antes de reclamar los datos el boton debe estar inabilitado

        if (currentUser!=null){
            // enviar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid());

            //  DatabaseReference reference=database.getReference("Usuarios/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id
            //con el anterior referencia podemos reclamar el nombre y el correo de firebase de el path usuarios
            FirebaseStorage storage_usu_foto=FirebaseStorage.getInstance();
            //StorageReference storageReference2=storage_usu_foto.getReference("Foto_Ususarios").child(Usuario_DAO.getInstance().getKeyUsuario());
            StorageReference storageReference3=storage_usu_foto.getReference("fotos_perfil_USuario").child(Usuario_DAO.getInstance().getKeyUsuario());


            // reference_perfil_foto=storage_fotos_de_Oerfuk.getReference("Fotos/foto_perfil_usuario_registrados"+getKeyUsuario()); //aqui crea la carpeta con el id del ussuario correspondiente y almacena una ubicacion para las fotos

            // StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("storage ref url in string");





            reference.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos


                    //String value = dataSnapshot.getValue(String.class);
                    Usuario usu=dataSnapshot.getValue(Usuario.class);//pone la referencia

                    String usu2=usu.getUsuario_nombre();
                    String foto_ussss=usu.getFotodePerfilURI();
                    String profesion=usu.getProfesion();


                    nombre_uso_de_firebase=usu.getUsuario_nombre();
                    cargo_usu_firebase=profesion;
                    nombre_usu.setText(nombre_uso_de_firebase);
                    profesion_usu.setText(cargo_usu_firebase);
                    foto_perfil_URI2=usu.getFotodePerfilURI();




                    Glide.with(getContext()).load(foto_ussss).into(foto_perfil_usuario);



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








}
