package com.example.islamicapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.islamicapp.AppController;
import com.example.islamicapp.CustomView.SKtextViewRobotoThin;
import com.example.islamicapp.R;

public class AboutFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SKtextViewRobotoThin mEmail;
    private ImageView mFaceBook;
    private ImageView mInstagram;
    private String mParam1;
    private String mParam2;
    private View mRootView;
    private ImageView mTwitter;
    private ImageView mWebSite;
    private ImageView mYouTube;

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_about, container, false);
        initUi();
        Log.e("called", "aboutfragment");
        return this.mRootView;
    }

    @SuppressLint({"SetTextI18n"})
    private void initUi() {
        this.mFaceBook = (ImageView) this.mRootView.findViewById(R.id.facebook);
        this.mWebSite = (ImageView) this.mRootView.findViewById(R.id.website);
        this.mYouTube = (ImageView) this.mRootView.findViewById(R.id.youtube);
        this.mInstagram = (ImageView) this.mRootView.findViewById(R.id.instagram);
        this.mTwitter = (ImageView) this.mRootView.findViewById(R.id.twitter);
        this.mEmail = (SKtextViewRobotoThin) this.mRootView.findViewById(R.id.mEmail);
        if (!AppController.FACEBOOK_PAGE_ID.equals("")) {
//            this.mFaceBook.setVisibility(0);
        } else {
//            this.mFaceBook.setVisibility(8);
        }
        if (!"email@ibn-tv.com".equals("")) {
//            this.mWebSite.setVisibility(0);
        } else {
//            this.mWebSite.setVisibility(8);
        }
        if (!AppController.YOUTUBE_CHANNEL_ID.equals("")) {
//            this.mYouTube.setVisibility(0);
        } else {
//            this.mYouTube.setVisibility(8);
        }
        if (!AppController.INSTAGRAM_PAGE_URL.equals("")) {
//            this.mInstagram.setVisibility(0);
        } else {
//            this.mInstagram.setVisibility(8);
        }
        if (!AppController.TWITTER_ID.equals("")) {
//            this.mTwitter.setVisibility(0);
        } else {
//            this.mTwitter.setVisibility(8);
        }
        if (!"email@ibn-tv.com".equals("")) {
//            this.mEmail.setVisibility(0);
            this.mEmail.setText("E-mail : email@ibn-tv.com");
        } else {
//            this.mEmail.setVisibility(8);
        }
        this.mFaceBook.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AppController.FACEBOOK_PAGE_ID.equals("") || AppController.FACEBOOK_PAGE_ID.equalsIgnoreCase(AppController.FACEBOOK_PAGE_ID)) {
                    Toast.makeText(AboutFragment.this.getActivity(), "Url not specified", Toast.LENGTH_LONG).show();
                } else {
                    AboutFragment.this.openFaceBookPage();
                }
            }
        });
        this.mWebSite.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ("email@ibn-tv.com".equals("") || "email@ibn-tv.com".equalsIgnoreCase("<...your website...>")) {
                    Toast.makeText(AboutFragment.this.getActivity(), "Url not specified", Toast.LENGTH_LONG).show();
                } else {
                    AboutFragment.this.openWebSite();
                }
            }
        });
        this.mYouTube.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AppController.YOUTUBE_CHANNEL_ID.equals("") || AppController.YOUTUBE_CHANNEL_ID.equalsIgnoreCase(AppController.YOUTUBE_CHANNEL_ID)) {
                    Toast.makeText(AboutFragment.this.getActivity(), "Url not specified", Toast.LENGTH_LONG).show();
                } else {
                    AboutFragment.this.openYotube();
                }
            }
        });
        this.mInstagram.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AppController.INSTAGRAM_PAGE_URL.equals("") || AppController.INSTAGRAM_PAGE_URL.equalsIgnoreCase(AppController.INSTAGRAM_PAGE_URL)) {
                    Toast.makeText(AboutFragment.this.getActivity(), "Url not specified", Toast.LENGTH_LONG).show();
                } else {
                    AboutFragment.this.openInstragram();
                }
            }
        });
        this.mTwitter.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (AppController.TWITTER_ID.equals("") || AppController.TWITTER_ID.equalsIgnoreCase(AppController.TWITTER_ID)) {
                    Toast.makeText(AboutFragment.this.getActivity(), "Url not specified", Toast.LENGTH_LONG).show();
                } else {
                    AboutFragment.this.openTwitter();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void openInstragram() {
        Intent likeIng = new Intent("android.intent.action.VIEW", Uri.parse(AppController.INSTAGRAM_PAGE_URL));
        likeIng.setPackage("com.instagram.android");
        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(AppController.INSTAGRAM_PAGE_URL)));
        }
    }

    /* access modifiers changed from: private */
    public void openTwitter() {
        Intent intent;
        try {
            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent("android.intent.action.VIEW", Uri.parse("twitter://user?screen_name=<...your Twitter_id ...>"));
//            intent.addFlags(268435456);
        } catch (Exception e) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/<...your Twitter_id ...>?lang=en"));
        }
        getActivity().startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void openWebSite() {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("email@ibn-tv.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().startActivity(intent);
    }

    /* access modifiers changed from: private */
    public void openYotube() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            try {
                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse(AppController.YOUTUBE_CHANNEL_ID));
                startActivity(intent);
                Intent intent2 = intent;
            } catch (ActivityNotFoundException e) {
                Intent intent3 = intent;
                Intent intent4 = new Intent("android.intent.action.VIEW");
                intent4.setData(Uri.parse(AppController.YOUTUBE_CHANNEL_ID));
                startActivity(intent4);
            }
        } catch (ActivityNotFoundException e2) {
            Intent intent42 = new Intent("android.intent.action.VIEW");
            intent42.setData(Uri.parse(AppController.YOUTUBE_CHANNEL_ID));
            startActivity(intent42);
        }
    }

    /* access modifiers changed from: private */
    public void openFaceBookPage() {
        Intent facebookIntent = new Intent("android.intent.action.VIEW");
        facebookIntent.setData(Uri.parse(getFacebookPageURL(getContext())));
        startActivity(facebookIntent);
    }

    public String getFacebookPageURL(Context context) {
        try {
            if (context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=<...your Facebook url..>";
            }
            return "fb://page/<...your Facebook page id..>";
        } catch (NameNotFoundException e) {
            return AppController.FACEBOOK_URL;
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if (a != null) {
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            }
        }
    }
}
