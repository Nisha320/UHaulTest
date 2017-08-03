package com.project.uhaultest.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.uhaultest.R;

public class CustomProgressDialog extends ProgressDialog {

    TextView loadingText;
    RelativeLayout loadingLayout;
    ImageView loadingImage;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_spinner_layout);
        init();
        setCancelable(false);
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_grey));
        loadingText.setTextColor(ContextCompat.getColor(getContext(), R.color.light_black));

    }

    private void init() {
        loadingText = (TextView) findViewById(R.id.loading_text);
        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        loadingImage = (ImageView) findViewById(R.id.loading_image);
    }

    public void updateLoadingText(String text) {
        if (loadingText != null) {
            loadingText.setText(text);
        }
    }

    public void updateImage(int drawableId) {
        if (loadingImage != null) {
            loadingImage.setBackgroundResource(drawableId);
        }
    }
}