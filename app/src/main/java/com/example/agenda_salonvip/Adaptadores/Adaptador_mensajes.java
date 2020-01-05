package com.example.agenda_salonvip.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agenda_salonvip.Entidades_logicas.LMensaje;
import com.example.agenda_salonvip.Entidades_logicas.LUsuario;
import com.example.agenda_salonvip.Holder.Mensajes_holder;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;

import java.util.ArrayList;
import java.util.List;

public class Adaptador_mensajes extends RecyclerView.Adapter<Mensajes_holder> {

    //consejo 5 una vez terminado el constructor de mensajes
    private List<LMensaje> mensajeList=new ArrayList<>();
    private Context c;

    public Adaptador_mensajes(Context c) {
        //this.mensajeList = mensajeList; en este caso lo quitamos por que queremos que lleve una lista anda mas
        this.c = c;
    }


    //nota 6 una vez creado el adapter creamos el emnsaje
    public int AddMenssnaje(LMensaje n){ //lleva el objeto que guarda la lista

        mensajeList.add(n);
//consejo 10 cuando ya este realizado el adaptador en main activity que recibe el recicle view las entradas debemos notificar
        //3 menajes

        int posicion=mensajeList.size()-1;


        //notificaque recinbio paramteros
        notifyItemInserted(mensajeList.size());//obtiene la poicion de la lista  ya que el ultimo Mensaje es el qeu se inserta
        return posicion;
    }




    public  void  actualizarmensajee(int posicion,LMensaje lMensajes){ //usado en mensajeria
        //este methodo actualizara el objeto en la pocicion
        mensajeList.set(posicion,lMensajes); //actualizar mensaje en esa posicion
        // notifyDataSetChanged(); //este accion hace que las anteriores se carguen a todos los usuarios
        //queremos que se actualice el mensaje asi que lo seleccionamos
        notifyItemChanged(posicion); //actualiza el mensaje ej500

    }

    @NonNull
    @Override
    public Mensajes_holder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==1){ //cuendo el mensahe es nuestro
            View vista_mensaje_emisor= LayoutInflater.from(c).inflate(R.layout.item_mensaje_emisor, parent, false);
            return new Mensajes_holder(vista_mensaje_emisor);//retorna la vista inflada del layout card view para generar la lsita ddel chat


        }else {
            //cuando la vista no es 1 es -1 , codigo en public int getItemViewType(
            View v = LayoutInflater.from(c).inflate(R.layout.item_mensaje_receptor, parent, false);
            return new Mensajes_holder(v);//retorna la vista inflada del layout card view para generar la lsita ddel chat
        }

    }

    @Override
    public void onBindViewHolder(@NonNull Mensajes_holder holder, int position) { //este se encarga del recibimiento

        LMensaje lMensajes=mensajeList.get(position);

        LUsuario lUsuarionnes=lMensajes.getlUsuario(); //meter datos usuario dentro del mensaje

        if (lUsuarionnes!=null){ //validamos si lusuario si el usuario exite

           // holder.getNombre().setText(lMensajes.getlUsuario().usuarioss.getUsuario_nombre()); //se supone que almacena ell nombre del nodo suuarios
            //Glide.with(c).load(lMensajes.getlUsuario().getUsuarioss().getFotodePerfilURI()).into(holder.getFotomensaje_foto_perfil()); //obtiene la ubicacion uri de la foto que se envio en el Mensaje


           holder.getNombre().setText(lUsuarionnes.getUsuarioss().getUsuario_nombre()); //se supone que almacena ell nombre del nodo suuarios
            Glide.with(c).load(lUsuarionnes.getUsuarioss().getFotodePerfilURI()).into(holder.getFotomensaje_foto_perfil()); //obtiene la ubicacion uri de la foto que se envio en el Mensaje


        }


        holder.getMensaje_enviado().setText(mensajeList.get(position).getMensaje().getMensaje() );

        if (lMensajes.getMensaje().isContienefoto()){

            holder.getFoto_enviada_en_mensaje().setVisibility(View.VISIBLE);
            holder.getMensaje_enviado().setVisibility(View.VISIBLE);

            //pone la URL de la foto donde esta a envir
            Glide.with(c).load(lMensajes.getMensaje().getUrlfoto()).into(holder.getFoto_enviada_en_mensaje()); //obtiene la ubicacion uri de la foto que se envio en el Mensaje


        }else {
            holder.getNombre();
            holder.getFoto_enviada_en_mensaje().setVisibility(View.GONE);
            holder.getMensaje_enviado().setVisibility(View.VISIBLE);
        }

        //obtenemos la foto tniendo en ccuenta que lmensajes esta conectado con logica de usuarios
        //nota deberia ser asi lMensajes.getlUsuario().getUsuario().getNombre() flata el nombre y no me aparece ver el por que
        //  Glide.with(c).load(lMensajes.getlUsuario().getUsuarioss().getUsuario_nombre()).into(holder.getFotomensaje_foto_perfil()); //obtiene la ubicacion uri de la foto que se envio en el Mensaje

        holder.getHora().setText(lMensajes.fecha_enviado_mensaje()); //adaptamos la hora que tendra el menaje con el objeto creado en L Mensajes

    }

    @Override
    public int getItemCount()  { //taaño d ela lista del chat
        return mensajeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mensajeList.get(position).getlUsuario() != null) { //si mesnaje list pregunta si fuimos nosootros quien enviamos el mensaje , de la lista que carga los mensajes

            //primero preguntamos si el mensaje es de nosotros
            if (mensajeList.get(position).getlUsuario().getKey().equals(Usuario_DAO.getInstance().getKeyUsuario())) {
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
