package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Lista_clientes2 extends AppCompatActivity {

    RecyclerView recyclerView_clientes;
    DatabaseReference databaseReference_clientes;

    FirebaseRecyclerOptions<Clientes> options;
    FirebaseRecyclerAdapter<Clientes, Clientes_View_holder> adapter_clients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes2);

        recyclerView_clientes=findViewById(R.id.clientes_rv);

        databaseReference_clientes= FirebaseDatabase.getInstance().getReference().child(Constantes.Nodo_clientes); //referencia del nodo clientes

        options=new FirebaseRecyclerOptions.Builder<Clientes>().setQuery(databaseReference_clientes,Clientes.class).build();




        adapter_clients=new FirebaseRecyclerAdapter<Clientes, Clientes_View_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Clientes_View_holder holder, int position, @NonNull Clientes model) {



                Glide.with(Lista_clientes2.this).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //carga foto de perfil de los usualios a la lista
               holder.getTelefono().setText(model.getTelefono_cliente());

            }

            @NonNull
            @Override
            public Clientes_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //aqui inflamos el lauout del item

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clientes,parent,false);
                return new Clientes_View_holder(view);


            }
        };

        GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),2);
        recyclerView_clientes.setLayoutManager(gridLayoutManager);
        adapter_clients.startListening();
        recyclerView_clientes.setAdapter(adapter_clients);





    }

    @Override
    protected void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();
        if (adapter_clients!=null){

            adapter_clients.startListening();
        }
        // adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter_clients!=null){

            adapter_clients.stopListening();
            super.onStop();
        }
        //adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter_clients!=null){

            adapter_clients.startListening();
        }
    }
}
