/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sure.mychat.add_friend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseAlertDialog;

import sure.mychat.R;
import sure.mychat.chat.ECChatActivity;


public class AddContactFragment extends Fragment implements AddContactContract.View {
    private EditText editText;
    private RelativeLayout searchedUserLayout;
    private TextView nameText;
    private Button searchBtn;
    private Button addBtn;
    private String toAddUsername;
    private ProgressDialog progressDialog;
    private AddContactContract.Presenter presenter;
    private TextView mTextView;
    private String searchName;

    public AddContactFragment() {

    }

    public static AddContactFragment newInstance() {
        return new AddContactFragment();
    }

    @Override
    public void showContact() {

    }

    @Override
    public void setPresenter(AddContactContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }

    }

    @Override
    public void back() {
        getActivity().finish();
    }

    @Override
    public void showToast(String msg) {
        if (msg != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void addSuccess() {
        Intent intent = new Intent(getActivity(), ECChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, getNameText());
        startActivity(intent);
        back();
    }

    @Override
    public void addFail() {
        new EaseAlertDialog(this.getContext(), R.string.not_add_myself).show();
    }

    @Override
    public void searchFail() {
        new EaseAlertDialog(this.getContext(), R.string.Please_enter_a_username).show();
    }

    @Override
    public void searchSuccess(String name) {
        searchedUserLayout.setVisibility(View.VISIBLE);
        nameText.setText(name);
    }

    @Override
    public void showDialog() {
        progressDialog = new ProgressDialog(getContext());
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void stopDialog() {
        progressDialog.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.em_activity_add_contact, container, false);
        initViews(view);
        presenter.start();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchContact();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                presenter.addContact();
            }
        });

        return view;
    }


    @Override
    public void initViews(View view) {
        mTextView = (TextView) view.findViewById(R.id.add_list_friends);
        editText = (EditText) view.findViewById(R.id.edit_note);
        String strAdd = view.getResources().getString(R.string.start_chat);
        mTextView.setText(strAdd);
        String strUserName = getResources().getString(R.string.user_name);
        editText.setHint(strUserName);
        searchedUserLayout = (RelativeLayout) view.findViewById(R.id.ll_user);
        nameText = (TextView) view.findViewById(R.id.name);
        searchBtn = (Button) view.findViewById(R.id.search);
        addBtn = (Button) view.findViewById(R.id.indicator);
    }

    @Override
    public String getSearchName() {
        return editText.getText().toString();
    }

    @Override
    public String getNameText() {
        return nameText.getText().toString();
    }

    /**
     * add contact
     *
     * @param view
     */
    public void addContact(View view) {
        /*if(DemoHelper.getInstance().getContactList().containsKey(nameText.getText().toString())){
            //let the user know the contact already in your contact list
		    if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(nameText.getText().toString())){
		        new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
		        return;
		    }
			new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
			return;
		}*/


    }


}
