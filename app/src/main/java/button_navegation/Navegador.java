package button_navegation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.agenda_salonvip.Actividades.Login_USU_fire;
import com.example.agenda_salonvip.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import fragments_navegador.Folder_fragment;
import fragments_navegador.citas_Fragment;
import fragments_navegador.clientes_inicioFragment;
import fragments_navegador.VentaGeneral;

public class Navegador extends AppCompatActivity {

        BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegador);

        mostrarFragmentoSeleccionado(new clientes_inicioFragment()); //aqui inicio el fragment para que siempre inicie en este

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navegador_button);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.clientes_inicio_item){ //aqui señala si pulso sobre el primer item

                    mostrarFragmentoSeleccionado(new clientes_inicioFragment());

                }
                if (menuItem.getItemId()==R.id.mensajeria){ //aqui señala si pulso sobre el primer item

                    mostrarFragmentoSeleccionado(new VentaGeneral());

                }
                if (menuItem.getItemId()==R.id.citas_item){ //aqui señala si pulso sobre el primer item

                    mostrarFragmentoSeleccionado(new citas_Fragment());

                }

                if (menuItem.getItemId()==R.id.portafolio_item){ //aqui señala si pulso sobre el primer item

                    mostrarFragmentoSeleccionado(new Folder_fragment());

                }




                return true;
            }
        });


        //codigo para el menu deplegable



    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_folder,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//recuperamos el item seleccionado para salir d ela app
        if(id == R.id.conficguracion_config){

            //El código que se ejecutara al hacer click en esa opción

        }
        if(id == R.id.salida_config){
            FirebaseAuth.getInstance().signOut();
            //El código que se ejecutara al hacer click en esa opción
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(Navegador.this, Login_USU_fire.class));
                            finish();
                        }
                    });


        }

        return super.onOptionsItemSelected(item);
    }


    //creo unm methodo que eligue el fragmento que se eligiio
    private void  mostrarFragmentoSeleccionado(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
