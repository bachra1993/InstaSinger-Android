package com.esprit.instasinger;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Firovski on 12/30/2016.
 */

public class MyApplication extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        PrintHashKey();
    }

    public void PrintHashKey()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "trabelsifiras.com.instasinger",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Firo:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }



    }
}
