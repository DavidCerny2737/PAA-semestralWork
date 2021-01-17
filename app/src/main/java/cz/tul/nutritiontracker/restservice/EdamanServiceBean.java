package cz.tul.nutritiontracker.restservice;

import android.util.Log;

import cz.tul.nutritiontracker.activity.FoodReceivingActivity;
import cz.tul.nutritiontracker.dto.FoodDTO;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EdamanServiceBean implements EdamanService {

    private static final String TAG = EdamanServiceBean.class.getSimpleName();
    private static final String BASE_URI = "https://api.edamam.com/api/food-database/v2/";
    private static final String APPLICATION_KEY = "dbe2680aa91d4118156c38f597b07916";
    private static final String APPLICATION_ID = "f162d352";

    private EdamanAPI api;
    private static EdamanServiceBean instance;
    private CompositeDisposable compositeDisposable;

    private EdamanServiceBean(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        this.api = retrofit.create(EdamanAPI.class);
        this.compositeDisposable = new CompositeDisposable();
    }

    public static EdamanServiceBean getInstance(){
        if(instance == null)
            instance = new EdamanServiceBean();
        return instance;
    }

    @Override
    public void searchForFoods(String query, final FoodReceivingActivity caller) {
        api.searchForFoods(APPLICATION_ID, APPLICATION_KEY, query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodDTO>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG,"Search for food in subscribe - beggining dispose.");
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(FoodDTO foodDTO) {
                Log.i(TAG,"Search for food request was successfull.");
                ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(foodDTO.getHints());
                task.run();
                task.awaitTermination();
                caller.setFoods(task.getItems(), foodDTO.getPagination());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Search for food request was unccessfull.", e);
                caller.setErrorMessage(e.getMessage());
            }
        });
    }

    @Override
    public void foodLinkPagination(String uri, final FoodReceivingActivity caller) {
        api.searchForFoodWithLink(uri).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FoodDTO>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG,"Search for food in subscribe - beggining dispose.");
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(FoodDTO foodDTO) {
                Log.i(TAG,"Search for food request was successfull.");
                ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(foodDTO.getHints());
                task.run();
                task.awaitTermination();
                caller.setFoods(task.getItems(), foodDTO.getPagination());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Search for food request was unccessfull.", e);
                caller.setErrorMessage(e.getMessage());
            }
        });
    }

    @Override
    public void findFoodById(String foodId) {

    }

    @Override
    public void dispose() {
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}
