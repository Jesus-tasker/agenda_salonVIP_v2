package com.example.agenda_salonvip.Holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_salonvip.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Citas_view_holder extends RecyclerView.ViewHolder {

    private CircleImageView foto_perfil_item_cita;
    private TextView nombre_cliente_item_cita;
    private TextView telefono;
    private TextView servicio;
    private TextView fecha;
    private TextView duracion_cita;
    private ImageView btn_cerrar,btn_cumplido;

    private Uri urifotos;


    public Citas_view_holder(@NonNull View itemView) {
        super(itemView);
        foto_perfil_item_cita=(CircleImageView) itemView.findViewById(R.id.citas_image_perfil);
        nombre_cliente_item_cita=(TextView)itemView.findViewById(R.id.nom_cliente_cita);
        telefono=(TextView)itemView.findViewById(R.id.telefono_cliente_cita);
        servicio=(TextView)itemView.findViewById(R.id.serv_cliente_cita);
        fecha=(TextView)itemView.findViewById(R.id.fecha_cliente_cita);
        duracion_cita=(TextView)itemView.findViewById(R.id.tiempo_cita_item);
        btn_cerrar=(ImageView)itemView.findViewById(R.id.borrar_cita);
        btn_cumplido=(ImageView)itemView.findViewById(R.id.cita_cumplida);

    }


    public CircleImageView getFoto_perfil_item_cita() {
        return foto_perfil_item_cita;
    }

    public void setFoto_perfil_item_cita(CircleImageView foto_perfil_item_cita) {
        this.foto_perfil_item_cita = foto_perfil_item_cita;
    }

    public TextView getNombre_cliente_item_cita() {
        return nombre_cliente_item_cita;
    }

    public void setNombre_cliente_item_cita(TextView nombre_cliente_item_cita) {
        this.nombre_cliente_item_cita = nombre_cliente_item_cita;
    }

    public TextView getTelefono() {
        return telefono;
    }

    public void setTelefono(TextView telefono) {
        this.telefono = telefono;
    }

    public TextView getServicio() {
        return servicio;
    }

    public void setServicio(TextView servicio) {
        this.servicio = servicio;
    }

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }

    public Uri getUrifotos() {
        return urifotos;
    }

    public void setUrifotos(Uri urifotos) {
        this.urifotos = urifotos;
    }

    public TextView getDuracion_cita() {
        return duracion_cita;
    }

    public void setDuracion_cita(TextView duracion_cita) {
        this.duracion_cita = duracion_cita;
    }

    public ImageView getBtn_cerrar() {
        return btn_cerrar;
    }

    public void setBtn_cerrar(ImageView btn_cerrar) {
        this.btn_cerrar = btn_cerrar;
    }

    public ImageView getBtn_cumplido() {
        return btn_cumplido;
    }

    public void setBtn_cumplido(ImageView btn_cumplido) {
        this.btn_cumplido = btn_cumplido;
    }
}
