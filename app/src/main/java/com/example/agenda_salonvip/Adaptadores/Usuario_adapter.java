package com.example.agenda_salonvip.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Usuario_adapter extends RecyclerView.Adapter<Usuario_adapter.ViewHolder> {

    private Context context;
    private List<Usuario> usuarioList;


    public Usuario_adapter(Context context,List<Usuario> usuarioList){

        this.context=context;
        this.usuarioList=usuarioList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_usuario_informacion,parent,false);

        return new Usuario_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Usuario usuario=usuarioList.get(position);
        holder.username.setText(usuario.getUsuario_nombre());
        if (usuario.getFotodePerfilURI().equals("default")){

            holder.foto_usuario_perfil.setImageResource(R.mipmap.ic_perfil);
        }else {
            Glide.with(context).load(usuario.getFotodePerfilURI()).into(holder.foto_usuario_perfil);
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{ //aquu llamamos a los objetos de layoit que usaremos osea el deseño


        public TextView username;
        public CircleImageView foto_usuario_perfil;

        public ViewHolder(View itemview){

            super(itemview);
            username=itemview.findViewById(R.id.nombre_perfil_usuario);
            foto_usuario_perfil=itemview.findViewById(R.id.foto_perfil_usuario);
        }
    }






}
