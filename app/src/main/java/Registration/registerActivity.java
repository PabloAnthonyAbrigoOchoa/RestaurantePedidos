package Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegresarMenuPP, btnRegister;
    TextView tieneCuenta;
    EditText txtInputUsername, txtInputEmail, txtInputPassword, txtInputConfirmPassword;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tieneCuenta = (TextView) findViewById(R.id.alreadyHaveAccount);

        txtInputUsername = (EditText) findViewById(R.id.inputUsername);
        txtInputEmail = (EditText) findViewById(R.id.inputEmail);
        txtInputPassword = (EditText) findViewById(R.id.inputPassword);
        txtInputConfirmPassword = (EditText) findViewById(R.id.inputConfirmPassword);

        btnRegresarMenuPP = (Button) findViewById(R.id.btnRegresarMenuPP);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegresarMenuPP.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (btnRegresarMenuPP==view){
            Intent i = new Intent(this, loginActivity.class);
            startActivity(i);
        } if (btnRegister==view){
            verificarCredenciales();
        }
        tieneCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,loginActivity.class));
            }
        });
    }

    private void verificarCredenciales() {
        String username = txtInputUsername.getText().toString();
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        String confirmPass = txtInputConfirmPassword.getText().toString();
        if(username.isEmpty() || username.length() < 5){
            showError(txtInputUsername,"Username no valido");
        }else if (email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail,"Email no valido");
        }else if (password.isEmpty() || password.length() < 7){
            showError(txtInputPassword,"Clave no valida minimo 7 caracteres");
        }else if (confirmPass.isEmpty() || !confirmPass.equals(password)){
            showError(txtInputConfirmPassword,"Clave no valida, no coincide.");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Proceso de Registro");
            mProgressBar.setMessage("Registrando usuario, espere un momento");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            //Registrar usuario
            //Exitoso -> Mostrar toast
            //redireccionar - intent a login
            Intent intent = new Intent(registerActivity.this, loginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //ocultar progressBar
            mProgressBar.dismiss();
        }
    }
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}
