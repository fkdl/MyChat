package sure.mychat.login;


import sure.mychat.BasePresenter;
import sure.mychat.BaseView;

public interface LoginContract {
    interface View extends BaseView<Presenter>{
        //开始加载
        void showLoding();
        //停止加载
        void stopLoding();
        //登录成功
        void loginSuccess();
        //登录失败
        void loginFail();
        //注册成功
        void registerSuccess();
        //注册失败
        void registerFail();
        //获得用户名
        String getUsername();
        //获得密码
        String getPassword();
        //显示Toast
        void showToast(String message);
    }
    interface Presenter extends BasePresenter{
        //登录
        void login();
        //注册
        void register();
    }
}
