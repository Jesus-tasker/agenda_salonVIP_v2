package com.example.agenda_salonvip.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Lista_clientes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter adapter_recicler_clientes;
    LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        recyclerView=findViewById(R.id.rv_clientes);



        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // rvUsuarios.setAdapter(adapter);


        Query query= FirebaseDatabase.getInstance()
                .getReference()
                .child(Constantes.Nodo_clientes).limitToLast(10); //el nomrbe d ela base de datos usuarios
        //  .limitToLast(70); //el limite d ela lista que acepta


        FirebaseRecyclerOptions<Clientes> options =
                new FirebaseRecyclerOptions.Builder<Clientes>()
                        .setQuery(query, Clientes.class) //carga una busqueda tipo query del nodo "Usuario"
                        .build();

        //databaseReference.addChildEventListener

        adapter_recicler_clientes= new FirebaseRecyclerAdapter<Clientes, Clientes_View_holder>(options) {//carga los datos


            @Override
            protected void onBindViewHolder(Clientes_View_holder holder, int position, Clientes model) {

                Glide.with(Lista_clientes.this).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //carga foto de perfil de los usualios a la lista
                holder.getNombre_cliente_item().setText(model.getUsuario_cliente()); //obtiene el nombre
                holder.getTelefono().setText(model.getTelefono_cliente());


            /*
                //para obtener la key que se usa en el toast y obtener el nombre del persona qeu tocamos click
                //convertimos USUArio
               final LUsuario lUsuario=new LUsuario(getSnapshots().getSnapshot(position).getKey(),model); //


                 holder.getLinearLayout_item_usuario_list().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Ver_usuarios_lista_para_chatear.this,"" +lUsuario.getKey(),Toast.LENGTH_SHORT ).show();
                    }
                });*/
            }
            @Override
            public Clientes_View_holder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Crear una nueva instancia de la ViewHolder, en este caso estamos usando una costumbre
                // diseño denominado R .layout.message para cada elemento
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lista_clientes, parent, false);

                return new Clientes_View_holder(view);
            }

        };


        recyclerView.setAdapter(adapter_recicler_clientes);

    }



    @Override
    protected void onStart() { //cuando salgamos d ela aplicacion y entremos, se cargeue el adaptador
        super.onStart();
        adapter_recicler_clientes.startListening();//adaotador inicia

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_recicler_clientes.stopListening(); //adaptador se detienen
    }

}
