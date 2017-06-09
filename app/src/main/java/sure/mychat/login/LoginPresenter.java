package sure.mychat.login;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private Context context;
    private String errorMesg;
    private int error_i;
    private String error_s;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    view.stopLoding();
                    view.showToast(errorMesg);
                    break;
                case 1:
                    view.stopLoding();
                    view.showToast("用户名和密码不能为空");
                default:
                    break;
            }
        }

    };

    public LoginPresenter(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void login() {
        String username = view.getUsername();
        String password = view.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            mHandler.sendMessage(msg);
            return;
        }
        EMClient.getInstance().login(username, password, new EMCallBack() {
            /**
             * 登陆成功的回调
             */
            @Override
            public void onSuccess() {
                view.stopLoding();
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                // 加载所有群组到内存，如果使用了群组的话
                // EMClient.getInstance().groupManager().loadAllGroups();
                // 登录成功跳转界面
                view.loginSuccess();
            }

            @Override
            public void onError(int i, String s) {
                error_i = i;
                error_s = s;
                /**
                 * 关于错误码可以参考官方api详细说明
                 * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                 */
                switch (error_i) {
                    // 网络异常 2
                    case EMError.NETWORK_ERROR:
                        errorMesg = "网络错误 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg1 = mHandler.obtainMessage();
                        msg1.what = 0;
                        mHandler.sendMessage(msg1);
                        break;
                    // 无效的用户名 101
                    case EMError.INVALID_USER_NAME:
                        errorMesg = "无效的用户名 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg2 = mHandler.obtainMessage();
                        msg2.what = 0;
                        mHandler.sendMessage(msg2);
                        break;
                    // 无效的密码 102
                    case EMError.INVALID_PASSWORD:
                        errorMesg = "无效的密码 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg3 = mHandler.obtainMessage();
                        msg3.what = 0;
                        mHandler.sendMessage(msg3);
                        break;
                    // 用户认证失败，用户名或密码错误 202
                    case EMError.USER_AUTHENTICATION_FAILED:
                        errorMesg = "用户认证失败，用户名或密码错误 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg4 = mHandler.obtainMessage();
                        msg4.what = 0;
                        mHandler.sendMessage(msg4);
                        break;
                    // 用户不存在 204
                    case EMError.USER_NOT_FOUND:
                        errorMesg = "用户不存在 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg5 = mHandler.obtainMessage();
                        msg5.what = 0;
                        mHandler.sendMessage(msg5);
                        break;
                    // 无法访问到服务器 300
                    case EMError.SERVER_NOT_REACHABLE:
                        errorMesg = "无法访问到服务器 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg6 = mHandler.obtainMessage();
                        msg6.what = 0;
                        mHandler.sendMessage(msg6);
                        break;
                    // 等待服务器响应超时 301
                    case EMError.SERVER_TIMEOUT:
                        errorMesg = "等待服务器响应超时 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg7 = mHandler.obtainMessage();
                        msg7.what = 0;
                        mHandler.sendMessage(msg7);
                        break;
                    // 服务器繁忙 302
                    case EMError.SERVER_BUSY:
                        errorMesg = "服务器繁忙 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg8 = mHandler.obtainMessage();
                        msg8.what = 0;
                        mHandler.sendMessage(msg8);
                        break;
                    // 未知 Server 异常 303 一般断网会出现这个错误
                    case EMError.SERVER_UNKNOWN_ERROR:
                        errorMesg = "未知的服务器异常 code: " + error_i + "\n" + "message:" + error_s;
                        Message msg9 = mHandler.obtainMessage();
                        msg9.what = 0;
                        mHandler.sendMessage(msg9);
                        break;
                    default:
                        errorMesg = "ml_sign_in_failed code: " + error_i + "\n" + "message:" + error_s;
                        Message msg0 = mHandler.obtainMessage();
                        msg0.what = 0;
                        mHandler.sendMessage(msg0);
                        break;
                }

            }


            @Override
            public void onProgress(int i, String s) {

            }
        });


    }


    @Override
    public void register() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String username = view.getUsername();
                    String password = view.getPassword();
                    EMClient.getInstance().createAccount(username, password);
                    view.stopLoding();
                    errorMesg = "注册成功";
                    Message msg3 = mHandler.obtainMessage();
                    msg3.what = 0;
                    mHandler.sendMessage(msg3);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    /**
                     * 关于错误码可以参考官方api详细说明
                     * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                     */
                    int errorCode = e.getErrorCode();
                    String message = e.getMessage();
                    switch (errorCode) {
                        // 网络错误
                        case EMError.NETWORK_ERROR:
                            errorMesg = "网络错误 code: " + errorCode + "\n" + "message:" + message;
                            Message msg1 = mHandler.obtainMessage();
                            msg1.what = 0;
                            mHandler.sendMessage(msg1);
                            break;
                        // 用户已存在
                        case EMError.USER_ALREADY_EXIST:
                            errorMesg = "用户已存在 code: " + errorCode + "\n" + "message:" + message;
                            Message msg2 = mHandler.obtainMessage();
                            msg2.what = 0;
                            mHandler.sendMessage(msg2);
                            break;
                        // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                        case EMError.USER_ILLEGAL_ARGUMENT:
                            errorMesg = "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + "\n" + "message:" + message;
                            Message msg3 = mHandler.obtainMessage();
                            msg3.what = 0;
                            mHandler.sendMessage(msg3);
                            break;
                        // 服务器未知错误
                        case EMError.SERVER_UNKNOWN_ERROR:
                            errorMesg = "服务器未知错误 code: " + errorCode + "\n" + "message:" + message;
                            Message msg4 = mHandler.obtainMessage();
                            msg4.what = 0;
                            mHandler.sendMessage(msg4);
                            break;
                        case EMError.USER_REG_FAILED:
                            errorMesg = "账户注册失败 code: " + errorCode + "\n" + "message:" + message;
                            Message msg5 = mHandler.obtainMessage();
                            msg5.what = 0;
                            mHandler.sendMessage(msg5);
                            break;
                        default:
                            errorMesg = "ml_sign_up_failed code: " + errorCode + "\n" + "message:" + message;
                            Message msg6 = mHandler.obtainMessage();
                            msg6.what = 0;
                            mHandler.sendMessage(msg6);
                            break;
                    }
                }
            }
        }).start();
    }

    @Override
    public void start() {

    }

}
