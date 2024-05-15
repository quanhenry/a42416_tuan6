package com.example.myapplication.tuan6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<product> mProductList;

    public ProductAdapter(Context mContext, List<product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderT62 holder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_view_t62,parent,false);
            holder=new ViewHolderT62();
            holder.imageview=convertView.findViewById(R.id.item_product_imageView);
            holder.styleidTv=convertView.findViewById(R.id.item_styleid_textview);
            holder.brandTv=convertView.findViewById(R.id.item_brand_textview);
            holder.priceTv=convertView.findViewById(R.id.item_price_textview);
            holder.additionalInfoTv=convertView.findViewById(R.id.item_additionalInfo_textview);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolderT62) convertView.getTag();
        }
        product product=mProductList.get(position);
        if(product!=null){
            Picasso.get().load(product.getSearchImage()).into(holder.imageview);
            holder.additionalInfoTv.setText(product.getAdditionalInfo());
            holder.brandTv.setText(product.getBrand());
            holder.priceTv.setText(product.getPrice());
            holder.styleidTv.setText(product.getStyleId());
        }

        return convertView;
    }
    static class ViewHolderT62{
        ImageView imageview;
        TextView styleidTv, brandTv, priceTv, additionalInfoTv;
    }
}
