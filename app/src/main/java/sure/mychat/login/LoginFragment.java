package sure.mychat.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sure.mychat.homepage.MainActivity;
import sure.mychat.R;


public class LoginFragment extends Fragment implements LoginContract.View {

    // 弹出框
    private ProgressDialog mDialog;
    // username 输入框
    private EditText usernameEdit;
    // 密码输入框
    private EditText passwordEdit;
    // 注册按钮
    private Button registerButton;
    // 登录按钮
    private Button loginButton;


    private LoginContract.Presenter presenter;


    public LoginFragment(){

    }

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_login,container,false);

        initViews(view);

        presenter.start();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register();
                showLoding();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
                showLoding();
            }
        });
        return view;
    }

    @Override
    public void showLoding() {
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("请稍后...");
        mDialog.show();
    }

    @Override
    public void stopLoding() {
        mDialog.dismiss();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFail() {

    }

    @Override
    public String getPassword() {
        return passwordEdit.getText().toString().trim();
    }

    @Override
    public String getUsername() {
        return usernameEdit.getText().toString().trim();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        if(presenter!=null){
            this.presenter=presenter;
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViews(View view) {
        registerButton=(Button)view.findViewById(R.id.button_register);
        loginButton=(Button)view.findViewById(R.id.button_login);
        usernameEdit=(EditText)view.findViewById(R.id.edit_username);
        passwordEdit=(EditText)view.findViewById(R.id.edit_password);
    }
}
