package com.example.farmstock;
//Developed By Pranshu Ranjan and with some help of online resources
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<FoodItems>{
    private List<FoodItems> list;
    private Context context;

    TextView currentFoodName,
            currentCost,
            quantityText,
            addMeal,
            subtractMeal,
            removeMeal;

    public OrderAdapter(Context context, List<FoodItems> myOrders) {
        super(context, 0, myOrders);
        this.list = myOrders;
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.food_items,parent,false
            );
        }

        final FoodItems currentFood = getItem(position);

        currentFoodName = (TextView)listItemView.findViewById(R.id.selected_food_name);
        currentCost = (TextView)listItemView.findViewById(R.id.selected_food_amount);
        subtractMeal = (TextView)listItemView.findViewById(R.id.minus_meal);
        quantityText = (TextView)listItemView.findViewById(R.id.quantity);
        addMeal = (TextView)listItemView.findViewById(R.id.plus_meal);
        removeMeal = (TextView)listItemView.findViewById(R.id.delete_item);

        //Set the text of the meal, amount and quantity
        currentFoodName.setText(currentFood.getmName());
        currentCost.setText("IN"+"\u20B9"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
        quantityText.setText("x "+ currentFood.getmQuantity());

        //OnClick listeners for all the buttons on the ListView Item
        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.addToQuantity();
                quantityText.setText("x "+ currentFood.getmQuantity());
                currentCost.setText("IN"+"\u20B9"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                notifyDataSetChanged();
            }
        });

        subtractMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFood.removeFromQuantity();
                quantityText.setText("x "+currentFood.getmQuantity());
                currentCost.setText("IN"+"\u20B9"+" "+ (currentFood.getmAmount() * currentFood.getmQuantity()));
                notifyDataSetChanged();
            }
        });

        removeMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return listItemView;
    }

}