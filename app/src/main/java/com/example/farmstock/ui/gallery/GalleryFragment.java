package com.example.farmstock.ui.gallery;
//Developed By Pranshu Ranjan
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.androdocs.httprequest.HttpRequest;
import com.example.farmstock.Home;
import com.example.farmstock.Main2Activity;
import com.example.farmstock.R;
import com.example.farmstock.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
//   (EditText) cityname=HomeFragment.findViewById(R.id.spinnerState);
    HomeFragment hm =new HomeFragment();
    EditText cityname=hm.root.findViewById(R.id.CityName);
    String CITY = cityname.getText().toString()+",IN";
    String API = "12fdd7256b090419d4dda1cb6ff15561";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    public static View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);


//        final TextView textView = root.findViewById(R.id.text_gallery);
//        final Button btn=root.findViewById(R.id.gal_btn);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText("fuck you ass hole!");
//            }
//        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.setText("there is anyone who is mad!");
//            }
//        });
        addressTxt = root.findViewById(R.id.address);
        updated_atTxt = root.findViewById(R.id.updated_at);
        statusTxt = root.findViewById(R.id.status);
        tempTxt = root.findViewById(R.id.temp);
        temp_minTxt = root.findViewById(R.id.temp_min);
        temp_maxTxt = root.findViewById(R.id.temp_max);
        sunriseTxt = root.findViewById(R.id.sunrise);
        sunsetTxt = root.findViewById(R.id.sunset);
        windTxt = root.findViewById(R.id.wind);
        pressureTxt = root.findViewById(R.id.pressure);
        humidityTxt = root.findViewById(R.id.humidity);
        if(isNetworkAvailable(getContext())&&CITY!=null) {
            new weatherTask().execute();
        }
        else{
            if(CITY==null){
                Toast.makeText(getActivity(), "Please Update City", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
        }
        return root;
    }
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* Showing the ProgressBar, Making the main design GONE */
            root.findViewById(R.id.loader).setVisibility(View.VISIBLE);
            root.findViewById(R.id.mainContainer).setVisibility(View.GONE);
            root.findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
                temp_minTxt.setText(tempMin);
                temp_maxTxt.setText(tempMax);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                windTxt.setText(windSpeed);
                pressureTxt.setText(pressure);
                humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                root.findViewById(R.id.loader).setVisibility(View.GONE);
                root.findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                root.findViewById(R.id.loader).setVisibility(View.GONE);
                root.findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }

}