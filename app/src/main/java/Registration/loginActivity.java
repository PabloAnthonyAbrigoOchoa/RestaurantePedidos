package Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import Models.QuienesSomos;
import ec.tecnologicoloja.proyecto.restaurant_pedidos.MainActivity;
import ec.tecnologicoloja.proyecto.restaurant_pedidos.R;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnQuienesSomos, btnRegistrate, btnLogin, btnGoogle;
    private ProgressDialog mProgressBar;
    EditText txtInputEmail, txtInputPassword;
    int RC_SIGN_IN = 1;
    String TAG = "GoogleSignInLoginActivity";
    //Vaiable para gestionar el FirebaseAuth
    private FirebaseAuth mAuth;
    //Linea de codigo para agregar clientes de inicio de sesion con Google
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnQuienesSomos = (Button) findViewById(R.id.btnQuienesSomos);
        btnRegistrate = (Button) findViewById(R.id.btnRegistrate);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnGoogle = (Button) findViewById(R.id.btnGoogle);
        btnQuienesSomos.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        mProgressBar = new ProgressDialog(loginActivity.this);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //Configuramos Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //Creamos un GoogleSingInClient con las opciones especificadas por la variable gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Inicializamos Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (btnQuienesSomos==view){
            Intent i = new Intent(this, QuienesSomos.class);
            startActivity(i);
        } if (btnRegistrate==view){
            Intent i = new Intent(this, registerActivity.class);
            startActivity(i);
        } if(btnLogin==view){
            verificarCredenciales();
        }
    }

    //Metodo en el cual llamamos a un metodo de startActivityForResult
    private void signIn(){
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                try {
                    //Aqui verificamos que el usuario elija su cuenta de Google y le envie al Metodo firebaseAutWthGoogle
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
            }else{
                //Toast para mostrar si el usuario se sale del menu de eleccion de la cuenta de Google
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                Toast.makeText(this, "Ocurrio un error. "+task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //Controlamos si es exitoso o no el login del usuario
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //Iniciar DASHBOARD u otra actividad luego del SignIn Exitoso
                            Intent dashboardActivity = new Intent(loginActivity.this, MainActivity.class);
                            startActivity(dashboardActivity);
                            loginActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    //Metodo para validar que no esten vacios nuestros campos
    public void verificarCredenciales(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();
        if(email.isEmpty() || !email.contains("@")){
            showError(txtInputEmail, "Email no válido");
        }else if(password.isEmpty()|| password.length()<7){
            showError(txtInputPassword, "Contraseña incorrecta");
        }else{
            //Mostrar ProgressBar
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Iniciando sesión, espere un momento...");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            //Registrar usuario
            //Exitoso -> Mostrar toast
            //redireccionar - intent a login
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
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


    //Metodo que nos servira para hacer validacion y almacenar dentro de la variable user los usuarios
    @Override
    protected void onStart() {
        FirebaseUser user = mAuth.getCurrentUser(); //obtenemos el usuario actual si esta logeado
        if(user!=null){ //si no es null el usuario ya esta logueado
            //mover al usuario al activityMain
            Intent dashboardActivity = new Intent(loginActivity.this, MainActivity.class);
            startActivity(dashboardActivity);
        }
        super.onStart();
    }

}
