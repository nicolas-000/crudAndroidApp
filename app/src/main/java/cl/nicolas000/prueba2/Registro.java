package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Registro extends AppCompatActivity {
    EditText nombres, apellidos, email, pass, pass2;
    Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombres = (EditText) findViewById(R.id.editTextNombreUsuario);
        apellidos = (EditText) findViewById(R.id.editTextApellidoUsuario);
        email = (EditText) findViewById(R.id.editTextEmailAddressUsuario);
        pass = (EditText) findViewById(R.id.editTextPasswordUsuario);
        pass2 = (EditText) findViewById(R.id.editTextPassword2Usuario);
        btnRegistro = (Button) findViewById(R.id.btnRegistrar);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombres.getText().toString().trim().length() > 0 && apellidos.getText().toString().trim().length() > 0 && email.getText().toString().trim().length() > 0 && pass.getText().toString().trim().length() > 0 && pass2.getText().toString().trim().length() > 0) {
                    if (pass.getText().toString().equals(pass2.getText().toString())) {
                        if (validarPassword(pass.getText().toString())) {
                            registrar(nombres.getText().toString(), apellidos.getText().toString(), email.getText().toString(), pass.getText().toString());
                            Intent l = new Intent(Registro.this, Listado.class);
                            startActivity(l);
                        } else { Crouton.showText(Registro.this, "La contraseña debe tener al menos 8 caracteres con Mayúsculas, Minúsculas, Números y Caracteres Especiales", Style.ALERT); }
                    } else { Crouton.showText(Registro.this, "Las contraseñas no coinciden", Style.ALERT); }
                } else { Crouton.showText(Registro.this, "No pueden haber campos vacíos", Style.ALERT); }
            }
        });
    }

    public static boolean validarPassword(String password) {
        Pattern patron = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,100}$");
        Matcher matcher = patron.matcher(password);
        return matcher.matches();
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

            Crouton.showText(this, "Datos Ingresados Sin Problemas", Style.CONFIRM);
        }catch (Exception e){
            Crouton.showText(this, "Error"+e.getMessage(), Style.ALERT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}