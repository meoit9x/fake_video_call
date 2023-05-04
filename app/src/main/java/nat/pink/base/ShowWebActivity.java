package nat.pink.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ShowWebActivity extends AppCompatActivity {
    WebView webView,webView1,webView2;
    ProgressBar progressBar;
    int webviewID = 1;
    String countryCodeValue;
    LinearLayout mainMenu;
    String HomeChangerURL ="https://google.com",homeURL="https://google.com",PACKAGE_NAME="",AccessTime="0",CustomercareURL="https://google.com";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Webview);
        setContentView(R.layout.activity_show_web);

        SharedPreferences sharedPreferences = getSharedPreferences("sysdata",MODE_PRIVATE);

        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        countryCodeValue = tm.getNetworkCountryIso();

        countryCodeValue="VN";

        mainMenu = findViewById(R.id.mainMenu);

        initView();

        progressBar.setMax(100);

        setupWebView();

        PACKAGE_NAME = getApplicationContext().getPackageName();

        AccessTime = sharedPreferences.getString("AccessTime", "0");

        webView.loadUrl(homeURL+"?package="+PACKAGE_NAME+"&lct="+countryCodeValue+"&AccessTime="+AccessTime+"&appName="+getApplicationInfo().loadLabel(getPackageManager()).toString());

    }



    private void initView() {


        webView = findViewById(R.id.webView);

        webView1 = findViewById(R.id.webView1);
        webView2 = findViewById(R.id.webView2);

        progressBar = findViewById(R.id.progressBar);

    }



    private void setupWebView() {

        webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setSaveFormData(true);

        this.webView.setWebChromeClient(
                new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        progressBar.setProgress(newProgress);
                        if (newProgress == 100) {
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                        WebView newWebView = new WebView(ShowWebActivity.this);
                        newWebView.getSettings().setJavaScriptEnabled(true);
                        newWebView.getSettings().setSupportZoom(true);
                        newWebView.getSettings().setBuiltInZoomControls(true);
                        newWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                        newWebView.getSettings().setSupportMultipleWindows(true);
                        newWebView.getSettings().setDomStorageEnabled(true);
                        newWebView.getSettings().setLoadWithOverviewMode(true);
                        newWebView.getSettings().setJavaScriptEnabled(true);
                        newWebView.getSettings().setSaveFormData(true);
                        newWebView.getSettings().setAllowFileAccess(true);
                        newWebView.getSettings().setAllowFileAccess(true);
                        newWebView.getSettings().setAllowContentAccess(true);
                        newWebView.getSettings().setSupportMultipleWindows(true);
                        newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                        newWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                        newWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");

                        view.addView(newWebView);
                        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                        transport.setWebView(newWebView);
                        resultMsg.sendToTarget();

                        newWebView.setWebViewClient(new WebViewClient() {

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                Log.d("Load URL: ",url);
                                webView1.setVisibility(View.VISIBLE);
                                webView.setVisibility(View.GONE);
                                webView1.loadUrl(url);
                                webviewID=2;
                                mainMenu.setVisibility(View.GONE);


                                return true;
                            }


                        });
                        return true;
                    }
                });

        this.webView.getSettings().setAllowFileAccess(true);

        this.webView.getSettings().setAllowFileAccess(true);
        this.webView.getSettings().setAllowContentAccess(true);
        this.webView.getSettings().setSupportMultipleWindows(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setSupportMultipleWindows(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");
        //webview 2
        webView1.getSettings().setJavaScriptEnabled(true);
        this.webView1.setWebViewClient(new WebViewClient());
        this.webView1.getSettings().setDomStorageEnabled(true);
        this.webView1.getSettings().setLoadWithOverviewMode(true);
        this.webView1.getSettings().setJavaScriptEnabled(true);
        this.webView1.getSettings().setSaveFormData(true);
        this.webView1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

                WebView newWebView = new WebView(ShowWebActivity.this);
                newWebView.getSettings().setJavaScriptEnabled(true);
                newWebView.getSettings().setSupportZoom(true);
                newWebView.getSettings().setBuiltInZoomControls(true);
                newWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                newWebView.getSettings().setSupportMultipleWindows(true);
                newWebView.getSettings().setDomStorageEnabled(true);
                newWebView.getSettings().setLoadWithOverviewMode(true);
                newWebView.getSettings().setJavaScriptEnabled(true);
                newWebView.getSettings().setSaveFormData(true);
                newWebView.getSettings().setAllowFileAccess(true);
                newWebView.getSettings().setAllowFileAccess(true);
                newWebView.getSettings().setAllowContentAccess(true);
                newWebView.getSettings().setSupportMultipleWindows(true);
                newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                newWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                newWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");

                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //view.loadUrl(url);
                        Log.d("Open URL", url);
                        webView2.loadUrl(url);
                        webView2.setVisibility(View.VISIBLE);
                        webView1.setVisibility(View.GONE);
                        webView.setVisibility(View.GONE);
                        webviewID=3;
                        mainMenu.setVisibility(View.GONE);
                        Log.d("webviewID",webviewID+"");

                        return true;
                    }
                });

                return true;


            }
        });
        this.webView1.getSettings().setAllowFileAccess(true);
        this.webView1.getSettings().setAllowFileAccess(true);
        this.webView1.getSettings().setAllowContentAccess(true);
        this.webView1.getSettings().setSupportMultipleWindows(true);
        this.webView1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView1.getSettings().setJavaScriptEnabled(true);
        this.webView1.getSettings().setSupportMultipleWindows(true);
        this.webView1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.webView1.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");

        //webview 3
        webView2.getSettings().setJavaScriptEnabled(true);
        this.webView2.setWebViewClient(new WebViewClient());
        this.webView2.getSettings().setDomStorageEnabled(true);
        this.webView2.getSettings().setLoadWithOverviewMode(true);
        this.webView2.getSettings().setJavaScriptEnabled(true);
        this.webView2.getSettings().setSaveFormData(true);
        this.webView2.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

                WebView newWebView = new WebView(ShowWebActivity.this);
                newWebView.getSettings().setJavaScriptEnabled(true);
                newWebView.getSettings().setSupportZoom(true);
                newWebView.getSettings().setBuiltInZoomControls(true);
                newWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
                newWebView.getSettings().setSupportMultipleWindows(true);
                newWebView.getSettings().setDomStorageEnabled(true);
                newWebView.getSettings().setLoadWithOverviewMode(true);
                newWebView.getSettings().setJavaScriptEnabled(true);
                newWebView.getSettings().setSaveFormData(true);
                newWebView.getSettings().setAllowFileAccess(true);
                newWebView.getSettings().setAllowFileAccess(true);
                newWebView.getSettings().setAllowContentAccess(true);
                newWebView.getSettings().setSupportMultipleWindows(true);
                newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                newWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                newWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");

                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //view.loadUrl(url);
                        Log.d("Open URL", url);
                        webView2.loadUrl(url);
                        mainMenu.setVisibility(View.GONE);
                        return true;
                    }
                });

                return true;


            }
        });
        this.webView2.getSettings().setAllowFileAccess(true);
        this.webView2.getSettings().setAllowFileAccess(true);
        this.webView2.getSettings().setAllowContentAccess(true);
        this.webView2.getSettings().setSupportMultipleWindows(true);
        this.webView2.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView2.getSettings().setJavaScriptEnabled(true);
        this.webView2.getSettings().setSupportMultipleWindows(true);
        this.webView2.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView2.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        this.webView2.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65 Mobile Safari/537.36");



        SharedPreferences sharedPreferences = getSharedPreferences("sysdata",MODE_PRIVATE);
        String RegisterURL = sharedPreferences.getString("RegisterURL", "");
        String AccessTime = sharedPreferences.getString("AccessTime", "0");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);

                int AccessTimeC = Integer.parseInt(AccessTime);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("AccessTime", String.valueOf(AccessTimeC+1));
                editor.commit();


                webView.evaluateJavascript("(function(){  document.getElementsByClassName('user-links')[0].innerHTML=`<a data-v-61fc9f0a='' href='#/login' class='nav-link font-link' data-v-f3f7587a=''>Đăng Nhập</a> | <a data-v-61fc9f0a='' href='"+RegisterURL+countryCodeValue+"' class='nav-link font-link' data-v-f3f7587a=''>Đăng Ký</a>`})();", paRes -> {
                    if (paRes != null && !paRes.isEmpty() && !paRes.equals("null")) {
                        // TODO: You will use get value
                    }
                });

                webView.evaluateJavascript("(function(){setInterval(()=>{document.getElementsByClassName('row options')[0].innerHTML=`<style>body { display: flex; justify-content: center; align-items: center; } body { width: 100%; height: 90vh; } .button { display: block; width: 320px; max-width: 100%; margin: 0 auto; margin-bottom: 0; overflow: hidden; position: relative; transform: translatez(0); text-decoration: none; box-sizing: border-box; font-size: 24px; font-weight: normal; box-shadow: 0 9px 18px rgba(0,0,0,0.2); } .instagram { text-align: center; border-radius: 50px; padding: 26px; color: white; background: #BD3381; transition: all 0.2s ease-out 0s; } .gradient { display: block; position: absolute; top: 0; right: 0; width: 100%; height: 100%; bottom: auto; margin: auto; z-index: -1; background: radial-gradient(90px circle at top center, rgba(238,88,63,.8) 30%, rgba(255,255,255,0)); transition: all 0s ease-out 0s; transform: translatex(-140px); animation: 18s linear 0s infinite move; } @keyframes move { 0% { transform: translatex(-140px); } 25% { transform: translatex(140px); opacity: 0.3; } 50% { transform: translatex(140px); opacity: 1; background: radial-gradient(90px circle at bottom center, rgba(238,88,63,.5) 30%, rgba(255,255,255,0)); } 75% { transform: translatex(-140px); opacity: 0.3; } 100% { opacity: 1; transform: translatex(-140px); background: radial-gradient(90px circle at top center, rgba(238,88,63,.5) 30%, rgba(255,255,255,0)); } }</style><a href='"+RegisterURL+countryCodeValue+"' class='button instagram'><span class='gradient'></span>Đăng Ký</a>`;},1000)})();", paRes -> {
                    if (paRes != null && !paRes.isEmpty() && !paRes.equals("null")) {
                        // TODO: You will use get value
                    }
                });
            }
        });

        webView1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        webView2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setProgress(0);
    }


    public void closeGame(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowWebActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Đóng sảnh");
        builder.setMessage("Bạn chắc chắn muốn thoát?");
        builder.setPositiveButton("Thoát",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainMenu.setVisibility(View.VISIBLE);
                        if( webviewID==2){
                            webView.setVisibility(View.INVISIBLE);
                            String baseUrl = "https://example.com/";
                            webView1.loadDataWithBaseURL(baseUrl, "", "text/html", null, baseUrl);
                            webView.setVisibility(View.VISIBLE);
                            webviewID=1;
                        }
                        else if(webviewID==3){
                            webView2.setVisibility(View.INVISIBLE);
                            String baseUrl = "https://example.com/";
                            webView2.loadDataWithBaseURL(baseUrl, "", "text/html", null, baseUrl);
                            webView1.setVisibility(View.VISIBLE);
                            webviewID=2;
                        }else{
                        }
                    }
                });
        builder.setNegativeButton("Không Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        if( webviewID==2){
            closeGame();
        }else  if( webviewID==3){
            closeGame();
        }else{
            mainMenu.setVisibility(View.VISIBLE);
            Log.d("Close","Close webview");
            if(webView.canGoBack()) {
                webView.goBack();
                Log.d("Close","Close webview 0");
            } else {
                Log.d("Close","Close webview 1");
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
        webView1.saveState(outState);
        webView2.saveState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
        webView1.restoreState(savedInstanceState);
        webView2.restoreState(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public void changeURL(View view){
        webView.loadUrl(HomeChangerURL+"?package="+PACKAGE_NAME+"&lct="+countryCodeValue+"&AccessTime="+AccessTime+"&appName="+getApplicationInfo().loadLabel(getPackageManager()).toString());
    }

    public void home(View view){
        webView.loadUrl(homeURL+"?package="+PACKAGE_NAME+"&lct="+countryCodeValue+"&AccessTime="+AccessTime+"&appName="+getApplicationInfo().loadLabel(getPackageManager()).toString());
    }

    public void cskh(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(CustomercareURL));
        startActivity(browserIntent);
    }

}
