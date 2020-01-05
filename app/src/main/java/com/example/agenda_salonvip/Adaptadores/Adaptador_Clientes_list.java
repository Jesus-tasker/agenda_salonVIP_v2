package com.example.agenda_salonvip.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Actividades.Lista_clientes;
import com.example.agenda_salonvip.Entidades_fire.Clientes;
import com.example.agenda_salonvip.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador_Clientes_list extends RecyclerView.Adapter <Adaptador_Clientes_list.listaprueba2_USU>{

    List<Clientes> clientesList;

    public Adaptador_Clientes_list(List<Clientes> clientesList1) {
        this.clientesList = clientesList1;
    }

    @NonNull
    @Override
    public listaprueba2_USU onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario_list,parent,false);

        listaprueba2_USU holder_lista=new listaprueba2_USU(v);
        return  holder_lista;
    }

    @Override
    public void onBindViewHolder(@NonNull listaprueba2_USU holder, int position) { //aqui es donde hacemos lo que queramos con el recicle view qu queremos tomar y poner datos

        Clientes clientes=clientesList.get(position);
        //  Glide.with()
       // Picasso.with(context).load(model.getFotodePerfilURI()).into(viewHolder.getFoto_perfil_item_cliente());

        // Glide.with(Lista_clientes.class).load(usuarioeUsu.getFotodePerfilURI()).into(holder.foto_perfil_usuario_de_lista); //carga foto de perfil de los usualios a la lista
        holder.nombre_usuario_de_listas.setText(clientes.getUsuario_cliente());

        //  Glide.with(Ver_usuarios_lista_para_chatear.this).load(model.getFotodePerfilURI()).into(holder.getFoto_perfil_usuario_de_lista()); //carga foto de perfil de los usualios a la lista
        //holder.getNombre_usuario_de_listas().setText(model.getUsuario_nombre()); //obtiene el nombre
        // Toast.makeText(Ver_usuarios_lista_para_chatear.this,"carga onview holder" ,Toast.LENGTH_SHORT ).show();


    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }

    public static class listaprueba2_USU extends RecyclerView.ViewHolder{

        private CircleImageView foto_perfil_usuario_de_lista;
        private TextView nombre_usuario_de_listas;
        // private LinearLayout linearLayout_item_usuario_list;

        public listaprueba2_USU(@NonNull View itemView) { //especifica qeu campos seran cambbiados conforme la informacion que necestamos usar
            super(itemView);
            foto_perfil_usuario_de_lista=(CircleImageView) itemView.findViewById(R.id.foto_perfil_item_Cliente);
            nombre_usuario_de_listas=(TextView)itemView.findViewById(R.id.nom_item_C);
            //linearLayout_item_usuario_list=itemView.findViewById(R.id.lauout_principal_usuariosList);

        }
    }


}
