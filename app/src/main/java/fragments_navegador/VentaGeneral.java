

package fragments_navegador;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Cliente_RTDB;
import com.example.agenda_salonvip.Actividades.Login_USU_fire;
import com.example.agenda_salonvip.Actividades.Mensajeria1;
import com.example.agenda_salonvip.Actividades.Servicios_ofrece;
import com.example.agenda_salonvip.Adaptadores.Usuario_adapter;
import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Servicios;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.Holder.Citas_view_holder;
import com.example.agenda_salonvip.Holder.Servicios_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Citas_DAO;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentaGeneral extends Fragment {


    RecyclerView recicler_ventaGeneral;


    DatabaseReference databaseReference_venta_generals;

    Servicios servicios;
    FirebaseRecyclerOptions<Servicios> options_Servicio;
    FirebaseRecyclerAdapter<Servicios, Servicios_View_holder> adapter_services2;
    DatabaseReference reference_data;

    String foto_usuario_daatabase;

    public VentaGeneral() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View visra =inflater.inflate(R.layout.fragment_mensajes, container, false);
        recicler_ventaGeneral=visra.findViewById(R.id.rec_lista_venta_general); //referencia del recicler view
        // recyclerView_clientes.setLayoutManager(new LinearLayoutManager(getContext()));
       // databaseReference_venta_generals= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_venta_general).child(Usuario_DAO.getInstance().getKeyUsuario().trim()); //referencia del nodo venta general
// databaseReference_clientes= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_clientes); //referencia del nodo clientes
            //Query query = cities.whereEqualTo("capital", true);

       databaseReference_venta_generals= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_venta_general);



        options_Servicio=new FirebaseRecyclerOptions.Builder<Servicios>().setQuery(databaseReference_venta_generals,Servicios.class).build();


        adapter_services2=new FirebaseRecyclerAdapter<Servicios, Servicios_View_holder>(options_Servicio) {


            @Override
            protected void onBindViewHolder(@NonNull Servicios_View_holder holder, int position, @NonNull final  Servicios servicios_model) {

                // loadImage(Glide.with(this), url, findViewById(R.id.image));
                //list.setAdapter(new MyAdapter(Glide.with(this), data));
                //   Glide.with(getActivity().getApplicationContext()).load(servicios_model.getFotode_servicio_URI()).into(holder.getFoto_emisor());

                Glide.with(getContext()).load(servicios_model.getFotodePerfilURI_Servicio()).into(holder.getFoto_emisor());
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
        recicler_ventaGeneral.setLayoutManager(gridLayoutManager);
        adapter_services2.startListening();
        recicler_ventaGeneral.setAdapter(adapter_services2);








        return visra;



    }


    @Override
    public void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();





        if (adapter_services2!=null){

            adapter_services2.startListening();
        }
        // adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter_services2!=null){

            adapter_services2.stopListening();
            super.onStop();
        }
        //adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }
    @Override
    public void onResume() { //aqui recuperamos los datos de firebase
        super.onResume();
        if (adapter_services2 != null) {

            adapter_services2.startListening();
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser!=null){
            // enviar.setEnabled(true); //esto dice que antes de reclamar los datos el boton debe estar inabilitado


            reference_data=FirebaseDatabase.getInstance().getReference(Constantes.Nodo_Usuario).child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //aqui trabajamos con el uid d no se si es el mismo Usuario_DAO.getInstance().getKeyUsuario();

            FirebaseStorage storage_usu_foto=FirebaseStorage.getInstance();




            reference_data.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //datasnappshop es el qeu recupera los datos


                    Usuario usu=dataSnapshot.getValue(Usuario.class);//pone la referencia

                    String usu2=usu.getUsuario_nombre();
                    String foto_ussss=usu.getFotodePerfilURI();
                    foto_usuario_daatabase=foto_ussss;
                    //FirebaseUser currentUser = mAuth.getCurrentUser();








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
