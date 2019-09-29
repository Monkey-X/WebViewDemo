package com.xu.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.widget.FrameLayout;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mWebContainer;
    private BridgeWebView mBridgeWebView;
    private long firstClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebContainer = findViewById(R.id.web_container);

        mBridgeWebView = new BridgeWebView(getApplicationContext());
        WebSettings settings = mBridgeWebView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:54.0) Gecko/20100101 Firefox/54.0");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebContainer.addView(mBridgeWebView);
        mBridgeWebView.loadUrl("https://www.baidu.com");

        registerMethod();
    }

    private void registerMethod() {
        mBridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

            }
        });

        mBridgeWebView.registerHandler("webCallJava", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    @Override
    public void onBackPressed() {
        if (mBridgeWebView != null && mBridgeWebView.canGoBack()) {
            mBridgeWebView.goBackOrForward(-1);
        } else {
            if (System.currentTimeMillis() - firstClickTime < 2000) {
                finish();
            } else {
                firstClickTime = System.currentTimeMillis();
            }
        }
    }

    private void destroyWebView() {
        mWebContainer.removeAllViews();

        if (mBridgeWebView != null) {
            mBridgeWebView.clearHistory();
            mBridgeWebView.clearCache(true);
            mBridgeWebView.loadUrl("about:blank");
            mBridgeWebView.freeMemory();
            mBridgeWebView.pauseTimers();
            mBridgeWebView.destroy();
            mBridgeWebView = null;
        }
    }
}
