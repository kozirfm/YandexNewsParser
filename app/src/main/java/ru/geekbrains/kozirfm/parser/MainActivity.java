package ru.geekbrains.kozirfm.parser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        request();

    }

    public void request() {
        Handler handler = new Handler(Looper.myLooper());
        new Thread(() -> {
            try {
                Document document = Jsoup.connect("https://yandex.ru/").get();
                Elements elements = document.getElementsByClass("news__item-content ");
                List<String> s = elements.eachText();
                handler.post(() -> getNews(s));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void getNews(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(i + 1)
                    .append(". ")
                    .append(list.get(i))
                    .append(System.getProperty("line.separator"));
        }

        textView.setText(stringBuffer);

    }
}