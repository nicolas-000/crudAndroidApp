package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    ListView listado;
    ArrayList<String> lista;
    EditText editTextBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listado = (ListView) findViewById(R.id.listadoUsuarios);
        editTextBuscar = (EditText) findViewById(R.id.editTextBuscar);

        cargarLista();

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idUsuario = Integer.parseInt(lista.get(position).split(" ")[0]);
                String nombre=lista.get(position).split(" ")[1];
                String apellido=lista.get(position).split(" ")[2];
                String mail=lista.get(position).split(" ")[3];
                Intent intent=new Intent(Listado.this,ModificarEliminar.class);
                intent.putExtra("Id",idUsuario);
                intent.putExtra("Nombre",nombre);
                intent.putExtra("Apellido",apellido);
                intent.putExtra("Email",mail);
                startActivity(intent);
            }
        });

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                cargarLista();
                if (count != 0) {
                    lista.removeIf(usuario -> (!usuario.contains(charSequence)));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        cargarLista();
    }

    private void cargarLista()
    {
        lista = ListaUsuario();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        };
        listado.setAdapter(adapter);
    }

    private ArrayList<String> ListaUsuario(){
        ArrayList<String> datos=new ArrayList<String>();
        ConnectionDBHelper helper = new ConnectionDBHelper(this, "APPSQLITE", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM USUARIOS";

        Cursor c=db.rawQuery(query,null);
        if(c.moveToFirst())
        {
            do{
                String linea = c.getInt(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3);
                datos.add(linea);
            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }
}