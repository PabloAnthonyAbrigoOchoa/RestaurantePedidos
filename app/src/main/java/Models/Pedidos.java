package Models;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ec.tecnologicoloja.proyecto.restaurant_pedidos.MainActivity;
import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class Pedidos extends AppCompatActivity implements View.OnClickListener {

    private Button regresarMenuP, calcular, cancelar;
    private RadioButton rdParaLlevar, rbEntregaDomicilio;
    private CheckBox checkBoxDN, checkBoxDF, checkBoxDC, checkBoxDA, checkBoxAN, checkBoxAB, checkBoxAC, checkBoxAdeC;
    private TextView ttotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        checkBoxDN = (CheckBox) findViewById(R.id.checkBoxDN);
        checkBoxDF = (CheckBox) findViewById(R.id.checkBoxDF);
        checkBoxDC = (CheckBox) findViewById(R.id.checkBoxDC);
        checkBoxDA = (CheckBox) findViewById(R.id.checkBoxDA);
        checkBoxAN = (CheckBox) findViewById(R.id.checkBoxAN);
        checkBoxAB = (CheckBox) findViewById(R.id.checkBoxAB);
        checkBoxAC = (CheckBox) findViewById(R.id.checkBoxAC);
        checkBoxAdeC = (CheckBox) findViewById(R.id.checkBoxAdeC);

        rdParaLlevar = (RadioButton) findViewById(R.id.rbParaLlevar);
        rbEntregaDomicilio = (RadioButton) findViewById(R.id.rbEntregaDomicilio);

        ttotal = (TextView) findViewById(R.id.textViewTotalPagar);

        calcular = (Button) findViewById(R.id.btnCalcular);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        regresarMenuP = (Button) findViewById(R.id.btnRegresarMenu);
        regresarMenuP.setOnClickListener(this);
    }

    //Metodo para calcular lo que el cliente escoja segun el checkBox
    public void calcular (View v){
        Double total = 0.0; //lo inicializamos en 0 para que no se acumulen los pedidos anteriores
        if (rbEntregaDomicilio.isChecked())
            total = total + 10;
        if (checkBoxDN.isChecked())
            total = total + 30;
        if (checkBoxDF.isChecked())
            total = total + 60;
        if (checkBoxDC.isChecked())
            total = total + 50;
        if (checkBoxDA.isChecked())
            total = total + 40;
        if (checkBoxAN.isChecked())
            total = total + 40;
        if (checkBoxAB.isChecked())
            total = total + 60;
        if (checkBoxAC.isChecked())
            total = total + 70;
        if (checkBoxAdeC.isChecked())
            total = total + 50;

        ttotal.setText("Total :" +total);
    }

    //Metodo para dejar todos los campos limpios y en blanco
    public void cancelar (View v){
        rbEntregaDomicilio.setChecked(false);
        checkBoxDN.setChecked(false);
        checkBoxDF.setChecked(false);
        checkBoxDC.setChecked(false);
        checkBoxDA.setChecked(false);
        checkBoxAN.setChecked(false);
        checkBoxAB.setChecked(false);
        checkBoxAC.setChecked(false);
        checkBoxAdeC.setChecked(false);

        ttotal.setText("Total=0");
    }


    //Metodo para regresar al MenuPrincipal
    @Override
    public void onClick(View view) {
        if (view==regresarMenuP){
            Intent intent = new Intent(Pedidos.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
