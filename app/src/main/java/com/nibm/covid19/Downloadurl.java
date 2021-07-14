package com.nibm.covid19;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Downloadurl {
    public  String readtheURL(String placeURL) throws IOException
    {

        String Data="";
        InputStream inputStream =null;
        HttpURLConnection httpURLConnection = null;


        try{
            URL url =new URL(placeURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line ="";
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            Data=stringBuffer.toString();
            bufferedReader.close();
            Log.d("url","data="+Data);


        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            httpURLConnection.connect();
        }
        return  Data;

    }
}
