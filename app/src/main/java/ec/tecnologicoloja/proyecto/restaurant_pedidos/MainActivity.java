package ec.tecnologicoloja.proyecto.restaurant_pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import Models.Pedidos;
import Models.QuienesSomos;
import Registration.loginActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btniniciarPedido, btnRegresarLogin;
    //private Spinner spinnerNumTables;
    //private ImageButton imgBtnPB;
    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTime);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //spinnerNumTables = findViewById(R.id.spinnerNumTables);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.num_tables, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerNumTables.setAdapter(adapter);
        //spinnerNumTables.setOnItemSelectedListener(this);
        btniniciarPedido = (Button) findViewById(R.id.btnIniciarPedido);
        btnRegresarLogin = (Button) findViewById(R.id.btnRegresarLogin);
        btniniciarPedido.setOnClickListener(this);
        btnRegresarLogin.setOnClickListener(this);
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    @Override
    public void onClick(View view) {
        if (view==btniniciarPedido){
            Intent i = new Intent(this, Pedidos.class);
            startActivity(i);
        } if (view==btnRegresarLogin){
            Intent login = new Intent(this, loginActivity.class);
            startActivity(login);
        }
    }
}