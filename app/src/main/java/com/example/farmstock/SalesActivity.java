package com.example.farmstock;

//Developed By Pranshu Ranjan

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity {

    TextView mealTotalText;
    ArrayList<FoodItems> orders;
    ArrayList<FoodItems> listViewItems;
    OrderAdapter adapter;
    Button Adder;
    ImageView placeOrderBtn;
    TextView header,mdate;
    int pricetag;
    String name, upivirtualid;
    int amount;
    final int UPI_PAYMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        header=(TextView)findViewById(R.id.TopHead);
        mdate=(TextView)findViewById(R.id.dateText);

        name="kishore kethineni";
        upivirtualid="kishorec111-3@okicici";
        amount=25;

        Intent intent=getIntent();
//      String str = intent.getStringExtra(Key);
        header.setText(intent.getStringExtra("Heading"));
        mdate.setText(intent.getStringExtra("date"));
        ListView storedOrders = (ListView)findViewById(R.id.selected_food_list);

        orders = getListItemData();
        mealTotalText = (TextView)findViewById(R.id.meal_total);
        adapter = new OrderAdapter(this, orders);

        storedOrders.setAdapter(adapter);
        adapter.registerDataSetObserver(observer);
        listViewItems = new ArrayList<FoodItems>();
        Adder=(Button)findViewById(R.id.ButtonAdder);

        Adder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });
        placeOrderBtn=findViewById(R.id.OrderPlaceBtn);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout lila1= new LinearLayout(SalesActivity.this);
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(SalesActivity.this);
                input.setHint("Enter your Phone Number");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                lila1.addView(input);
                AlertDialog dialog = new AlertDialog.Builder(SalesActivity.this)
                        .setTitle("Place Order")
                        .setMessage("Enter your phone number")
                        .setView(lila1)
                        .setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(input.getText().toString().length()>10){
                                    Toast.makeText(SalesActivity.this, "Phone number does not exceeds more than 10 digits", Toast.LENGTH_SHORT).show();

                                    return ;
                                }
                                payUsingUpi(name, upivirtualid,Integer.toString(amount));
                                Toast.makeText(SalesActivity.this, "Thanks ! your order  has been placed ", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SalesActivity.this, "You will shory recieve a confirmation message", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }

     void payUsingUpi(String name, String upivirtualid, String amount) {
        Log.e("main ", "name "+name +"--up--"+upivirtualid+"--"+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upivirtualid)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(SalesActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }


    public int calculateMealTotal(){
        int mealTotal = 0;
        for(FoodItems order : orders){
            mealTotal += order.getmAmount() * order.getmQuantity();
        }
        mealTotalText.setText(Integer.toString(mealTotal));

        return mealTotal;
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            setMealTotal();
        }
    };

    private ArrayList<FoodItems> getListItemData(){
        ArrayList<FoodItems> listViewItems = new ArrayList<FoodItems>();
        listViewItems.add(new FoodItems("Rice",30));
        listViewItems.add(new FoodItems("Beans",40));
        return listViewItems;
    }
    public void inserItem(String itemname,int price){
        getListItemData().add(new FoodItems(itemname,price));
        adapter.insert(new FoodItems(itemname,price),getListItemData().size());
        adapter.notifyDataSetChanged();
    }

    public void showAddItemDialog() {
        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);
        final EditText input = new EditText(this);
        final EditText input1 = new EditText(this);
        input.setHint("Enter item name");
        input1.setHint("Enter item price");
        input1.setInputType(InputType.TYPE_CLASS_NUMBER);
        lila1.addView(input);
        lila1.addView(input1);
        AlertDialog dialog = new AlertDialog.Builder(SalesActivity.this)
                .setTitle("Add Item")
                .setMessage("Enter item details")
                .setView(lila1)
                .setPositiveButton("Add item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pricetag=Integer.parseInt(input1.getText().toString());
                        inserItem(input.getText().toString(),Integer.parseInt(input1.getText().toString()));

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void setMealTotal(){
        mealTotalText.setText("IN"+"\u20B9"+" "+ calculateMealTotal());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(SalesActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(SalesActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(SalesActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(SalesActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(SalesActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}

