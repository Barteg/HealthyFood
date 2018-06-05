package com.example.bartek.foodscanner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
 */
public class scanFood extends AppCompatActivity implements View.OnClickListener {


    private Button scanBtn;
    private TextView formatTxt, contentTxt, firstTxt, secondTxt, thirdTxt, fourthTxt, fifthTxt, sixthTxt, seventhTxt, eighthTxt, ninthTxt, tenthTxt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_food);


        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        firstTxt = (TextView)findViewById(R.id.firstComponent);
        secondTxt = (TextView)findViewById(R.id.secondComponent);
        thirdTxt = (TextView)findViewById(R.id.thirdComponent);
        fourthTxt = (TextView)findViewById(R.id.fourthComponent);
        fifthTxt = (TextView)findViewById(R.id.fifthComponent);
        sixthTxt = (TextView)findViewById(R.id.sixthComponent);
        seventhTxt = (TextView)findViewById(R.id.seventhComponent);
        eighthTxt = (TextView)findViewById(R.id.eighthComponent);
        ninthTxt = (TextView)findViewById(R.id.ninthComponent);
        tenthTxt = (TextView)findViewById(R.id.tenthComponent);
        //listen for clicks
       // scanBtn.setOnClickListener((View.OnClickListener) this);

    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://foodapi18.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final Controller service = retrofit.create(Controller.class);

    public void onClick(View v){
        //check for scan button
        if(v.getId()==R.id.scan_button){
            //instantiate ZXing integration class
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //start scanning
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //retrieve result of scanning - instantiate ZXing object
        final IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //check we have a valid result
        if (scanningResult != null) {

            formatTxt.setText("");
            contentTxt.setText("");
            firstTxt.setText("");
            secondTxt.setText("");
            thirdTxt.setText("");
            fourthTxt.setText("");
            fifthTxt.setText("");
            sixthTxt.setText("");
            seventhTxt.setText("");
            eighthTxt.setText("");
            ninthTxt.setText("");
            tenthTxt.setText("");
            String scanContent = scanningResult.getContents();
            Call<foodModel> createCall = service.getPath(scanContent.toString()); //w środku service.get pluje się, ze "get(String) w interfejsie nie może być zastosowany"
            createCall.enqueue(new Callback<foodModel>() {
                @Override
                public void onResponse(Call<foodModel> call, Response<foodModel> resp) {


                    //String scanContent = scanningResult.getContents();
                    //String scanFormat = scanningResult.getFormatName();

                    formatTxt.append(resp.body().productName + "\n");

                    contentTxt.append(resp.body().productCode + "\n");

                    //pierwszy
                    if(resp.body().firstComponent == null)
                        firstTxt.setVisibility(View.GONE);
                    else
                        firstTxt.append(resp.body().firstComponent + "\n");

                    //drugi
                    if(resp.body().secondComponent == null)
                        secondTxt.setVisibility(View.GONE);
                    else
                    secondTxt.append(resp.body().secondComponent+ "\n");

                    //trzeci
                    if(resp.body().thirdComponent == null)
                        thirdTxt.setVisibility(View.GONE);
                    else
                    thirdTxt.append(resp.body().thirdComponent+ "\n");

                    //czwarty
                    if(resp.body().fourthComponent == null)
                        fourthTxt.setVisibility(View.GONE);
                    else
                    fourthTxt.append(resp.body().fourthComponent+ "\n");

                    //piaty
                    if(resp.body().fifthComponent == null)
                        fifthTxt.setVisibility(View.GONE);
                    else
                    fifthTxt.append(resp.body().fifthComponent+ "\n");

                    //szósty
                    if(resp.body().sixthComponent == null)
                        sixthTxt.setVisibility(View.GONE);
                    else
                    sixthTxt.append(resp.body().sixthComponent+ "\n");

                    //siódmy
                    if(resp.body().seventhComponent == null)
                        seventhTxt.setVisibility(View.GONE);
                    else
                    seventhTxt.append(resp.body().seventhComponent+ "\n");

                    //ósmy
                    if(resp.body().eighthComponent == null)
                        eighthTxt.setVisibility(View.GONE);
                    else
                    eighthTxt.append(resp.body().eighthComponent+ "\n");

                    //dziewiaty
                    if(resp.body().ninthComponent == null)
                        ninthTxt.setVisibility(View.GONE);
                    else
                    ninthTxt.append(resp.body().ninthComponent+ "\n");

                    //dziesiaty
                    if(resp.body().tenthComponent == null)
                        tenthTxt.setVisibility(View.GONE);
                    else
                    tenthTxt.append(resp.body().tenthComponent+ "\n");
                }

                @Override
                public void onFailure(Call<foodModel> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "No scan data received!", Toast.LENGTH_SHORT);
                    toast.show();
                }
/*
                @Override
                public void onFailure(Call<Food> call, Throwable t) {
                    t.printStackTrace();
                    formatTxt.setText(t.getMessage());
                }

*/
            /*
            //get content from Intent Result
            String scanContent = scanningResult.getContents();
            //get format name of data scanned
            String scanFormat = scanningResult.getFormatName();
            //output to UI
            formatTxt.setText("FORMAT: "+scanFormat);
            contentTxt.setText("CONTENT: "+scanContent);*/
            });
        }
        else{
            //invalid scan data or scan canceled
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }
        }
    }

