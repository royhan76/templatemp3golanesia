package com.golanesia.myapplication;

import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String AD_UNIT_ID = "ca-app-pub-1045901976964837/4129408234";
    private static final String AD_UNIT_ID_BANNER = "ca-app-pub-1045901976964837/1934435833";
    private static final String TAG = "TAG DEBUG";
    private AdView adView;
    FrameLayout frameLayout;
    private boolean initialLayoutComplete = false;
    JcPlayerView jcPlayerView;
    private InterstitialAd mInterstitialAd;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jcPlayerView = (JcPlayerView) findViewById(R.id.jcplayer);
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<JcAudio> arrayList = new ArrayList<>();
        arrayList.add(JcAudio.createFromAssets("الفصل الأول : في الغزل وشكوى الغرام", "الفصل الأول _ في الغزل وشكوى الغرام.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل الثاني : في التحذير من هوى النفس", "الفصل الثاني _ في التحذير من هوى النفس.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل الثالث : في مدح سيد المرسلين صلى الله عليه وسلمِ", "الفصل الثالث _ في مدح سيد المرسلين صلى الله عليه وسلم.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل الرابع : في مولده عليه الصلاة والسلامُ", "الفصل الرابع _ في مولده عليه الصلاة والسلام.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل الخامس : في معجزاته صلى الله عليه وسلمُ", "الفصل الخامس _ في معجزاته صلى الله عليه وسلم.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل السادس : في شـرف الــقرآن ومدحـهُ", "الفصل السادس _ في شـرف الــقرآن ومدحـه.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل السابع : في إسرائه ومعراجه صلى الله عليه وسلمُْ", "الفصل السابع _ في إسرائه ومعراجه صلى الله عليه وسلم.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل الثامن: في جهاد النبي صلى الله عليه وسلمُِْ", "الفصل الثامن_ في جهاد النبي صلى الله عليه وسلم.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل التاسع : في التوسل بالنبي صلى الله عليه وسلمُ", "الفصل التاسع _ في التوسل بالنبي صلى الله عليه وسلم.mp3"));
        arrayList.add(JcAudio.createFromAssets("الفصل العاشر : في المناجاة وعرض الحاجاتُ", "الفصل العاشر _ في المناجاة وعرض الحاجات.mp3"));


        this.jcPlayerView.initPlaylist(arrayList, null);
        adapterSetup();
        this.frameLayout = (FrameLayout) findViewById(R.id.adContainerView);
        if (isNetworkAvaliable(this)) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                // com.google.android.gms.ads.initialization.OnInitializationCompleteListener
                public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                    loadAd();
                    loadIntertitial();
                }
            });

        }

    }

    private void loadIntertitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                MainActivity.this.mInterstitialAd = interstitialAd;
                Log.d(TAG, "IKLAN MUNCUL 1");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "IKLAN TIDAK MUNCUL 1");
            }

        });
    }
    private void loadAd() {
        adView = new AdView(this);
        frameLayout.addView(adView);
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (!initialLayoutComplete) {
                            initialLayoutComplete = true;
                            loadBanner();
                        }
                    }
                });
    }
    private void loadBanner() {
        adView.setAdUnitId(AD_UNIT_ID_BANNER);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        WindowMetrics windowMetrics = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowMetrics = getWindowManager().getCurrentWindowMetrics();
        }
        Rect bounds = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            bounds = windowMetrics.getBounds();
        }

        float adWidthPixels = frameLayout.getWidth();

        if (adWidthPixels == 0f) {
            adWidthPixels = bounds.width();
        }

        float density = getResources().getDisplayMetrics().density;
        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(0) != null && connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED) || (connectivityManager.getNetworkInfo(1) != null && connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {
            Log.i(TAG, "ono internet");
            return true;
        }
        Log.i(TAG, "internet off");
        return false;


    }

    private void adapterSetup() {
        AudioAdapter audioAdapter = new AudioAdapter(jcPlayerView.getMyPlaylist());

        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                jcPlayerView.playAudio(jcPlayerView.getMyPlaylist().get(position));
                showInterstitial();
            }

            @Override
            public void onSongItemDeleteClicked(int position) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(audioAdapter);
    }

    private void showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
//            Log.d(TAG, "IKLAN MUNCUL:");
        } else {
//            Log.d(TAG, "IKLAN TIDAK MUNCUL");
        }
    }
}