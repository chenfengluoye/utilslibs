package com.ckj.projects;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;


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

    public static boolean ping(String ipAddress) throws Exception{
        int timeOut=3000;
        boolean status=InetAddress.getByName(ipAddress).isReachable(timeOut);   // 当返回值是true时，说明host是可用的
        return status;
    }

    public static String getNet(String url, Map<String,String> headers) throws RuntimeException {
        StringBuilder builder=new StringBuilder();
        try{
            URL urls=new URL(url);
            HttpURLConnection con= (HttpURLConnection) urls.openConnection();
            con.setRequestMethod("GET");
            if(headers!=null){
                for(Map.Entry<String,String> entry:headers.entrySet()){
                    con.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }
            con.setDoInput(true);
            String line=null;

            BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            if(builder.length()>0){
                builder.deleteCharAt(builder.length()-1);
            }
            return builder.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String postNet(String url, Map<String,String> headers,String data) throws RuntimeException {
        StringBuilder builder=new StringBuilder();
        try{
            URL urls=new URL(url);
            HttpURLConnection con= (HttpURLConnection) urls.openConnection();
            con.setRequestMethod("POST");
            if(headers!=null){
                for(Map.Entry<String,String> entry:headers.entrySet()){
                    con.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }
            con.setDoInput(true);
            if(data!=null&&!data.equals("")){
                con.setDoOutput(true);
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
                writer.write(data);
                writer.flush();
            }
            String line=null;
            BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
            if(builder.length()>0){
                builder.deleteCharAt(builder.length()-1);
            }
            return builder.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Map<String,String> getHeader(String headStr){
        Map<String,String> map=new HashMap<>();
        String[] kvs=headStr.split("\n");
        for(String kv:kvs){
            if(kv.contains(":")){
                String k=kv.substring(0,kv.indexOf(":"));
                String v=kv.substring(kv.indexOf(": ")+2);
                map.put(k,v);
            }
        }
        return map;
    }
}
