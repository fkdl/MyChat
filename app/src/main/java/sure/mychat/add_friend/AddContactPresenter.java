package sure.mychat.add_friend;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;

import sure.mychat.R;

public class AddContactPresenter implements AddContactContract.Presenter {
    private AddContactContract.View view;
    private Context context;
    private int flag = 1;
    private String message;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    view.stopDialog();
                    //view.showToast(message);
                    view.addSuccess();
                    break;
                case 1:
                    view.stopDialog();
                    break;
                case 2:
                    view.stopDialog();
                    //view.showToast(null);
                    view.addSuccess();
                    break;
            }
        }

    };

    public AddContactPresenter(Context context, AddContactContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchContact() {
        String name = view.getSearchName();
        if (TextUtils.isEmpty(name)) {
            view.searchFail();
        } else {
            view.searchSuccess(name);
        }
    }

    @Override
    public void addContact() {
        if (EMClient.getInstance().getCurrentUser().equals(view.getNameText())) {
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            mHandler.sendMessage(msg);
            view.addFail();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("1111111111111", "1111111111111111111");
                        String s = context.getResources().getString(R.string.Add_a_friend);
                        EMClient.getInstance().contactManager().addContact(view.getNameText(), s);
                        message = context.getResources().getString(R.string.send_successful);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 0;
                        mHandler.sendMessage(msg);
                    } catch (final Exception e) {
                        Log.d("2222222222222222222","22222222222222222");
                        String s = e.getMessage();
                        message=null;
                        //message = context.getResources().getString(R.string.Request_add_buddy_failure) + s;
                        Message msg = mHandler.obtainMessage();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                }
            }).start();
        }
    }
}
