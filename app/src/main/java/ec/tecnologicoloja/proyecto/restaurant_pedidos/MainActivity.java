package ec.tecnologicoloja.proyecto.restaurant_pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import Models.Pedidos;
import Models.QuienesSomos;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Button btniniciarPedido, btnSalir;
    private Spinner spinnerNumTables;
    private ImageButton imgBtnPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTime);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerNumTables = findViewById(R.id.spinnerNumTables);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.num_tables, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumTables.setAdapter(adapter);
        spinnerNumTables.setOnItemSelectedListener(this);
        btniniciarPedido = (Button) findViewById(R.id.btnIniciarPedido);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        imgBtnPB = (ImageButton) findViewById(R.id.imageButtonPB);
        btniniciarPedido.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if (view==btniniciarPedido){
            Intent i = new Intent(this, Pedidos.class);
            startActivity(i);
        } if (view==btnSalir){

        } if(view==imgBtnPB){
            Intent intent = new Intent(this, QuienesSomos.class);
            startActivity(intent);
        }
    }

}