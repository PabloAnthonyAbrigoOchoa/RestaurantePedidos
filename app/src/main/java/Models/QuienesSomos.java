package Models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Registration.loginActivity;
import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class QuienesSomos extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegresarMenuP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quienes_somos);
        btnRegresarMenuP = (Button) findViewById(R.id.btnRegresarMenuP);
        btnRegresarMenuP.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (btnRegresarMenuP==view){
            Intent p = new Intent(this, loginActivity.class);
            startActivity(p);
        }
    }
}
