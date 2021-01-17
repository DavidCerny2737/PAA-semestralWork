package cz.tul.nutritiontracker.restservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cz.tul.nutritiontracker.dto.HintDTO;

public class ImageDownloadAsyncTask {

    private static final String TAG = ImageDownloadAsyncTask.class.getSimpleName();

    private List<HintDTO> items;
    private ExecutorService threadPool;

    private static ImageDownloadAsyncTask instance;

    private ImageDownloadAsyncTask(List<HintDTO> items){
        this.items = items;
        this.threadPool = new ThreadPoolExecutor(3, 6, 5L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    public static ImageDownloadAsyncTask getInstance(List<HintDTO> items) {
        if(instance == null)
            return new ImageDownloadAsyncTask(items);
        instance.setItems(items);
        return instance;
    }

    public static ImageDownloadAsyncTask getInstance(){
        return instance;
    }

    public void awaitTermination(){
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e(TAG, "Image downloading thread suddenly interupted while waiting for finish.", e);
        }
    }

    public void run(){
        for(final HintDTO food: items){
            Runnable task = new Runnable() {
                @Override
                public void run() {

                    if(food.getFood().getImageUri() == null || food.getFood().getImageUri().equals(""))
                        return;

                    InputStream is = null;
                    HttpURLConnection connection = null;
                    try {
                        URL imageUrl = new URL(food.getFood().getImageUri());
                        connection = (HttpURLConnection) imageUrl.openConnection();
                        connection.setDoInput(true);
                        connection.connect();

                        is = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        food.getFood().setImageBitmap(bitmap);
                    } catch (MalformedURLException e){
                        Log.e(TAG, "Malformed url for downloading an image.", e);
                    } catch (IOException e){
                        Log.e(TAG, "Unspecified error while downloading food image.", e);
                    } finally {
                        if(connection != null)
                            connection.disconnect();
                        if(is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Error while trying to close input stream for external image.", e);
                            }
                        }
                    }
                }
            };
            threadPool.submit(task);
        }
    }

    public List<HintDTO> getItems() {
        return items;
    }

    public void setItems(List<HintDTO> items) {
        this.items = items;
    }
}
