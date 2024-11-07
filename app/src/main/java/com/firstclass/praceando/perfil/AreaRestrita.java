package com.firstclass.praceando.perfil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firstclass.praceando.Globals;
import com.firstclass.praceando.R;

public class AreaRestrita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_area_restrita);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Globals globals = (Globals) getApplication();
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        if (globals.getPremium())
            webView.loadUrl("http://ec2-52-204-64-214.compute-1.amazonaws.com:5001/dashboard/"+globals.getId());
        else
            webView.loadUrl("https://app.powerbi.com/view?r=eyJrIjoiMWQyMzMzMmQtMzViNi00NzBjLWI3NzAtNmIyYWFiOGY4Nzg5IiwidCI6ImIxNDhmMTRjLTIzOTctNDAyYy1hYjZhLTFiNDcxMTE3N2FjMCJ9");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
