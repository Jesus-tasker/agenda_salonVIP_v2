package com.example.agenda_salonvip.Holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda_salonvip.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Clientes_View_holder extends RecyclerView.ViewHolder {

    private CircleImageView foto_perfil_item_cliente;
    private TextView nombre_cliente_item;
    private TextView telefono;
    private ImageView btn_mensajeria;
    private ImageView btn_cita_nueva;

    private Uri urifotos;



    public Clientes_View_holder(@NonNull View itemView) { //especifica qeu campos seran cambbiados conforme la informacion que necestamos usar
        super(itemView);
        foto_perfil_item_cliente=(CircleImageView) itemView.findViewById(R.id.foto_perfil_item_Cliente);
        nombre_cliente_item=(TextView)itemView.findViewById(R.id.nom_item_C);
        telefono=(TextView)itemView.findViewById(R.id.tel_item_c);
        //linearLayout_item_usuario_list=itemView.findViewById(R.id.lauout_principal_usuariosList);
        btn_mensajeria=(ImageView)itemView.findViewById(R.id.btn_mensajes_item);
        btn_cita_nueva=(ImageView)itemView.findViewById(R.id.btn_item_cita);




    }

    public CircleImageView getFoto_perfil_item_cliente() {
        return foto_perfil_item_cliente;
    }

    public void setFoto_perfil_item_cliente(CircleImageView foto_perfil_item_cliente) {
        this.foto_perfil_item_cliente = foto_perfil_item_cliente;
    }

    public TextView getNombre_cliente_item() {
        return nombre_cliente_item;
    }

    public void setNombre_cliente_item(TextView nombre_cliente_item) {
        this.nombre_cliente_item = nombre_cliente_item;
    }

    public TextView getTelefono() {
        return telefono;
    }

    public void setTelefono(TextView telefono) {
        this.telefono = telefono;
    }

    public ImageView getBtn_mensajeria() {
        return btn_mensajeria;
    }

    public void setBtn_mensajeria(ImageView btn_mensajeria) {
        this.btn_mensajeria = btn_mensajeria;
    }

    public ImageView getBtn_cita_nueva() {
        return btn_cita_nueva;
    }

    public void setBtn_cita_nueva(ImageView btn_cita_nueva) {
        this.btn_cita_nueva = btn_cita_nueva;
    }

    public Uri getUrifotos() {
        return urifotos;
    }

    public void setUrifotos(Uri urifotos) {
        this.urifotos = urifotos;
    }
}
