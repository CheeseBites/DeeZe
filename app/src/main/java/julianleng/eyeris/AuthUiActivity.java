
package julianleng.eyeris;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthUiActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;

    @BindView(R.id.default_theme)
    RadioButton mUseDefaultTheme;

    @BindView(R.id.green_theme)
    RadioButton mUseGreenTheme;

    @BindView(R.id.purple_theme)
    RadioButton mUsePurpleTheme;

    @BindView(R.id.dark_theme)
    RadioButton mUseDarkTheme;

    @BindView(R.id.email_provider)
    RadioButton mUseEmailProvider;

    @BindView(R.id.google_provider)
    RadioButton mUseGoogleProvider;

    @BindView(R.id.facebook_provider)
    RadioButton mUseFacebookProvider;

    @BindView(R.id.sign_in)
    Button mSignIn;

    @BindView(R.id.root)
    View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(SignedInActivity.createIntent(this, null));
            finish();
        }

        setContentView(R.layout.auth_ui_layout);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.sign_in)
    public void signIn(View view) {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(getSelectedTheme())
                        .setLogo(R.drawable.app_icon)
                        .setProviders(getSelectedProviders())
                        .setIsSmartLockEnabled(true)
                        .setAllowNewEmailAccounts(true)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
        }
    }

    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {
            finish();
        } else {
            // Sign in failed
            if (response == null) {
                // User pressed back button
                showSnackbar(R.string.sign_in_cancelled);
                return;
            }

            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                showSnackbar(R.string.no_internet_connection);
            }
        }
    }

    @MainThread
    @StyleRes
    private int getSelectedTheme() {
        if (mUseDefaultTheme.isChecked()) {
            return AuthUI.getDefaultTheme();
        }

        if (mUsePurpleTheme.isChecked()) {
            return R.style.PurpleTheme;
        }

        if (mUseDarkTheme.isChecked()) {
            return R.style.DarkTheme;
        }

        return R.style.GreenTheme;
    }


    @MainThread
    private List<IdpConfig> getSelectedProviders() {
        List<IdpConfig> selectedProviders = new ArrayList<>();

        if (mUseEmailProvider.isChecked()) {
            selectedProviders.add(new IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        }

        if (mUseFacebookProvider.isChecked()) {
            selectedProviders.add(
                    new IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER)
                            .build());
        }

        if (mUseGoogleProvider.isChecked()) {
            selectedProviders.add(
                    new IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER)
                            .build());
        }

        return selectedProviders;
    }


    @MainThread
    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, AuthUiActivity.class);
        return in;
    }
}