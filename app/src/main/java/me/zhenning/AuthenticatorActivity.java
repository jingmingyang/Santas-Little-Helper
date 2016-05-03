package me.zhenning;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import me.chayut.santaslittlehelper.R;

/**
 * Created by jiang on 2016/05/03.
 */
public class AuthenticatorActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public final static String ARG_ACCOUNT_TYPE = "ARG_ACCOUNT_TYPE";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "ARG_IS_ADDING_NEW_ACCOUNT";

    private AccountManager mAccountManager;

    final EditText AddEmailAccount = (EditText) findViewById(R.id.add_account_email);

    final EditText AddAccountPassword = (EditText) findViewById(R.id.add_account_password);

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private boolean mValid = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "AuthenticatorActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);

        mAccountManager = AccountManager.get(this);

        final String currentEmail = AddEmailAccount.getText().toString().trim();

        AddEmailAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentEmail.matches(emailPattern) && (s.length() > 0)){
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    mValid = true;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.Account_Create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mValid)
                    addAccountToAccountManager();
                else
                    Toast.makeText(getApplicationContext(),
                            "You need a valid email address!",
                            Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void addAccountToAccountManager(){

        String accountName = AddEmailAccount.getText().toString().trim();
        String userPassword = AddAccountPassword.getText().toString().trim();

        Account newAccount = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
        mAccountManager.addAccountExplicitly(newAccount, userPassword, null);
        mAccountManager.setAuthToken(newAccount,
                AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,
                AccountGeneral.Santa_AuthToken);
    }

}
