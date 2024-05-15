package com.example.myapplication.tuan6;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class t62MainActivity extends AppCompatActivity {
    private ListView listview;
    private ProductAdapter adapter;
    private List<product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_t62_main2);
        listview=findViewById(R.id.t6listview);
        adapter=new ProductAdapter(this, productList);
        listview.setAdapter(adapter);
        new FetchProductsTask().execute();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private class FetchProductsTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder response=new StringBuilder();
            try{
                URL url=new URL("https://hungnttg.github.io/shopgiay.json");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader=new
                        BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                while((line= reader.readLine())!=null){
                    response.append(line);
                }
                reader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null && s.isEmpty()){
                try {
                    JSONObject json=new JSONObject(s);
                    JSONArray productArray=json.getJSONArray("products");
                    for(int i=0;i<productArray.length();i++){
                        JSONObject productObject=productArray.getJSONObject(i);
                        String styleId=productObject.getString("styleid");
                        String brand=productObject.getString("brand_filter_facet");
                        String price=productObject.getString("price");
                        String additionalInfo=productObject.getString("product_additional_info");
                        String searchImage=productObject.getString("search_image");
                        product product=new product(styleId,brand,price,additionalInfo,searchImage );
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Toast.makeText(t62MainActivity.this, "Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        }
    }
}