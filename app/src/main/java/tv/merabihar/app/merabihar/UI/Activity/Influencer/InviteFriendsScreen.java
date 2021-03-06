package tv.merabihar.app.merabihar.UI.Activity.Influencer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import tv.merabihar.app.merabihar.R;
import tv.merabihar.app.merabihar.Util.PreferenceHandler;

public class InviteFriendsScreen extends AppCompatActivity {

    TextView mReferalCode;
    LinearLayout mWhatsapp,mFaceBook,mSms,mMore;
    String shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            shareContent = "Hi friends I get 50 coins from Mera Bihar, Install Mera Bihar and use my referral code and get 50 coins immediately.\n\n Use my referal code for Sign-Up MBR"+PreferenceHandler.getInstance(InviteFriendsScreen.this).getUserId()+"\n http://bit.ly/2JXcOnw";

            setContentView(R.layout.activity_invite_friends_screen);
            mReferalCode = (TextView)findViewById(R.id.referal_code_text_2);
            mWhatsapp = (LinearLayout)findViewById(R.id.whatsapp_invite);
            mFaceBook = (LinearLayout)findViewById(R.id.facebook_invite);
            mSms = (LinearLayout)findViewById(R.id.sms_invite);
            mMore = (LinearLayout)findViewById(R.id.more_invite);

            int profileId = PreferenceHandler.getInstance(InviteFriendsScreen.this).getUserId();

            if(profileId!=0){
                String ref = "MBR"+profileId;
                String referCodeText = ref;
             //   String referCodeText = Base64.encodeToString(ref.getBytes(), Base64.DEFAULT);
                mReferalCode.setText(""+referCodeText);
            }

            mReferalCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String text = mReferalCode.getText().toString();

                    if(text!=null&&!text.isEmpty()){

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", text);
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(InviteFriendsScreen.this, "Text copied", Toast.LENGTH_SHORT).show();

                    }


                }
            });

            mWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                    try {
                        Objects.requireNonNull(InviteFriendsScreen.this).startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                    }

                }
            });

            mFaceBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String textBody = shareContent;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,!TextUtils.isEmpty(textBody) ? textBody : "");



                    boolean facebookAppFound = false;
                    List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana") ||
                                info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.lite")) {
                            intent.setPackage(info.activityInfo.packageName);
                            facebookAppFound = true;
                            break;
                        }
                    }
                    if (!facebookAppFound) {
//                        String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + "https://play.google.com/store/apps/details?id=app.zingo.bihartourismguide";
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                    }
                    startActivity(intent);
                }
            });

            mSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");

                    smsIntent.putExtra("sms_body", shareContent);
                    if (smsIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(smsIntent);
                    } else {
                        Log.d("SMS", "Can't resolve app for ACTION_SENDTO Intent");
                        try {
                            Intent smsIntent2 = new Intent(android.content.Intent.ACTION_VIEW);
                            smsIntent2.putExtra("sms_body", shareContent);
                            smsIntent2.setData(Uri.parse("sms:"));
                            startActivity(smsIntent2);
                        } catch (android.content.ActivityNotFoundException anfe) {
                            Log.d("Error SMS" , "Error");
                        }
                    }

                }
            });

            mMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Send to"));
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
