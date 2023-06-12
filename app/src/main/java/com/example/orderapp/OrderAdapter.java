package com.example.orderapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Model> modelList;
    Context context;

    public OrderAdapter(Context context, List<Model> modelList) {
                this.context = context;
                this.modelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Model drink = modelList.get(position);

        String nameofDrink = drink.getmDrinkName();
        String descriptionofdrink = drink.getmDrinkDetail();
        int images = drink.getmDrinkPhoto();

        holder.mDrinkName.setText(nameofDrink);
        holder.mDrinkDescription.setText(descriptionofdrink);
        holder.imageView.setImageResource(images);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = holder.getAdapterPosition();
                Model selectedModel = modelList.get(selectedPosition);


                Intent intent = new Intent(v.getContext(), DrinkSetupActivity.class);
                intent.putExtra("selectedModel", selectedModel);
                intent.putExtra("position", selectedPosition);
                v.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mDrinkName, mDrinkDescription;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mDrinkName = itemView.findViewById(R.id.coffeeName);
            mDrinkDescription = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.coffeeImage);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Model drink = modelList.get(position);

                Intent intent = new Intent(context, DrinkSetupActivity.class);
                intent.putExtra("name", drink.getmDrinkName());
                intent.putExtra("description", drink.getmDrinkDetail());
                intent.putExtra("imageResource", drink.getmDrinkPhoto());
                context.startActivity(intent);
            }
        }

    }
    }

