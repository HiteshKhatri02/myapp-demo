package com.myapp.demo;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;


/**
 * Application class for the app
 *
 */

public class AppController extends Application {

    private static AppController mPDApp;

    public static ImageLoaderConfiguration config;

    /**
     * This method is used to get instance of the class
     * @return get instance of application
     */
    public static AppController get() {
        return mPDApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPDApp = this;

        FirebaseApp.initializeApp(this);//Initialise Firebase

        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);//Init Image Loader instance
    }
}
