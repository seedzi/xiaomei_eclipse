package com.xiaomei.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtils {

//	http://www.2cto.com/kf/201203/123919.html
	/**
	 * 上传文件到服务器
	 * @param filePath  文件本地目录
	 * @param actionUrl  服务器url
	 * @return
	 */
	public static boolean uploadFile(final String filePath,final String actionUrl){
		String end ="\r\n";
        String twoHyphens ="--";
        String boundary ="*****";
        String newName ="image.jpg";
        DataOutputStream ds = null;
        try{
          URL url =new URL(actionUrl);
          HttpURLConnection con= (HttpURLConnection)url.openConnection();
          /* 允许Input、Output，不使用Cache */
          con.setDoInput(true);
          con.setDoOutput(true);
          con.setUseCaches(false);
          /* 设置传送的method=POST */
          con.setRequestMethod("POST");
          /* setRequestProperty */
          con.setRequestProperty("Connection", "Keep-Alive");
          con.setRequestProperty("Charset", "UTF-8");
          con.setRequestProperty("Content-Type",
                             "multipart/form-data;boundary="+boundary);
          /* 设置DataOutputStream */
          ds = new DataOutputStream(con.getOutputStream());
          ds.writeBytes(twoHyphens + boundary + end);
          ds.writeBytes("Content-Disposition: form-data; "+
                        "name=\"file1\";filename=\""+
                        newName +"\""+ end);
          ds.writeBytes(end);  
          /* 取得文件的FileInputStream */
          FileInputStream fStream =new FileInputStream(filePath);
          /* 设置每次写入1024bytes */
          int bufferSize =1024;
          byte[] buffer = new byte[bufferSize];
          int length =-1;
          /* 从文件读取数据至缓冲区 */
          while((length = fStream.read(buffer)) !=-1){
            /* 将资料写入DataOutputStream中 */
            ds.write(buffer, 0, length);
          }
          ds.writeBytes(end);
          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
          fStream.close();
          ds.flush();
          /* 取得Response内容 */
          InputStream is = con.getInputStream();
          int ch;
          StringBuffer b =new StringBuffer();
          while( ( ch = is.read() ) !=-1 ){
            b.append( (char)ch );
          }
          return true;
        }catch(Exception e){
        	return false;
        }finally{
        	if(ds!=null)
				try {
					ds.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
		
	}

}
