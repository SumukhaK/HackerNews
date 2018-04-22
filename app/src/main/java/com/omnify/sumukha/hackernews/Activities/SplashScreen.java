package com.omnify.sumukha.hackernews.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.Scopes;
import com.omnify.sumukha.hackernews.R;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    Button googleSignInBtn;
    private static final String GOOGLE_TOS_URL = "https://www.google.com/policies/terms/";
    private static final String GOOGLE_PRIVACY_POLICY_URL = "https://www.google.com/policies/privacy/";
    private static final int RC_SIGN_IN = 100;
    SharedPreferences sharedPreferences;
    boolean savedGoogleLogin;
    String savedGoogleLoginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        googleSignInBtn = findViewById(R.id.google_login_bouuton);

        sharedPreferences = getSharedPreferences("googleSignIn", Context.MODE_PRIVATE);
        savedGoogleLogin = sharedPreferences.getBoolean("saved_login", false);

        if(savedGoogleLogin){
            savedGoogleLoginId = sharedPreferences.getString("login_id","abc@gmail.com");
            Toast.makeText(SplashScreen.this,"Logging in with "+savedGoogleLoginId,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(getSelectedProviders())
                                .setTosUrl(GOOGLE_TOS_URL)
                                .setPrivacyPolicyUrl(GOOGLE_PRIVACY_POLICY_URL)
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }

    }


    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == RESULT_OK) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("saved_login",true);
            editor.putString("login_id",response.getEmail());
            editor.commit();
            showSnackbar(response.getEmail());
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar("Cancelled");
                return;
            }

            if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar("No network");
                return;
            }

            showSnackbar("Unknown err");
            //Log.e(TAG, "Sign-in error: ", response.getError());
        }
    }

    private void showSnackbar(String errorMessageRes) {
        Toast.makeText(SplashScreen.this, errorMessageRes, Toast.LENGTH_LONG).show();
    }


    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();

        selectedProviders.add(
                new AuthUI.IdpConfig.GoogleBuilder().setScopes(getGoogleScopes()).build());


        return selectedProviders;
    }


    private List<String> getGoogleScopes() {
        List<String> result = new ArrayList<>();
        result.add(Scopes.DRIVE_FILE);
        return result;
    }


}
