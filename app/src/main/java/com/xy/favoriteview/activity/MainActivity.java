package com.xy.favoriteview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xy.favoriteview.R;
import com.xy.favoriteview.view.FavoriteView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FavoriteView favoriteView;
    private Button cleanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.favoriteView = (FavoriteView) this.findViewById(R.id.view_favorite);
        this.cleanButton = (Button) this.findViewById(R.id.btn_clean);

        this.favoriteView.setOnClickListener(this);
        this.cleanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_favorite:
                this.favoriteView.launchAnim();
                break;

            case R.id.btn_clean:
                favoriteView.cleanHearColor();
                break;
        }
    }
}
