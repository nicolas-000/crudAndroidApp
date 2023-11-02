package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {
    ListView listado;
    ArrayList<String> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listado = (ListView) findViewById(R.id.listadoUsuarios);

        cargarLista();

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        lista=ListaUsuario();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
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
                String linea = c.getInt(0)+" "+c.getString(1)+" "+c.getString(2);
                datos.add(linea);
            }while(c.moveToNext());
        }
        db.close();
        return datos;
    }
}