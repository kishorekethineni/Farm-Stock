package com.example.farmstock.ui.tools;
//Developed By Pranshu Ranjan
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmstock.R;
import com.example.farmstock.SalesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ToolsFragment extends Fragment {
    private ArrayList<ExampleItem> mExamplelist;
    private RecyclerView recyclerView;
    public static View root;
    private FloatingActionButton ItemAdder;
    private  ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_tools, container, false);
        mExamplelist = new ArrayList<>();
        createExampleList();

        buildRecyclerView();

        ItemAdder= (FloatingActionButton)root.findViewById(R.id.additemBtn);

        ItemAdder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                final Date date = new Date();
                final String date1=formatter.format(date);
                LinearLayout lila1= new LinearLayout(getContext());
                lila1.setOrientation(LinearLayout.VERTICAL);
                final EditText input = new EditText(getContext());
                input.setHint("Enter Title for this purchase");
                lila1.addView(input);

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("New Purchase")
                        .setMessage("Enter detail for your Inventory")
                        .setView(lila1)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (input.getText().toString() != null&& input.getText().length()<=12) {
                                    inserItem(mExamplelist.size(), input.getText().toString(), date1);

                                }
                                else{
                                    Toast.makeText(getContext(), "input lenght could not be more than 12 and less than 0", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();



            }
        });




        return root;
    }
    public void inserItem(int position,String title,String date){
        mExamplelist.add(position,new ExampleItem(title,date));
        mAdapter.notifyItemInserted(position);

    }
    public void deleteItem(int position){
        mExamplelist.remove(position);
        mAdapter.notifyItemRemoved(position);

    }
    public void changeItem(int position ,String text){
        mExamplelist.get(position).changeText2(text);
        mAdapter.notifyItemChanged(position);

    }
    public void buildRecyclerView() {

        mExamplelist.add(new ExampleItem("Purchase List 1","Sell date"));
        mExamplelist.add(new ExampleItem("Purchase List 2","Sell date"));
    }

    public void createExampleList() {
        recyclerView=root.findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        mlayoutManager= new LinearLayoutManager(this.getContext());
        mAdapter =new ExampleAdapter(mExamplelist);

        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(mAdapter );

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positon) {
                Intent i =new Intent(getContext(), SalesActivity.class);
                i.putExtra("Heading",mExamplelist.get(positon).getmText1());
                i.putExtra("date",mExamplelist.get(positon).getmText2());
                startActivity(i);
            }

            @Override
            public void onDeleteClick(int position) {
                deleteItem(position);
            }
        });
    }
}