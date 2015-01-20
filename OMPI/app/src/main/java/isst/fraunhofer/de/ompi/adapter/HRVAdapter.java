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
import java.io.FileReader;
import java.util.ArrayList;

import isst.fraunhofer.de.ompi.model.HRV;

/**
 * Created by durin on 19/12/2014.
 */
public class HRVAdapter {

    final static String hrvPackage = "com.RMT.ompihrv";
    public static String MY_PACKAGE_NAME;

    private static HRVAdapter mInstance;
    private static Context mContext;
    private PersonAdapter personAdapter;
    private StateAdapter stateAdapter;


    private HRVAdapter(Context pContext) {
        mContext = pContext;
        personAdapter = PersonAdapter.getInstance(pContext);
        stateAdapter = StateAdapter.getInstance(pContext);
        MY_PACKAGE_NAME = pContext.getApplicationContext().getPackageName();
    }

    public static HRVAdapter getInstance(Context pContext) {
        if (mInstance == null) {
            mInstance = new HRVAdapter(pContext);
        }
        mContext = pContext;
        return mInstance;
    }

    public ArrayList<HRV> getHRV() {

        File files[] = checkFiles();
        if (files!=null && files.length>0)
            return readFile(files[0]);
        else return null;

    }

    /**
     * Sucht die gespeicherte HRV-Daten.
     *
     * @return Anzahl von gefundenen Dateien
     */
    private File[] checkFiles() {

        // File sdcard = Environment.getExternalStorageDirectory();
        // String s1=Environment.getExternalStorageState();
        File hrvDirectory;

            hrvDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), hrvPackage);
            if (hrvDirectory.listFiles()==null)
            hrvDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), hrvPackage);



        File files[] = hrvDirectory.listFiles();

        Log.d("Files", "Path: " + hrvDirectory.getPath());
        //Log.d("Files", "Size: " + files.length);
        return files;
    }

    private ArrayList<HRV> readFile(File file) {
        ArrayList<HRV> measurement = new ArrayList<HRV>();


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            long date = getDateFromFilename(file);

            while ((line = br.readLine()) != null) {
                HRV hrv = new HRV();
                hrv.setDate(date);
                //try{
                hrv.setRrInterval(Long.parseLong(line));
            //}
                /*catch (Exception e){
                    Log.d("Files",line + " Error by HRV Measurement");
                    hrv.setRrInterval(0);
                }*/
                hrv.setPersonId(personAdapter.getPerson().getLongId());
                hrv.setDayNr(stateAdapter.getState().getDayNr());
                hrv.setFirstHRV(stateAdapter.getState().isFirstHrv());
                measurement.add(hrv);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return measurement;
    }

    public long getDateFromFilename(File file) {
        long date = 0;
        String datetime = file.getName().substring(4, 21);
        date = Long.parseLong(datetime);
        return date;
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


    public boolean isHRVValid(Context pContext) {
        if (checkFiles()!=null&&checkFiles().length!=0)
            return true;
        else return false;
    }

    public boolean appInstalled() {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(hrvPackage, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
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


    public void startHRV() {

        if (appInstalled()) {
            Intent LaunchIntent = new Intent();
            LaunchIntent.setClassName(hrvPackage,hrvPackage+".ActivityHomescreen");
            ((Activity) mContext).startActivityForResult(LaunchIntent, 600);
        }
         else {
            try {
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + hrvPackage)), 42);
            } catch (android.content.ActivityNotFoundException anfe) {
                ((Activity) mContext).startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + hrvPackage)), 42);
            }
        }

    }

    public void deleteHrvFile() {
        if(checkFiles()!=null) {
            for (File file : checkFiles()) {
                file.delete();
            }
        }
    }

}
