package clientes_BBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class clientes_BBDD extends SQLiteOpenHelper {

    public  clientes_BBDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE clientes(_ID integer primary key autoincrement, "+
                "nombre text,telefono text,servicio text,descripcion text);";



        String query2="CREATE TABLE citas(_ID integer primary key autoincrement, "+
                "fecha_cita text,nombre_cliente text,telefono_cliente text,servicio_cliente text);";



        String query3="CREATE TABLE usuarios(_ID integer primary key autoincrement, "+
                "Usuario text,Pasword text,rut text);";


        db.execSQL(query3);
        db.execSQL(query2);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //methodo abrir y cerrar base de datos

    public void abri(){
        this.getWritableDatabase();
    }

    public void cerrar(){

        this.close();
    }

    //METHODO QUE PERMITE REGISTRAREN LA TABLA USUARIOS
    public void insertarRegistro_usuario(String nom,String passw,String numm){

        ContentValues valoress=new ContentValues();

        valoress.put("Usuario",nom);
        valoress.put("pasword",passw);
        valoress.put("rut",numm);
        //insertar nombre d ela tabla ,null y valores

        this.getWritableDatabase().insert("usuarios",null,valoress);


    }

    //video 11, se cra el methodo para validar si el usuario existe de tipo cursor
    //https://www.youtube.com/watch?v=J9uEvvUjvrU&list=PLnWAzeXp9V4my1r9re0Qxl1VTmr7wxlKs&index=10
    //usa los parametros usu y pass que recibira
    //throwws es un control de eerrores(poca informacion sobre eso)
    public Cursor consultrusupass(String usu, String pass)throws SQLException {

        Cursor nuevocursor=null;
        //se crea la sentencia para llamar al methodo cuery
        nuevocursor=this.getReadableDatabase().query("usuarios",   //tabla de la consulta
                new String[]{"_ID","Usuario","pasword"},  //ileras de la tabla
                "Usuario like '"+usu+"' and pasword like '"+pass+"'",
                null,
                null,
                null,
                null); //criterios a comparar

        //la consulta retorna el valor de la consulta y lo almacena en el cursor
        //si el usu y pass existen retorna un registro de no ser asi retorna 0 registros



        return nuevocursor;

    }



    //METHODO QUE PERMITE REGISTRAREN LA TABLA USUARIOS
    public void insertarRegistro_clientes(String nom,String tel,String serv,String obserb){

        ContentValues valoress=new ContentValues();

        valoress.put("nombre",nom);
        valoress.put("telefono",tel);
        valoress.put("servicio",serv);
        valoress.put("descripcion",obserb);
        //insertar nombre d ela tabla ,null y valores

        this.getWritableDatabase().insert("clientes",null,valoress);



    }

    public  void  nuevacita(String fecha_cita,String nom_cliente,String telefono, String servicio){

        ContentValues valoress=new ContentValues();

        valoress.put("fecha_cita",fecha_cita);
        valoress.put("nombre_cliente",nom_cliente);
        valoress.put("telefono_cliente",telefono);
        valoress.put("servicio_cliente",servicio);

        //insertar nombre d ela tabla ,null y valores

        this.getWritableDatabase().insert("citas",null,valoress);


    }

    public Cursor lista_clientes_nombre()throws SQLException{ //retorna solo una lista de valores co el nombre de los clientes

        Cursor nuevo=null;

        nuevo=this.getReadableDatabase().rawQuery("SELECT nombre FROM clientes",null);



        return nuevo;
    }
    public Cursor lista_clientes_telefono()throws SQLException{ //retorna solo una lista de valores co el nombre de los clientes

        Cursor nuevo=null;

        nuevo=this.getReadableDatabase().rawQuery("SELECT telefono FROM clientes",null);



        return nuevo;
    }



    public Cursor cargar_usuarios(String nom,String tel)throws SQLException { //compara usuarios?

        Cursor nuevocursor=null;
        //se crea la sentencia para llamar al methodo cuery
        nuevocursor=this.getReadableDatabase().query("clientes",   //tabla de la consulta
                new String[]{"_ID","nombre","telefono","servicio","descripcion"},  //ileras de la tabla
                "nombre like '"+nom+"' and telefono like '"+tel+"'", //
                null,
                null,
                null,
                null); //criterios a comparar

        //la consulta retorna el valor de la consulta y lo almacena en el cursor
        //si el usu y pass existen retorna un registro de no ser asi retorna 0 registros



        return nuevocursor;

    }



}
