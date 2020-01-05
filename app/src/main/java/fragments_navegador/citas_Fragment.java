package fragments_navegador;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Crear_cita;
import com.example.agenda_salonvip.Entidades_fire.CitaEnt;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Holder.Citas_view_holder;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Citas_DAO;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class citas_Fragment extends Fragment {




    RecyclerView recyclerView_cita_fragment;
    DatabaseReference databaseReference_citas;

    FirebaseRecyclerOptions<CitaEnt> options;
    FirebaseRecyclerAdapter<CitaEnt, Citas_view_holder> adapter_cita;

    public citas_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View visra =inflater.inflate(R.layout.fragment_citas_, container, false);

        recyclerView_cita_fragment=visra.findViewById(R.id.recicler_lista_citas_clientes); //referencia del recicler view

        databaseReference_citas= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_cias).child(Citas_DAO.getInstance().getKeycliente().trim()); //referencia del nodo clientes

        options=new FirebaseRecyclerOptions.Builder<CitaEnt>().setQuery(databaseReference_citas,CitaEnt.class).build();


        adapter_cita=new FirebaseRecyclerAdapter<CitaEnt, Citas_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Citas_view_holder holder, int position, @NonNull CitaEnt model) {


               Glide.with(getActivity().getApplicationContext()).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cita());
                // Glide.with(clientes_inicioFragment).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //carga foto de perfil de los usualios a la lista

                holder.getNombre_cliente_item_cita().setText(model.getNombre_cliente_cita()); //obtiene el nombre
                holder.getTelefono().setText(model.getTelefono_cliente_cita());
                holder.getServicio().setText(model.getServicio());
                holder.getDuracion_cita().setText(model.getTiempo_proceso());
               // holder.getFecha().setText(model.getFecha_de_cita());
              //  String strLong = Long.toString(model.getFecha_de_cita());
               //holder.getFecha().setText(strLong);










            }

            @NonNull
            @Override
            public Citas_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_citas_list,parent,false);
                return new Citas_view_holder(view);

            }
        };
        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),1);
        recyclerView_cita_fragment.setLayoutManager(gridLayoutManager);
        adapter_cita.startListening();
        recyclerView_cita_fragment.setAdapter(adapter_cita);



        return visra;

    }

    @Override
    public void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();
        if (adapter_cita!=null){

            adapter_cita.startListening();
        }
        // adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter_cita!=null){

            adapter_cita.stopListening();
            super.onStop();
        }
        //adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter_cita!=null){

            adapter_cita.startListening();
        }
    }


}

