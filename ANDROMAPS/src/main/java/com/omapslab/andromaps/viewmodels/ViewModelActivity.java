package com.omapslab.andromaps.viewmodels;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * MVP Core
 * Generate ViewModels Base For all Activities
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public abstract class ViewModelActivity extends BaseActivity {
    protected IViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewModel();
        viewModel.onCreate(new Handler(Looper.getMainLooper()));
    }

    abstract protected void createViewModel();


    protected void setNoTitle(AppCompatActivity a) {
        a.requestWindowFeature(Window.FEATURE_NO_TITLE);
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        a.getSupportActionBar().hide();
    }

    protected void setHeaderThema(AppCompatActivity a, int headerLayout, HeaderCustomThemeListener listener) {
        ActionBar actionBar = a.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(a);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        View view = layoutInflater.inflate(headerLayout, null);
        actionBar.setCustomView(view, layout);
        listener.onHeaderMenuAction(view);
        actionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) actionBar.getCustomView().getParent();
        parent.setContentInsetsAbsolute(0,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    public interface HeaderCustomThemeListener {
        void onHeaderMenuAction(View v);
    }
}
