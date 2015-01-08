package isst.fraunhofer.de.ompi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import isst.fraunhofer.de.ompi.model.HRV;

/**
 * Created by durin on 19/12/2014.
 */
public class HRVAdapter {

    final static String hrvPackage="com.RMT.ompihrv";

    private static HRVAdapter mInstance;
    private static Context mContext;

    public ArrayList<HRV> getHRV(){

        File files[] = checkFiles();
        if (files.length ==1)
            return readFile(files[0]);
        else return null;

    }
    /**
     * Sucht die gespeicherte HRV-Daten.
     * @return Anzahl von gefundenen Dateien
     */
    public  File[] checkFiles(){

       // File sdcard = Environment.getExternalStorageDirectory();
       // String s1=Environment.getExternalStorageState();
        File hrvDirectory= new File (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),hrvPackage);

        File files[] = hrvDirectory.listFiles();
        Log.d("Files", "Path: " + hrvDirectory.getPath());
        Log.d("Files", "Size: "+ files.length);
        return files;
    }

    public ArrayList<HRV> readFile(File file){
        ArrayList<HRV> measurement =new ArrayList<HRV>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            long date = getDateFromFilename(file);

            while((line = br.readLine()) != null) {
                HRV hrv = new HRV();
                hrv.setDate(date);
                hrv.setRrInterval(Long.parseLong(line));
                measurement.add(hrv);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return measurement;
    }

    public long getDateFromFilename(File file){
        long date=0;
        String datetime = file.getName().substring(4,21);
        date = Long.parseLong(datetime);

        return date;
    }

    public void deleteFile(File file){
        file.delete();
    }



    public static HRVAdapter getmInstance() {
        return mInstance;
    }

    public static void setmInstance(HRVAdapter mInstance) {
        HRVAdapter.mInstance = mInstance;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }





    private HRVAdapter(Context pContext){
        mContext=pContext;

    }

    public static HRVAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new HRVAdapter(pContext);
        }
        mContext = pContext;
        return mInstance;
    }

    public boolean validateHRV(Context pContext)
    {
        //TODO Implement validation
        return true;
    }

    public boolean appInstalled() {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(hrvPackage, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    public void installHRV() {
        if (!appInstalled()) {
            try {
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + hrvPackage)), 42);
            } catch (android.content.ActivityNotFoundException anfe) {
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + hrvPackage)), 42);
            }
        }
    }



    public void startHRV(){

        if(appInstalled()) {
            Intent LaunchIntent = mContext.getPackageManager()
                    .getLaunchIntentForPackage(hrvPackage);
            ((Activity)mContext).startActivityForResult(LaunchIntent,42);
        }
        else {
            try {
                ((Activity)mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + hrvPackage)),42);
            } catch (android.content.ActivityNotFoundException anfe) {
                ((Activity)mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" +hrvPackage)),42);
            }
        }

    }


}
