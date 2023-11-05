package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ModificarEliminar extends AppCompatActivity {
    EditText editTextNombreUsuario, editTextApellidoUsuario, editTextEmailUsuario, editTextPassUsuario, editTextPass2Usuario;
    Button btnModificar, btnEliminar;
    int idUsuario;
    String nombre, apellido, mail, pass;
    ArrayList<String> listaDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar);

        editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
        editTextApellidoUsuario = (EditText) findViewById(R.id.editTextApellidoUsuario);
        editTextEmailUsuario = (EditText) findViewById(R.id.editTextEmailAddressUsuario);
        editTextPassUsuario = (EditText) findViewById(R.id.editTextPasswordUsuario);
        editTextPass2Usuario = (EditText) findViewById(R.id.editTextPassword2Usuario);
        btnModificar = (Button) findViewById(R.id.btnModificar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            idUsuario=b.getInt("Id");
            listaDatos = obtenerDatosUsuario(idUsuario);
            nombre = listaDatos.get(0);
            apellido = listaDatos.get(1);
            mail = listaDatos.get(2);
            pass = listaDatos.get(3);
        }

        editTextNombreUsuario.setText(nombre);
        editTextApellidoUsuario.setText(apellido);
        editTextEmailUsuario.setText(mail);
        editTextPassUsuario.setText(pass);
        editTextPass2Usuario.setText(pass);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextNombreUsuario.getText().toString() != "" && editTextApellidoUsuario.getText().toString() != "" && editTextEmailUsuario.getText().toString() != "" && editTextPassUsuario.getText().toString() != "" && editTextPass2Usuario.getText().toString() != "") {
                    if (editTextPassUsuario.getText().toString().equals(editTextPass2Usuario.getText().toString())) {
                        if (validarPassword(editTextPassUsuario.getText().toString())) {
                            modificar(idUsuario,editTextNombreUsuario.getText().toString(),editTextApellidoUsuario.getText().toString(), editTextEmailUsuario.getText().toString(), editTextPassUsuario.getText().toString());
                            onBackPressed();
                        } else { Crouton.showText(ModificarEliminar.this, "La contraseña debe tener al menos 8 caracteres con Mayúsculas, Minúsculas, Números y Caracteres Especiales", Style.ALERT); }
                    } else { Crouton.showText(ModificarEliminar.this, "Las contraseñas no coinciden", Style.ALERT); }
                } else { Crouton.showText(ModificarEliminar.this, "No pueden haber campos vacíos", Style.ALERT); }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(idUsuario);
                onBackPressed();
            }
        });
    }

    private ArrayList<String> obtenerDatosUsuario(int id){
        ArrayList<String> datos=new ArrayList<String>();
        ConnectionDBHelper helper = new ConnectionDBHelper(this, "APPSQLITE", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM USUARIOS WHERE ID="+id;

        Cursor c=db.rawQuery(query,null);
        if(c.moveToFirst())
        {
            do{
                datos.add(c.getString(1));
                datos.add(c.getString(2));
                datos.add(c.getString(3));
                datos.add(c.getString(4));
            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }

    public static boolean validarPassword(String password) {
        Pattern patron = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,100}$");
        Matcher matcher = patron.matcher(password);
        return matcher.matches();
    }

    private void modificar(int Id, String Nombre, String Apellido, String Email, String clave)
    {
        ConnectionDBHelper helper=new ConnectionDBHelper(this,"APPSQLITE", null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="UPDATE USUARIOS SET NOMBRE='"+Nombre+"', APELLIDO='"+Apellido+"', EMAIL='"+Email+"', CLAVE='"+clave+"' WHERE ID="+Id;
        db.execSQL(sql);
        db.close();
    }

    private void eliminar(int Id)
    {
        ConnectionDBHelper helper=new ConnectionDBHelper(this,"APPSQLITE", null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="DELETE FROM USUARIOS WHERE ID="+Id;
        db.execSQL(sql);
        db.close();
    }
}