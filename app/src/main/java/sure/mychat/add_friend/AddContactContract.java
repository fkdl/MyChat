package sure.mychat.add_friend;

import android.widget.Toast;

import sure.mychat.BasePresenter;
import sure.mychat.BaseView;



public interface AddContactContract {
    interface View extends BaseView<Presenter> {
        //返回操作
        void back();
        //显示搜索到的联系人
        void showContact();
        //添加成功
        void    addSuccess();
        //添加失败
        void addFail();
        //获取联系人名称
        String getSearchName();
        //搜索成功
        void searchSuccess(String name);
        //搜索失败
        void searchFail();
        //获取搜索到的联系人名称
        String getNameText();
        //显示弹出框
        void showDialog();
        //取消弹出框
        void stopDialog();
        //显示Toast
        void showToast(String msg);
    }
    interface Presenter extends BasePresenter {
        //查询联系人
        void searchContact();
        //添加联系人
        void addContact();
    }
}
