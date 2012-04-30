package com.turnertech;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;





public class mainAct extends Activity
{
    Button button;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListenerOnButton();
        String login[] = LoadPreferences();
        EditText UN = (EditText)findViewById(R.id.txtUN);
        UN.setText(login[0]);
        EditText PW = (EditText)findViewById(R.id.txtPW);
        PW.setText(login[1]);

    }
    public void addListenerOnButton() {
        button = (Button) findViewById(R.id.btnGO);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mainAct.this.setProgressBarIndeterminateVisibility(true);
                EditText UN = (EditText)findViewById(R.id.txtUN);
                EditText PW = (EditText)findViewById(R.id.txtPW);
                String  userName = UN.getText().toString();
                String password = PW.getText().toString();
                if (userName.equals("") || password.equals(""))  {
                    Toast error = Toast.makeText(mainAct.this, "Please make sure you enter both your email and password", Toast.LENGTH_SHORT);
                    error.show();
                } else {
                    SavePreferences("user", userName);
                    SavePreferences("pass",password);
                }
                String login[] = LoadPreferences();


                int unread =  imapExec.exec(login[0], login[1]);
                if (unread != -1){

                    Toast me = Toast.makeText(mainAct.this, "Successfully marked " + unread + " messages as read.", Toast.LENGTH_SHORT);
                    me.show();
                    mainAct.this.setProgressBarIndeterminateVisibility(false);
                }
            }
        });
    }


    private void SavePreferences(String key, String value){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        LoadPreferences();
    }
    private String[] LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String strSavedMem1 = sharedPreferences.getString("user", "");
        String strSavedMem2 = sharedPreferences.getString("pass", "");
        String loginArr[] = new String[2];
        loginArr[0] = strSavedMem1;
        loginArr[1] = strSavedMem2;
        return loginArr;

    }



}

