package sure.mychat.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sure.mychat.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment fragment=new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_framelayout,fragment)
                .commit();
        new LoginPresenter(LoginActivity.this,fragment);
    }
}
