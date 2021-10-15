package ec.tecnologicoloja.proyecto.restaurant_pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import Models.Pedidos;
import Registration.loginActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userName, userEmail, userID;
    private CircleImageView userImg;
    private Button btniniciarPedido, btnRegresarLogin, btnCerrarSesion, btnEliminarCuenta;

    //Variable para gestionar FirebaseAuth
    private FirebaseAuth mAuth;

    //Variables opcionales para cerrar sesion de google tambien
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTime);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (TextView) findViewById(R.id.userNombre);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userID = (TextView) findViewById(R.id.userId);
        userImg = (CircleImageView) findViewById(R.id.userImagen);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        btniniciarPedido = (Button) findViewById(R.id.btnIniciarPedido);
        btnRegresarLogin = (Button) findViewById(R.id.btnRegresarLogin);
        btnCerrarSesion = (Button) findViewById(R.id.btnLogout);
        btnEliminarCuenta = (Button) findViewById(R.id.btnEliminarCta);
        btniniciarPedido.setOnClickListener(this);
        btnRegresarLogin.setOnClickListener(this);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cerrar sesion con Firebase
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //Cerrar sesión con google tambien: Google sign out
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Abrir MainActivity con SigIn button
                        if(task.isSuccessful()){
                            Intent loginActivity = new Intent(getApplicationContext(), loginActivity.class);
                            startActivity(loginActivity);
                            MainActivity.this.finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        //Eliminamos un usuario luego de que este autenticado
        btnEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenemos el usuario actual
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential1 = EmailAuthProvider.getCredential("pabloabrigo_66@gmail.com", String.valueOf(1234567));
                user.reauthenticate(credential1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(MainActivity.this,loginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else{
                                            Toast.makeText(getApplicationContext(), "No se pudo eliminar el Usuario", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                // Get the account de google
                GoogleSignInAccount signInAccount =
                        GoogleSignIn.getLastSignedInAccount(getApplicationContext()); //Obtenemos la ultima sesion del usuario iniciada
                if (signInAccount != null) {
                    AuthCredential credential =
                            GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    //Re-autenticar el usuario para eliminarlo
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Eliminar el usuario
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("MainActivity", "onSuccess:Usuario Eliminado");
                                        //llamar al metodo signOut para salir de aqui
                                        signOut();
                                    }
                                });
                            } else {
                                Log.e("MainActivity", "onComplete: Error al eliminar el usuario",
                                        task.getException());
                            }
                        }
                    });
                } else {
                    Log.d("dashBoard", "Error: reAuthenticateUser: user account is null");
                }
            }
        });//fin del onClick


        //Creamos un usuario de FirebaseUser que lo podemos obtener en cualquier parte
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Establecemos los campos
        userName.setText(currentUser.getDisplayName());
        userEmail.setText(currentUser.getEmail());
        userID.setText(currentUser.getUid());

        //cargamos la imagen mediante el glide
        Glide.with(this).load(currentUser.getPhotoUrl()).into(userImg);

        //Configuramos las gso para google signIn con el fin de luego desloguearnos de google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

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

    //Metodo que nos sirve para salir de la sesion de google con el correo que esta establecido
    private void signOut() {
        //sign out de firebase
        FirebaseAuth.getInstance().signOut();
        //sign out de "google sign in"
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //regresar al login screen o MainActivity
                //Abrir mainActivity para que inicie sesión o sign in otra vez.
                Intent loginActivity = new Intent(getApplicationContext(), loginActivity.class);
                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginActivity);
                MainActivity.this.finish();
            }
        });
    }
}

