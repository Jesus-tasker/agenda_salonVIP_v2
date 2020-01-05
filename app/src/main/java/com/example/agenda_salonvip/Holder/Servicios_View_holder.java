package com.example.agenda_salonvip.Holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_salonvip.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Servicios_View_holder extends RecyclerView.ViewHolder {

    CircleImageView foto_emisor;
    ImageView foto_servicio;
    TextView nombre_emisor;
    TextView ubicacion;
    ImageView menu_servicios;
    TextView precio_Servicio;
    ImageView guardar_Servicioa_carpeta;
    ImageView chaatear_emisor;

    private Uri urifotos;

    public Servicios_View_holder(@NonNull View itemView) {
        super(itemView);

        foto_emisor=(CircleImageView)itemView.findViewById(R.id.foto_servicio_emisor);
        foto_servicio=(ImageView)itemView.findViewById(R.id.imagen_del_producto);
        nombre_emisor=(TextView)itemView.findViewById(R.id.nombre_receptor_servicio);
        ubicacion=(TextView)itemView.findViewById(R.id.ubicacion_servicio);
        menu_servicios=(ImageView)itemView.findViewById(R.id.menu_servicio);
        precio_Servicio=(TextView)itemView.findViewById(R.id.precio_servicio);
        guardar_Servicioa_carpeta=(ImageView)itemView.findViewById(R.id.guardar_servicio_favoritos);
        chaatear_emisor=(ImageView)itemView.findViewById(R.id.chatear_emisor_servicio);




    }

    public CircleImageView getFoto_emisor() {
        return foto_emisor;
    }

    public void setFoto_emisor(CircleImageView foto_emisor) {
        this.foto_emisor = foto_emisor;
    }

    public ImageView getFoto_servicio() {
        return foto_servicio;
    }

    public void setFoto_servicio(ImageView foto_servicio) {
        this.foto_servicio = foto_servicio;
    }

    public TextView getNombre_emisor() {
        return nombre_emisor;
    }

    public void setNombre_emisor(TextView nombre_emisor) {
        this.nombre_emisor = nombre_emisor;
    }

    public TextView getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(TextView ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ImageView getMenu_servicios() {
        return menu_servicios;
    }

    public void setMenu_servicios(ImageView menu_servicios) {
        this.menu_servicios = menu_servicios;
    }

    public TextView getPrecio_Servicio() {
        return precio_Servicio;
    }

    public void setPrecio_Servicio(TextView precio_Servicio) {
        this.precio_Servicio = precio_Servicio;
    }

    public ImageView getGuardar_Servicioa_carpeta() {
        return guardar_Servicioa_carpeta;
    }

    public void setGuardar_Servicioa_carpeta(ImageView guardar_Servicioa_carpeta) {
        this.guardar_Servicioa_carpeta = guardar_Servicioa_carpeta;
    }

    public ImageView getChaatear_emisor() {
        return chaatear_emisor;
    }

    public void setChaatear_emisor(ImageView chaatear_emisor) {
        this.chaatear_emisor = chaatear_emisor;
    }

    public Uri getUrifotos() {
        return urifotos;
    }

    public void setUrifotos(Uri urifotos) {
        this.urifotos = urifotos;
    }
}
