package com.ckj.projects;

import java.io.*;
import java.net.*;


/**
 * Created by Chenkaiju
 */
public class HttpsUtils {


    /**
     * 编码
     * @param input
     * @param charset
     * @return
     */
    public static String urlEncode(String input, String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException(var3);
        }
    }

    /**
     * 解码
     * @param input
     * @param charset
     * @return
     */
    public static String urlDecode(String input, String charset) {
        try {
            return URLDecoder.decode(input, charset);
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException(var3);
        }
    }


    public static String httpGetRequest(String url){
        try{
            URL localURL = new URL(url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuilder resultBuffer = new StringBuilder();
            String tempLine = null;
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return resultBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String httpPostRequest(String url,String msg) throws RuntimeException{
        try{
            URL localURL = new URL(url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            OutputStream outs=connection.getOutputStream();
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outs));
            writer.write(msg);
            writer.flush();
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuilder resultBuffer = new StringBuilder();
            String tempLine = null;
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new RuntimeException("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            try {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if(writer!=null){
                    writer.close();
                }if(outs!=null){
                    outs.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return resultBuffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    public static String sendBytesToNet(String urlString,byte[] datas){
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            OutputStream out = con.getOutputStream();
            out.write(datas);
            out.flush();
            out.close();
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            StringBuilder resultBuffer = new StringBuilder();
            String tempLine = null;
            if (con.getResponseCode() >= 300) {
                throw new RuntimeException("HTTP Request is not success, Response code is " + con.getResponseCode());
            }
            try {
                inputStream = con.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
