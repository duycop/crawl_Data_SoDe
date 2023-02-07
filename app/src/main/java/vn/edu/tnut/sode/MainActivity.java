package vn.edu.tnut.sode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView webView_xsmb;
    WebView webView_display;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set fullscreen
        //getSupportActionBar().hide();  //hàm này ẩn TIêu đề của APP, chưa ẩn thanh trạng thái của điện thoại

        //ko sài đc với phiên bản biên dịch này
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        //dòng này : OK == làm ẩn thanh trạng thái của đt
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //TestThoiTiet();
        //trỏ tới đối tượng trên giao diện
        webView_display = findViewById(R.id.webView);
        //cho phép chạy js
        webView_display.getSettings().setJavaScriptEnabled(true);
        webView_display.addJavascriptInterface(this, "mo_rong");
        webView_display.loadUrl("file:///android_asset/sode.html");

        //trỏ tới đối tượng trong RAM
        webView_xsmb = new WebView(this.getApplicationContext());
        //cho phép chạy js
        webView_xsmb.getSettings().setJavaScriptEnabled(true);
        webView_xsmb.addJavascriptInterface(this, "mo_rong");

        webView_xsmb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("sode","webView_xsmb onPageFinished url="+url);
                //gọi 1 đoạn code js chạy trong trang
                //code js này ko có sẵn trong trang
                //$('.special-prize')[0].innerText)
                String js1="javascript:window.mo_rong.nhan_sode($('.special-prize')[0].innerText);";
                js1+="window.mo_rong.nhan_ngay($($('.section-header')[1]).find('a')[2].innerText.slice(-10))";
                webView_xsmb.loadUrl(js1);
                //
            }
        });
        webView_xsmb.loadUrl("https://xoso.com.vn/xo-so-mien-bac/xsmb-p1.html");

    }
/*
    void TestThoiTiet(){
        try {
            Log.d("thoitiet","xxxhello bat dau vào test thời tiết");
            Log.d("thoitiet","xxx ok");
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Log.d("thoitiet","build ok");
            MediaType mediaType = MediaType.parse("text/plain");
            Log.d("thoitiet","media ok");
            RequestBody body = RequestBody.create(mediaType, "");
            Log.d("thoitiet","body ok");
            Request request = new Request.Builder()
                    .url("https://service.baomoi.com/weatherlist.json")
                    .method("GET", body)
                    .addHeader("referer", "https://baomoi.com/")
                    .build();
            Log.d("thoitiet","request ok");
            Response response = client.newCall(request).execute();
            Log.d("thoitiet","response ok");
            String jsonString = response.body().string();
            Log.d("thoitiet",jsonString);
        } catch (Exception e) {
            Log.d("thoitiet","Error: "+e.getMessage());
        }
    }
*/
    String giai_db="Loading...", ngay="Loading...";
    @JavascriptInterface
    public void nhan_sode(String giai_db_js){
        //nhận đc số đề thì https...
        if(giai_db_js!=null && giai_db_js !="")
            giai_db=giai_db_js;
        Log.d("sode","nhan dc giai_db_js = "+giai_db_js);
        //truyền xuống trang display: sode.html local

        //chưa truyền đc, có gì đó sai sai
        //webView_display.loadUrl("javascript:hienthisode('1234')");
    }
    @JavascriptInterface
    public void nhan_ngay(String ngay_js){
        //nhận đc ngay tu trang https
        if(ngay_js!=null && ngay_js !="")
            ngay=ngay_js;
        Log.d("sode","nhan dc ngay_js = "+ngay_js);
    }
    int dem=0;
    @JavascriptInterface
    public String get_sode(){
        dem++;
        return "{\"ngay\":\""+ngay+"\",\"giai_db\":\""+giai_db+"\",\"dem\":"+dem+"}";
    }
}