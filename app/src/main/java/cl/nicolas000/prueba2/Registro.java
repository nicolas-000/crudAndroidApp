package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {
    EditText nombres, apellidos, email, pass, pass2;
    Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombres = (EditText) findViewById(R.id.editTextNombre);
        apellidos = (EditText) findViewById(R.id.editTextApellido);
        email = (EditText) findViewById(R.id.editTextEmailAddress);
        pass = (EditText) findViewById(R.id.editTextPassword);
        pass2 = (EditText) findViewById(R.id.editTextPassword2);
        btnRegistro = (Button) findViewById(R.id.btnRegistrar);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.getText().toString().equals(pass2.getText().toString())) {
                    registrar(nombres.getText().toString(), apellidos.getText().toString(), email.getText().toString(), pass.getText().toString());
                    Intent l = new Intent(Registro.this, Listado.class);
                    startActivity(l);
                }
            }
        });
    }

    public void registrar(String nom, String ape, String mai, String cla)
    {
        ConnectionDBHelper helper = new ConnectionDBHelper(this, "APPSQLITE", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            ContentValues datos=new ContentValues();
            datos.put("Nombre",nom);
            datos.put("Apellido",ape);
            datos.put("Email",mai);
            datos.put("Clave",cla);
            db.insert("USUARIOS",null,datos);

            // Toast.makeText(this,"Datos Ingresados Sin Problemas", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            // Toast.makeText(this,"Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}