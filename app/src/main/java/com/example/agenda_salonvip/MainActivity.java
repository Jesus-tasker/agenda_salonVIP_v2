package com.example.agenda_salonvip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import clientes_BBDD.clientes_BBDD;

public class MainActivity extends AppCompatActivity {
    EditText usuario,contrase;
    Button login;
    TextView crear_cuenta;



    clientes_BBDD helper=new clientes_BBDD(this,"usuario_estilista",null,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario=(EditText)findViewById(R.id.acceso_usuario);
        contrase=(EditText)findViewById(R.id.acceso_contrasena);
        login=(Button)findViewById(R.id.login);
        crear_cuenta=(TextView)findViewById(R.id.registrese);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //si el usuario existe me retorna un curnos y si no un null
                //llamamos al methodo de sqlite_openHelper, para ver si el usuario existe
                //Cursor cursor1=helper.consultrusupass(usuario.getText().toString(),contrase.getText().toString());
                //por comodidad es mejor ponerlo en un try cth

                try{  //si el dato existe psa a la siguient ventana y si no enviar un mensaje de error
                    Cursor cursor1=helper.consultrusupass(usuario.getText().toString(),contrase.getText().toString());

                    if (cursor1.getCount()>0){//si el cursor es mayor que 0 pasamos a la ventana pues encontro el dato
                        Intent i=new Intent(getApplicationContext(),pagina_inicio.class );
                        startActivity(i);

                    }
                    else {

                        Toast.makeText(getApplicationContext(),"usuario y/O password incorrecto",Toast.LENGTH_LONG).show();
                    }

                    usuario.setText(""); //vaciamos los espacios al terminar siempre
                    contrase.setText(""); //vaciamso los espacios al terminar siempre
                    usuario.findFocus();
                }catch (SQLException e){


                }


            }
        });





        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Registro_Usuario.class );
                startActivity(i);
            }
        });

    }
}
