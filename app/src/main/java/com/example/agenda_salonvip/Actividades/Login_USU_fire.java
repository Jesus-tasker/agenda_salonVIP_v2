package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import button_navegation.Navegador;

public class Login_USU_fire extends AppCompatActivity {
    EditText edNombre;
    EditText usu_paswworld;



    TextView crear_cuenta;

    Button button_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__usu_fire);

        edNombre =(EditText)findViewById(R.id.login_correo);
        usu_paswworld=(EditText)findViewById(R.id.login_passw);
        crear_cuenta=(TextView)findViewById(R.id.crea_cuenta);
        button_login=(Button)findViewById(R.id.login_usuario);

        mAuth=FirebaseAuth.getInstance();




        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo= edNombre.getText().toString();

                if (isValidEmail(correo) && validar_contrasena()){


                    String contra=usu_paswworld.getText().toString();

                    mAuth.signInWithEmailAndPassword(correo, contra)
                            .addOnCompleteListener(Login_USU_fire.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(Login_USU_fire.this, "contraseña correcta", Toast.LENGTH_SHORT).show();


                                        startActivity(new Intent(Login_USU_fire.this, Navegador.class));
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Login_USU_fire.this, "sin internetg", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });


                }else {

                    Toast.makeText(Login_USU_fire.this, "contraseña invalida", Toast.LENGTH_SHORT).show();

                }       }
        });



        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_USU_fire.this, Registro_Usuario_simple_firebase2.class));


            }
        });


        Usuario_DAO.getInstance().aanadirFotodeRerfil_usuarioSinFoto(); //nota ahcer que este se ejecute una sola vez pues carga todos los nodos de firebase

    }
    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public Boolean validar_contrasena () {



        String contra1;
        contra1 = usu_paswworld.getText().toString();
        if ( (contra1.length() >= 6 && contra1.length() <= 16)) {
            return  true;

        }else {return false;}


    }

    @Override //este methodo verifica si el  Usuario esta logiado osea esta dentro de la app
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Toast.makeText(Login_USU_fire.this, "Usuario logeado", Toast.LENGTH_SHORT).show();
            nextactivity(); //esta comprobacion permite que si esta logeado se mantenga en la pagina de inicio main activyty


        }
        //  updateUI(currentUser);
    }

    private void nextactivity(){
        startActivity(new Intent(Login_USU_fire.this, Navegador.class));
        finish();//se recomienda poner finish para quitar la actividad y que no quede en segundo plano consumiendo recursos

    }
}
