package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button btnDatos, btnRegistro, btnMostrar, btnAcerca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnDatos = (Button) findViewById(R.id.btnDatos);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnAcerca = (Button) findViewById(R.id.btnAcerca);
        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(Menu.this, WebService.class);
                startActivity(d);
                finish();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(Menu.this, Registro.class);
                startActivity(r);
                finish();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m = new Intent(Menu.this, Listado.class);
                startActivity(m);
                finish();
            }
        });

        btnAcerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Menu.this, Acerca.class);
                startActivity(a);
                finish();
            }
        });
    }
}