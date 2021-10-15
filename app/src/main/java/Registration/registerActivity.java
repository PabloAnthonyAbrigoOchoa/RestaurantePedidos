package Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegresarMenuPP, btnRegister;
    TextView tieneCuenta;
    EditText txtInputUsername, txtInputEmail, txtInputPassword, txtInputConfirmPassword;
    private ProgressDialog mProgressBar;
    //Firebase
    FirebaseAuth mAuth;

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

        //Instanciamos la variable del Firebase
        mAuth = FirebaseAuth.getInstance();
        //Inicializamos el ProgressBar y le pasamos el contexto en este caso de la misma activity
        mProgressBar = new ProgressDialog(registerActivity.this);
    }

    private void verificarCredenciales() {
        String username = txtInputUsername.getText().toString();
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        String confirmPass = txtInputConfirmPassword.getText().toString();
        if(username.isEmpty() || username.length() < 5){ //Validamos si el Username esta vacio o menor a 5 caracteres
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
            mProgressBar.setMessage("Registrando el usuario, por favor espere un momento");
            mProgressBar.setCanceledOnTouchOutside(false);//Por si presionamos cancelar con el dedo este no se cancele y se siga mostrando
            mProgressBar.show();
            //Registramos el usuario
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        mProgressBar.dismiss();//ocultamos progressBar

                        //redireccionamos - intent a loginActivity
                        Intent intent = new Intent(registerActivity.this, loginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "¡Registrado Exitosamente!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se pudo registrar...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Metodo para establecer el error y el mensaje mediante un string
    private void showError(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}
