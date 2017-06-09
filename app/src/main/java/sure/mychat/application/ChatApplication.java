package sure.mychat.application;


import android.app.Application;


import com.hyphenate.chat.EMClient;

import com.hyphenate.easeui.controller.EaseUI;


public class ChatApplication extends Application {
    private static ChatApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        EaseUI.getInstance().init(this,null);
        EMClient.getInstance().setDebugMode(true);
    }
    public static ChatApplication getInstance() {
        return instance;
    }
}
