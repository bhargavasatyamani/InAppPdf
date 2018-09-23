package com.example.bhargav_2.inapppdf;

import android.annotation.SuppressLint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    File file;
    FileOutputStream mfileOutputStream;
    PdfDocument document;
    PdfDocument.PageInfo pageInfo;
    PdfDocument.Page page;
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView=(WebView)findViewById(R.id.myWebView);
        mWebView.loadUrl("https://www.lipsum.com/");

        mWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view,String url){
                //Toast.makeText(MainActivity.this, "Page loaded", Toast.LENGTH_LONG).show();
                createPDF();
                Toast.makeText(MainActivity.this, "File Created", Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void createPDF(){
        int content_width=mWebView.getWidth();
        int content_height=mWebView.getHeight();
        File folder=getCacheDir();
        file=new File(folder,"mypdf.pdf");
        document=new PdfDocument();
        pageInfo=new PdfDocument.PageInfo.Builder(content_width,content_height,1).create();
        page=document.startPage(pageInfo);
        mWebView.draw(page.getCanvas());
        document.finishPage(page);

        try {
            mfileOutputStream=new FileOutputStream(file);
            document.writeTo(mfileOutputStream);
            mfileOutputStream.flush();
            mfileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();


    }


}
