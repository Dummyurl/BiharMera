package tv.merabihar.app.merabihar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tv.merabihar.app.merabihar.Model.Contents;
import tv.merabihar.app.merabihar.R;
import tv.merabihar.app.merabihar.UI.Activity.ContentDetailScreen;
import tv.merabihar.app.merabihar.UI.Activity.ContentImageDetailScreen;

/**
 * Created by ZingoHotels Tech on 14-11-2018.
 */

public class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.MyViewHolder> {

    Context context;
    ArrayList<ArrayList<Contents>> mContentList;
    View view;



    public VideoFragmentAdapter(Context context , ArrayList<ArrayList<Contents>> mContentList)
    {
        this.context = context;
        this.mContentList = mContentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_follow_content_single_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final ArrayList<Contents> content = mContentList.get(position);


        if(position%2==0){
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }else{
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        SimpleDraweeView iv1 = holder.iv1 ;
        SimpleDraweeView iv2 = holder.iv2 ;
        SimpleDraweeView iv3 = holder.iv3 ;  // large imageview
        SimpleDraweeView iv4 = holder.iv4 ;
        SimpleDraweeView iv5 = holder.iv5 ;
        SimpleDraweeView iv6 = holder.iv6 ;
        SimpleDraweeView iv7 = holder.iv7 ;
        SimpleDraweeView iv8 = holder.iv8 ;
        SimpleDraweeView iv9 = holder.iv9 ;


        if(content!=null&&content.size()!=0&&content.size()==9){


            String urlString1 = "https://img.youtube.com/vi/"+content.get(0).getContentURL()+"/0.jpg";

            if(urlString1!=null){
                loadCroppedImage(urlString1,iv1);
                //iv1.setImageURI(Uri.parse(urlString1));
            }

            String urlString2 = "https://img.youtube.com/vi/"+content.get(1).getContentURL()+"/0.jpg";

            if(urlString2!=null){
                loadCroppedImage(urlString2,iv2);
               // iv2.setImageURI(Uri.parse(urlString2));
            }

            String urlString3 = "https://img.youtube.com/vi/"+content.get(2).getContentURL()+"/0.jpg";

            if(urlString3!=null){
                loadCroppedImage(urlString3,iv3);
                //iv3.setImageURI(Uri.parse(urlString3));
            }

            String urlString4 = "https://img.youtube.com/vi/"+content.get(3).getContentURL()+"/0.jpg";

            if(urlString4!=null){
                loadCroppedImage(urlString4,iv4);
                //iv4.setImageURI(Uri.parse(urlString4));
            }

            String urlString5 = "https://img.youtube.com/vi/"+content.get(4).getContentURL()+"/0.jpg";

            if(urlString5!=null){
                loadCroppedImage(urlString5,iv5);
                //iv5.setImageURI(Uri.parse(urlString5));
            }

            String urlString6 = "https://img.youtube.com/vi/"+content.get(5).getContentURL()+"/0.jpg";

            if(urlString6!=null){
                loadCroppedImage(urlString6,iv6);
                //iv6.setImageURI(Uri.parse(urlString6));
            }

            String urlString7 = "https://img.youtube.com/vi/"+content.get(6).getContentURL()+"/0.jpg";

            if(urlString7!=null){
                loadCroppedImage(urlString7,iv7);
                //iv7.setImageURI(Uri.parse(urlString7));
            }

            String urlString8 = "https://img.youtube.com/vi/"+content.get(7).getContentURL()+"/0.jpg";

            if(urlString8!=null){
                loadCroppedImage(urlString8,iv8);
                //iv8.setImageURI(Uri.parse(urlString8));
            }

            String urlString9 = "https://img.youtube.com/vi/"+content.get(8).getContentURL()+"/0.jpg";

            if(urlString9!=null){
                loadCroppedImage(urlString9,iv9);
                //iv9.setImageURI(Uri.parse(urlString9));
            }




        }
        // load image from api

        /*When clicked on the content image*/
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(0));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(1));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(2));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(3));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(4));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(5));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(6));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        iv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(7));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        iv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentDetailScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contents",content.get(8));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9 ;

        MyViewHolder(View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.follow_frag_img_view1);
            iv2 = itemView.findViewById(R.id.follow_frag_img_view2);
            iv3 = itemView.findViewById(R.id.follow_frag_img_view3);
            iv4 = itemView.findViewById(R.id.follow_frag_img_view4);
            iv5 = itemView.findViewById(R.id.follow_frag_img_view5);
            iv6 = itemView.findViewById(R.id.follow_frag_img_view6);
            iv7 = itemView.findViewById(R.id.follow_frag_img_view7);
            iv8 = itemView.findViewById(R.id.follow_frag_img_view8);
            iv9 = itemView.findViewById(R.id.follow_frag_img_view9);
        }

    }


    private void loadCroppedImage(String urlString, final ImageView imageView) {
        Picasso.with(context).load(urlString).into(new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                // Cropping the image
                Bitmap customBitMap =  Bitmap.createBitmap(bitmap, 0, 45, bitmap.getWidth(), bitmap.getHeight()-90);

                Glide.with(context)
                        .load(customBitMap)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.no_image)
                                .error(R.drawable.no_image))
                        .into(imageView);

//                mCustomLoader.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                // Log.e("Cropping Failed", errorDrawable.toString());
//                mCustomLoader.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

}