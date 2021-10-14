package Models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ec.tecnologicoloja.proyecto.restaurant_pedidos.MainActivity;
import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class Pedidos extends AppCompatActivity implements View.OnClickListener {
    private Button regresarMenuP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        regresarMenuP = (Button) findViewById(R.id.btnRegresarMenu);
        regresarMenuP.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view==regresarMenuP){
            Intent intent = new Intent(Pedidos.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
