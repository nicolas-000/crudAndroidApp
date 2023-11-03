package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModificarEliminar extends AppCompatActivity {
    EditText editTextNombreUsuario, editTextApellidoUsuario, editTextEmailUsuario, editTextPassUsuario, editTextPass2Usuario;
    Button btnModificar, btnEliminar;
    int idUsuario;
    String nombre, apellido, mail, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar);

        editTextNombreUsuario = (EditText) findViewById(R.id.editTextNombreUsuario);
        editTextApellidoUsuario = (EditText) findViewById(R.id.editTextApellidoUsuario);
        editTextEmailUsuario = (EditText) findViewById(R.id.editTextEmailAddressUsuario);
        editTextPassUsuario = (EditText) findViewById(R.id.editTextPasswordUsuario);
        editTextPass2Usuario = (EditText) findViewById(R.id.editTextPassword2Usuario);

        Bundle b=getIntent().getExtras();
        if(b!=null)
        {
            idUsuario=b.getInt("Id");
            nombre=b.getString("Nombre");
            apellido=b.getString("Apellido");
            mail=b.getString("Email");
            pass=b.getString("Pass");
        }

        editTextNombreUsuario.setText(nombre);
        editTextApellidoUsuario.setText(apellido);
        editTextEmailUsuario.setText(mail);
        editTextPassUsuario.setText(pass);
        editTextPass2Usuario.setText(pass);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(idUsuario,editTextNombreUsuario.getText().toString(),editTextApellidoUsuario.getText().toString(), editTextEmailUsuario.getText().toString());
                onBackPressed();
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

    private void modificar(int Id, String Nombre, String Apellido, String Email)
    {
        ConnectionDBHelper helper=new ConnectionDBHelper(this,"APPSQLITE", null,1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql="UPDATE USUARIOS SET NOMBRE='"+Nombre+"', APELLIDO='"+Apellido+"', EMAIL='"+Email+"' WHERE ID="+Id;
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