package com.lrm.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 网络
 */
public class FileTypeUtils {

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    //初始化文件类型信息
    static{
        getAllFileType();
    }

    /**
     * 常见文件头信息
     */
    private static void getAllFileType()
    {
        //JPEG (jpg)
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
        //PNG (png)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        //GIF (gif)
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        //TIFF (tif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        //16色位图(bmp)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        //24位位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        //256色位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");
        //CAD (dwg)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        //HTML (html)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");
        //HTM (htm)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
        //css
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
        //js
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
        //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        //Photoshop (psd)
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        //Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        //MS Excel 注意：word、msi 和 excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc");
        //Visio 绘图
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd");
        //MS Access (mdb)
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf");
        //rmvb/rm相同
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb");
        //flv与f4v相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv");
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        //wmv与asf相同
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv");
        //Wave (wav)
        FILE_TYPE_MAP.put("52494646e27807005741", "wav");
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        //MIDI (mid)
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        //可执行文件
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        //jsp文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        //MF文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        //xml文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
        //xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
        //java文件
        FILE_TYPE_MAP.put("7061636b616765207765", "java");
        //bat文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        //gz文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        //bat文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");
        //bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        //bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        //bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        //docx文件
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");
        //WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");

        //Quicktime (mov)
        FILE_TYPE_MAP.put("6D6F6F76", "mov");
        //WordPerfect (wpd)
        FILE_TYPE_MAP.put("FF575043", "wpd");
        //Outlook Express (dbx)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
        //Outlook (pst)
        FILE_TYPE_MAP.put("2142444E", "pst");
        //Quicken (qdf)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
        //Windows Password (pwl)
        FILE_TYPE_MAP.put("E3828596", "pwl");
        //Real Audio (ram)
        FILE_TYPE_MAP.put("2E7261FD", "ram");
    }

    /**
     * 得到上传文件的文件头
     * @param src 文件的byte[]
     * @return 文件头
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据制定文件的文件头判断其文件类型
     * @param file 上传的文件
     * @return 文件类型
     */
    public static String getFileType(MultipartFile file){
        String res = null;
        try {
            InputStream is = file.getInputStream();
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            System.out.println(fileCode);


            //这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while(keyIter.hasNext()){
                String key = keyIter.next();
                if(key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())){
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}