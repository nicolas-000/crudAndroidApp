package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPass;
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCredenciales(editTextEmail.getText().toString(), editTextPass.getText().toString())) {
                    Intent m = new Intent(MainActivity.this, Menu.class);
                    startActivity(m);
                    finish();
                } else { Crouton.showText(MainActivity.this, "Credenciales Incorrectas", Style.ALERT); }
            }
        });
    }

    private boolean validarCredenciales(String mail, String pass) {
        ArrayList<String> datos = obtenerAdmins();
        return datos.contains(mail + " " + pass);
    }

    private ArrayList<String> obtenerAdmins() {
        ArrayList<String> datos=new ArrayList<String>();
        ConnectionDBHelper helper = new ConnectionDBHelper(this, "APPSQLITE", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM SUPERUSUARIOS";

        Cursor c=db.rawQuery(query,null);

        if(c.moveToFirst())
        {
            do{
                String linea = c.getString(2)+" "+c.getString(3);
                datos.add(linea);
            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }
}