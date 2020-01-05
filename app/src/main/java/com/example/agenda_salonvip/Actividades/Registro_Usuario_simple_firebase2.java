package com.example.agenda_salonvip.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda_salonvip.Entidades_fire.Usuario;
import com.example.agenda_salonvip.R;
import com.example.agenda_salonvip.Utilidades.Constantes;
import com.example.agenda_salonvip.persistencias_fire.Usuario_DAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import button_navegation.Navegador;

public class Registro_Usuario_simple_firebase2 extends AppCompatActivity {

    private EditText crear_correo2;
    private EditText crerar_contrasena2, contrasena2,nombreusuario;

    private Button registrar12, atras_crear_cuetna;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference,referenceUsuario;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro__usuario_simple_firebase2);
        crear_correo2 = (EditText) findViewById(R.id.correo_usuario2);
        crerar_contrasena2 = (EditText) findViewById(R.id.contraasena2);
        contrasena2 = (EditText) findViewById(R.id.contraasena_repite2);
        nombreusuario = (EditText) findViewById(R.id.nombre_usuario2);
        registrar12 = (Button) findViewById(R.id.guardar_cuenta_nueva2);
        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        referenceUsuario=database.getReference(Constantes.Nodo_Usuario) ;//nodo principal donde almacena los usuarios


        progressDialog=new ProgressDialog(this);//barra de progreso

        registrar12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String correo = crear_correo2.getText().toString().trim();
                final String nombre=nombreusuario.getText().toString().trim();
                final String answ_1 = contrasena2.getText().toString().trim();

                if (TextUtils.isEmpty(correo)) {

                    Toast.makeText(Registro_Usuario_simple_firebase2.this, "ingresar correo", Toast.LENGTH_LONG).show();

                }

                if (TextUtils.isEmpty(answ_1)) {

                    Toast.makeText(Registro_Usuario_simple_firebase2.this, "ingresar contraseña", Toast.LENGTH_LONG).show();

                }


                if (isValidEmail(correo) && validar_contrasena()&& valida_nombre(nombre)) {

                    progressDialog.setMessage("realizando registro en linea... ");
                    progressDialog.show();

                    final String contrasena = crerar_contrasena2.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(correo, contrasena) //autentica correo y contrasena
                            .addOnCompleteListener(Registro_Usuario_simple_firebase2.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        Usuario_DAO.getInstance();
                                        Usuario usuario=new Usuario();
                                        usuario.setCorreo_usuario(correo);
                                        usuario.setUsuario_nombre(nombre);

                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                                        DatabaseReference reference=database.getReference(Constantes.Nodo_Usuario+"/"+currentUser.getUid()); //se concadena con el UID de firebase que es un codigo largo de id


                                        reference.setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() { //para enviar la informacion probamos con la clase hashmap
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){

                                                    startActivity(new Intent(Registro_Usuario_simple_firebase2.this, Navegador.class));
                                                    finish(); //con esto cada vez que termine se cierra y retorna al login


                                                }
                                                startActivity(new Intent(Registro_Usuario_simple_firebase2.this, Usuario_Informacion.class));
                                                finish(); //con esto cada vez que termine se cierra y retorna al login



                                            }
                                        });



                                    }else {
                                        Toast.makeText(Registro_Usuario_simple_firebase2.this, "error al registrarse", Toast.LENGTH_SHORT).show();


                                    }


                                }
                            });
                }


            }

        });
    }


    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean valida_nombre(String nombre){
        return  !nombre.isEmpty();
    }

    public Boolean validar_contrasena () {

        String contra1, contrarepiteee1;
        contra1 = crerar_contrasena2.getText().toString();
        contrarepiteee1 = contrasena2.getText().toString();
        if (contra1.equals(contrarepiteee1) && (contra1.length() >= 6 && contra1.length() <= 16)) {
            return  true;

        }else {return false;}


    }
}