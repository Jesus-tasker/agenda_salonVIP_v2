package fragments_navegador;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Cliente_RTDB;
import com.example.agenda_salonvip.Actividades.Crear_cita;
import com.example.agenda_salonvip.Actividades.Lista_clientes;
import com.example.agenda_salonvip.Actividades.Lista_clientes2;
import com.example.agenda_salonvip.Actividades.Mensajeria1;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.cita_cliente_exitente;
import com.example.agenda_salonvip.interfaz2;
import com.example.agenda_salonvip.persistencias_fire.Clientes_DF;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class clientes_inicioFragment extends Fragment {

    RecyclerView recyclerView_clientes;
    View vista_btoton_agregar; //esta vista es el qeu me permitira accionar el boton en el fragment
    ImageView agregar_cliente_frag,agregar_cita_frag,revisar_clientes_listaa;


    DatabaseReference databaseReference_clientes;

    FirebaseRecyclerOptions<Clientes> options;
    FirebaseRecyclerAdapter<Clientes, Clientes_View_holder> adapter_clients;
    public clientes_inicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View visra=inflater.inflate(R.layout.fragment_clientes_inicio, container, false);

        recyclerView_clientes=visra.findViewById(R.id.recicler_lista_clientes); //referencia del recicler view
       // recyclerView_clientes.setLayoutManager(new LinearLayoutManager(getContext()));
        agregar_cliente_frag=(ImageView)visra.findViewById(R.id.nuevo_cliente_fragment); //asi atrapa vista los fragmentos para reconocer el objeto

        Usuario_DAO.getInstance().getKeyUsuario();

        databaseReference_clientes= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_clientes).child(Clientes_DF.getInstance().getKeycliente().trim()); //referencia del nodo clientes

        options=new FirebaseRecyclerOptions.Builder<Clientes>().setQuery(databaseReference_clientes,Clientes.class).build();




        adapter_clients=new FirebaseRecyclerAdapter<Clientes, Clientes_View_holder>(options) {





            @Override
            protected void onBindViewHolder(@NonNull final Clientes_View_holder holder, final int position, @NonNull final Clientes model) {




                Glide.with(getActivity().getApplicationContext()).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente());
               // Glide.with(clientes_inicioFragment).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //carga foto de perfil de los usualios a la lista
                holder.getNombre_cliente_item().setText(model.getUsuario_cliente()); //obtiene el nombre
                holder.getTelefono().setText(model.getTelefono_cliente());



                final Lclientes clientes=new Lclientes(getSnapshots().getSnapshot(position).getKey(),model);

                holder.getBtn_cita_nueva().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       String foto= model.getFotodePerfilURI();
                        String nomm=model.getUsuario_cliente();
                        String tell=model.getTelefono_cliente();

                        //Toast.makeText(getActivity(), "Cliente "+nomm+" telefono: "+tell+clientes.getKey(), Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(), "Cliente "+nomm+" telefono:"+tell, Toast.LENGTH_LONG).show();




                        Intent i=new Intent(getActivity(), Crear_cita.class);
                        i.putExtra("foto",foto);
                        i.putExtra("nombre",nomm);
                        i.putExtra("telefono",tell);
                        i.putExtra("key_receptor",clientes.getKey());
                        startActivity(i);






                       // create_nueva_cita();



                    }
                });

                //holder.getLayoutPosition()

                holder.getBtn_mensajeria().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String foto= model.getFotodePerfilURI();
                        String nomm=model.getUsuario_cliente();
                        String tell=model.getTelefono_cliente();
                        Intent i=new Intent(getActivity(), Mensajeria1.class);
                        i.putExtra("foto",foto);
                        i.putExtra("nombre",nomm);
                        i.putExtra("telefono",tell);
                        i.putExtra("key_receptor",clientes.getKey());

                        startActivity(i);

                       // Toast.makeText(getActivity(), "Cliente "+nomm+" telefono: "+tell+clientes.getKey(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Cliente "+nomm+" telefono:"+tell, Toast.LENGTH_LONG).show();


                    }
                });



            }

            @NonNull
            @Override
            public Clientes_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //aqui inflamos el lauout del item

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clientes,parent,false);
                return new Clientes_View_holder(view);


            }
        };



        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),1);
        recyclerView_clientes.setLayoutManager(gridLayoutManager);
        adapter_clients.startListening();
        recyclerView_clientes.setAdapter(adapter_clients);



        agregar_cliente_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), Cliente_RTDB.class);

                startActivity(i);

            }
        });


        /*
        agregar_cita_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Intent i=new Intent(getActivity(), cita_cliente_exitente.class);
                startActivity(i);

            }
        });

        revisar_clientes_listaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Intent i=new Intent(getActivity(), Lista_clientes2.class);
                startActivity(i);

            }
        }); */

        return visra;


    }


    public  void  create_nueva_cita(){



        Intent i=new Intent(getActivity(), Crear_cita.class);
            startActivity(i);


    }

    @Override
    public void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();
        if (adapter_clients!=null){

            adapter_clients.startListening();
        }
        // adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter_clients!=null){

            adapter_clients.stopListening();
            super.onStop();
        }
        //adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter_clients!=null){

            adapter_clients.startListening();
        }
    }





}
