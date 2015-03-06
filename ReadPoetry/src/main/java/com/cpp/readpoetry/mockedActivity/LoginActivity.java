package com.cpp.readpoetry.mockedActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cpp.readpoetry.R;

/**
 * Created by Three. on 2015/3/6.
 */
public class LoginActivity extends Activity implements View.OnClickListener {


    private EditText userNameEdit;
    private EditText passWordEdit;

    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);

        initView();

    }

    private void initView() {

        findViewById(R.id.ripple_button_back).setOnClickListener(this);
        findViewById(R.id.ripple_button_confirm).setOnClickListener(this);

        userNameEdit = (EditText) findViewById(R.id.usr_edit);
        passWordEdit = (EditText) findViewById(R.id.password_edit);

        registerText = (TextView) findViewById(R.id.register_text);


        SpannableString spanString = new SpannableString("注册");
        spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(LoginActivity.this, "is", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
            }
        }, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerText.setHighlightColor(Color.TRANSPARENT);
        registerText.append(spanString);
        registerText.setMovementMethod(LinkMovementMethod.getInstance());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ripple_button_back:
                finish();
                break;
            case R.id.ripple_button_confirm:
                userNameEdit.setError("Error...");
                break;
            default:
        }
    }
}
