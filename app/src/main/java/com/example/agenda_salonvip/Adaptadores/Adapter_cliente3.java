package com.example.agenda_salonvip.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_logicas.Lclientes;
import com.example.agenda_salonvip.Holder.Clientes_View_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.firebase.database.core.view.View;

import java.util.ArrayList;
import java.util.List;

public class Adapter_cliente3 extends RecyclerView.Adapter<Clientes_View_holder> {

    //consejo 5 una vez terminado el constructor de mensajes
    private List<Lclientes> clientes_list=new ArrayList<>();
    private Context c;

    public Adapter_cliente3(Context c) {
        //this.mensajeList = mensajeList; en este caso lo quitamos por que queremos que lleve una lista anda mas
        this.c = c;
    }


    //nota 6 una vez creado el adapter creamos el emnsaje
    public int AddMenssnaje(Lclientes n){ //lleva el objeto que guarda la lista

       clientes_list.add(n);
//consejo 10 cuando ya este realizado el adaptador en main activity que recibe el recicle view las entradas debemos notificar
        //3 menajes

        int posicion=clientes_list.size()-1;


        //notificaque recinbio paramteros
        notifyItemInserted(clientes_list.size());//obtiene la poicion de la lista  ya que el ultimo Mensaje es el qeu se inserta
        return posicion;
    }




    public  void  actualizarmensajee(int posicion,Lclientes lclientes){ //usado en mensajeria
        //este methodo actualizara el objeto en la pocicion
        clientes_list.set(posicion,lclientes); //actualizar mensaje en esa posicion
        // notifyDataSetChanged(); //este accion hace que las anteriores se carguen a todos los usuarios
        //queremos que se actualice el mensaje asi que lo seleccionamos
        notifyItemChanged(posicion); //actualiza el mensaje ej500

    }

    @NonNull
    @Override
    public Clientes_View_holder onCreateViewHolder(ViewGroup parent, int viewType) {

       android.view.View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clientes,parent,false);
        return new Clientes_View_holder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Clientes_View_holder holder, int position) { //este se encarga del recibimiento

        Lclientes lclientes=clientes_list.get(position);
        Glide.with(c).load(lclientes.getClientes().getFotodePerfilURI()).into(holder.getFoto_perfil_item_cliente()); //obtiene la ubicacion uri de la foto que se envio en el Mensaje
        holder.getNombre_cliente_item().setText(clientes_list.get(position).getClientes().getUsuario_cliente());
        holder.getTelefono().setText(clientes_list.get(position).getClientes().getTelefono_cliente());







    }

    @Override
    public int getItemCount()  { //taaño d ela lista del chat
        return clientes_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (clientes_list.get(position).getClientes() != null) { //si mesnaje list pregunta si fuimos nosootros quien enviamos el mensaje , de la lista que carga los mensajes

            //primero preguntamos si el mensaje es de nosotros
            if (clientes_list.get(position).getClientes().getKey_cita().equals(Usuario_DAO.getInstance().getKeyUsuario())) {
                return 1;

            } else {
                return -1;
            }
            //return super.getItemViewType(position);


        }else {

            return -1;
        }

    }
}
