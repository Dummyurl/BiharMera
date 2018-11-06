package tv.merabihar.app.merabihar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.merabihar.app.merabihar.CustomFonts.MyTextView_Lato_Regular;
import tv.merabihar.app.merabihar.Model.Goals;
import tv.merabihar.app.merabihar.Model.SubscribedGoals;
import tv.merabihar.app.merabihar.Model.TargetDes;
import tv.merabihar.app.merabihar.R;
import tv.merabihar.app.merabihar.Util.ThreadExecuter;
import tv.merabihar.app.merabihar.Util.Util;
import tv.merabihar.app.merabihar.WebAPI.GoalAPI;

public class ActiveTargetFragmentsAdapter extends RecyclerView.Adapter<ActiveTargetFragmentsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SubscribedGoals> list;
    public ActiveTargetFragmentsAdapter(Context context,ArrayList<SubscribedGoals> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        try{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.active_fragement_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SubscribedGoals targetDes = list.get(position);

        if(targetDes!=null){

            getGoals(targetDes.getGoalId(),holder.offerTitle);
            if(targetDes.getGoalId()==1){


                holder.activate_offer.setText("Refer Friend");

                String activeDate = targetDes.getActiveDate();

                if(activeDate.contains("T")){

                    String active[] = activeDate.split("T");
                    try {


                       // int value = (int)duration(active[0],new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        if(targetDes.getStatus().equalsIgnoreCase("Activated")){

                            String rewards = targetDes.getRewardsEarned();
                            if(rewards.contains(",")){

                                String rew[] = rewards.split(",");

                                if(rew[0].equals("1")&&Integer.parseInt(rew[1])>=100){

                                    holder.mCircle1.setImageResource(R.drawable.ovel_tick);
                                    holder.mCircleView1.setBackgroundColor(Color.parseColor("#176e0b"));

                                }else if(rew[0].equals("1")&&Integer.parseInt(rew[1])<100){

                                    holder.mCircle1.setImageResource(R.drawable.oval_cross);
                                    holder.mCircleView1.setBackgroundColor(Color.parseColor("#FF0000"));

                                }
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }else if(targetDes.getGoalId()==2){

                holder.activate_offer.setVisibility(View.GONE);

            }else if(targetDes.getGoalId()==3){

                holder.activate_offer.setText("Watch Video");

                String endDate = targetDes.getEndDate();

                if(endDate!=null&&!endDate.isEmpty()){

                    if(endDate.contains("T")){

                        String dats[] = endDate.split("T");
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dats[0]);

                            if(new Date().getTime()-date.getTime()>0){


                            }else{
                                if(targetDes.getStatus().equalsIgnoreCase("Activated")) {

                                    String rewards = targetDes.getRewardsEarned();

                                    int value = Integer.parseInt(rewards);

                                    if (value >= 10800) {
                                        holder.mCircle1.setImageResource(R.drawable.ovel_tick);
                                        holder.mCircleView1.setBackgroundColor(Color.parseColor("#176e0b"));
                                    } else if (value >= 21600) {
                                        holder.mCircle2.setImageResource(R.drawable.ovel_tick);
                                        holder.mCircleView2.setBackgroundColor(Color.parseColor("#176e0b"));
                                    } else if (value >= 32400) {
                                        holder.mCircle3.setImageResource(R.drawable.ovel_tick);
                                        holder.mCircleView3.setBackgroundColor(Color.parseColor("#176e0b"));
                                    } else if (value >= 43200) {
                                        holder.mCircle4.setImageResource(R.drawable.ovel_tick);
                                        holder.mCircleView4.setBackgroundColor(Color.parseColor("#176e0b"));
                                    } else if (value >= 54000) {
                                        holder.mCircle5.setImageResource(R.drawable.ovel_tick);
                                    }

                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }

    }

    @Override
    public int getItemCount() {

        return list.size();

    }



    class ViewHolder extends RecyclerView.ViewHolder {

        TextView offerTitle, offerDetails;
        Button activate_offer;
        CircleImageView offerLogo;
        ImageView mCircle1,mCircle2,mCircle3,mCircle4,mCircle5;
        View mCircleView1,mCircleView2,mCircleView3,mCircleView4;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setClickable(true);

            offerDetails = itemView.findViewById(R.id.offer_details);
            offerTitle = itemView.findViewById(R.id.offer_title);
            activate_offer = itemView.findViewById(R.id.activate_offer);
            offerLogo = itemView.findViewById(R.id.offer_logo);
            mCircle1 = itemView.findViewById(R.id.month1_pb_circle);
            mCircle2 = itemView.findViewById(R.id.month2_pb_circle);
            mCircle3 = itemView.findViewById(R.id.month3_pb_circle);
            mCircle4 = itemView.findViewById(R.id.month4_pb);
            mCircle5 = itemView.findViewById(R.id.month5_pb_circle);

            mCircleView1 = itemView.findViewById(R.id.month1_pb_line);
            mCircleView2 = itemView.findViewById(R.id.month2_pb_line);
            mCircleView3 = itemView.findViewById(R.id.month3_pb_line);
            mCircleView4 = itemView.findViewById(R.id.month4_pb_line);



        }

    }

    public void getGoals(final int ids,final TextView textView)
    {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                final GoalAPI categoryAPI = Util.getClient().create(GoalAPI.class);
                Call<Goals> getCat = categoryAPI.getGoalsById(ids);
                //Call<ArrayList<Category>> getCat = categoryAPI.getCategories();

                getCat.enqueue(new Callback<Goals>() {

                    @Override
                    public void onResponse(Call<Goals> call, Response<Goals> response) {


                        Goals goals = response.body();

                        if(response.code() == 200 && goals!= null)
                        {

                            textView.setText(""+goals.getGoalName());

                        }else{



                        }
                    }

                    @Override
                    public void onFailure(Call<Goals> call, Throwable t) {


                        Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });

    }

    public long duration(String fromm,String too) throws Exception{

        String from = fromm+" 00:00:00";
        String to = too+" 00:00:00";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {

            if(from.contains("-"))
            {
                d1 = format1.parse(from);
                d2 = format1.parse(to);
            }
            else
            {
                d1 = format.parse(from);
                d2 = format.parse(to);
            }
            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            return diffDays;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }

    }

}
