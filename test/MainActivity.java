package com.example.test;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<WeatherSample> weatherSamples = new ArrayList<>();
    private List<WeatherSample> test = new ArrayList<>();
    private String[] tokens;

    //    private Response response;

    private String url = "https://raw.githubusercontent.com/geojun01/lottodata/master/";
    private String s = "https://raw.githubusercontent.com/geojun01/lottodata/master/lottostats.csv";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new HttpAsyncTask().execute(s);

    }


    // JSON 모든데이터 파싱 코드
    private void roft() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<WeatherSample>> call = apiService.getPosts();

        call.enqueue(new Callback<List<WeatherSample>>() {
            @Override
            public void onResponse(Call<List<WeatherSample>> call, Response<List<WeatherSample>> response) {
                if (!response.isSuccessful()) {
                    Log.e("로그", "Code " + response.code());
                    return;
                }

                List<WeatherSample> posts = response.body();


                Log.e("로그", "Size " + posts.size());

                for (int i = 0; i < posts.size(); i++) {
                    System.out.println("데이터 확인 : " + posts.get(i).getDrwNo() + " / " + posts.get(i).getDrwtNo1()
                            + " / " + posts.get(i).getDrwtNo2()
                            + " / " + posts.get(i).getDrwtNo3()
                            + " / " + posts.get(i).getDrwtNo4()
                            + " / " + posts.get(i).getDrwtNo5()
                            + " / " + posts.get(i).getDrwtNo6()
                            + " / " + posts.get(i).getBnusNo()
                            + " / " + posts.get(i).getDrwNoDate()
                            + " / " + posts.get(i).getFirstWinamnt());
                }

            }

            @Override
            public void onFailure(Call<List<WeatherSample>> call, Throwable t) {
                Log.e("로그", "에러 " + t.toString());
            }
        });
    }



    // SCV 파일 읽어오기
    @SuppressLint("StaticFieldLeak")
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {

            String result = null;
            String strUrl = strings[0];
            //Log.e("로그", strUrl);

            try {
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();

                //result = response.body().string();
                result = response.body().string();
                //Log.e("로그", result);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if  (s != null) {
                //Log.e("로그", "확인 :" + s);
                readWeatherData(s);
            }

            //Log.e("로그", "확인 :" + s);
        }
    }


    //웹에서 읽어온 데이터
    private void readWeatherData(String s) {
        InputStream is = new ByteArrayInputStream(s.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                // Split
                tokens = line.split(",");

                // Read the data
//                WeatherSample sample = new WeatherSample();
//                sample.setDrwNo(tokens[0]);
//                sample.setDrwtNo1(Integer.parseInt(tokens[11]));
//                sample.setDrwtNo1(Integer.parseInt(tokens[12]));
//                sample.setDrwtNo2(Integer.parseInt(tokens[13]));
//                sample.setDrwtNo3(Integer.parseInt(tokens[14]));
//                sample.setDrwtNo4(Integer.parseInt(tokens[15]));
//                sample.setDrwtNo5(Integer.parseInt(tokens[16]));
//                sample.setDrwtNo6(Integer.parseInt(tokens[17]));
//                sample.setBnusNo(Integer.parseInt(tokens[18]));
//
//                weatherSamples.add(sample);

                Log.e("로그", "결과 : " + Arrays.toString(tokens));
                Log.e("로그", "결과 : " + tokens[89]);

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error readling data file on line" + line, e);
            e.printStackTrace();
        }

        //test();

    }








    //row 폴더의 cvs파일
    private void readWeatherData() {
        InputStream is = getResources().openRawResource(R.raw.lotto);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                // Split
                tokens = line.split(",");

                // Read the data
                WeatherSample sample = new WeatherSample();
                sample.setDrwNo(tokens[0]);
                sample.setDrwtNo1(Integer.parseInt(tokens[12]));
                sample.setDrwtNo2(Integer.parseInt(tokens[13]));
                sample.setDrwtNo3(Integer.parseInt(tokens[14]));
                sample.setDrwtNo4(Integer.parseInt(tokens[15]));
                sample.setDrwtNo5(Integer.parseInt(tokens[16]));
                sample.setDrwtNo6(Integer.parseInt(tokens[17]));
                sample.setBnusNo(Integer.parseInt(tokens[18]));

                weatherSamples.add(sample);

                //Log.d("MyActivity", "Just created: " + sample);

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error readling data file on line" + line, e);
            e.printStackTrace();
        }


    }

}
