package com.example.agenda_salonvip.Adaptadores;
/*
import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Lista_clientes;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class Adapter_clients extends FirebaseRecyclerAdapter<Clientes , Clientes_View_holder> {

    Context context;


    public Adapter_clients(Class<Clientes> modelClass, int modelLayout, Class<Clientes_View_holder> viewHolderClass, DatabaseReference ref, Context c)
    {


        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }


    @Override
    protected void populateViewHolder(Clientes_View_holder viewHolder, final Clientes model, int position) {

       //  Glide.with(Lista_clientes.this).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_usuario_de_lista()); //carga foto de perfil de los usualios a la lista
               Picasso.with(context).load(model.getFotodePerfilURI()).into(viewHolder.getFoto_perfil_item_cliente());

        viewHolder.getNombre_cliente_item().setText(model.getUsuario_cliente());
        viewHolder.getTelefono().setText(model.getTelefono_cliente());



        /*Picasso.with(context).load(model.getLogo()).into(viewHolder.imagen);
        viewHolder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"La version es: "+ String.valueOf(model.getVersion()),Toast.LENGTH_SHORT).show();
            }
        });


    }

}
            */