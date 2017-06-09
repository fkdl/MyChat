package sure.mychat.add_friend;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sure.mychat.R;

public class add_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.activity_add);
        super.onCreate(savedInstanceState);
        AddContactFragment fragment=new AddContactFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.add_framelayout,fragment)
                .commit();
        new AddContactPresenter(add_activity.this,fragment);
    }
}
