package cl.nicolas000.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WebService extends AppCompatActivity {
    TextView textDate, textTemp, textHum;
    ImageView imageTemp;

    RequestQueue datos;
    Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textDate = (TextView) findViewById(R.id.textDate);
        textTemp = (TextView) findViewById(R.id.textTemp);
        textHum = (TextView) findViewById(R.id.textHum);
        imageTemp = (ImageView) findViewById(R.id.imageTemp);

        datos = Volley.newRequestQueue(this);
        Refrescar.run();
    }

    public Runnable Refrescar =new Runnable() {
        @Override
        public void run() {
            textDate.setText(obtenerFechaHora());
            obtenerDatos();
            mHandler.postDelayed(this,1000);
        }
    };
    public String obtenerFechaHora()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY -- HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return strDate;
    }

    public void cambiarImagen(Float valor)
    {
        if(valor>=30) {
            imageTemp.setImageResource(R.drawable.hightemperature);
        }else {
            imageTemp.setImageResource(R.drawable.lowtemperature);
        }
    }

    public void obtenerDatos()
    {
        String url="https://www.pnk.cl/muestra_datos.php";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    textTemp.setText(response.getString("temperatura")+" C°");
                    textHum.setText(response.getString("humedad")+" %");
                    Float valor = Float.parseFloat(response.getString("temperatura"));
                    cambiarImagen(valor);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        datos.add(request);
    }
}