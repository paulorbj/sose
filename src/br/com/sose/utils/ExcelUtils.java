package br.com.sose.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ExcelUtils {

	 /**
    *
    * @param zipfile the template file
    * @param tmpfile the XML file with the sheet data
    * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
    * @param out the stream to write the result to
    */
	public static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
       ZipFile zip = new ZipFile(zipfile);

       ZipOutputStream zos = new ZipOutputStream(out);

       @SuppressWarnings("unchecked")
       Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
       while (en.hasMoreElements()) {
           ZipEntry ze = en.nextElement();
           if(!ze.getName().equals(entry)){
               zos.putNextEntry(new ZipEntry(ze.getName()));
               InputStream is = zip.getInputStream(ze);
               copyStream(is, zos);
               is.close();
           }
       }
       zos.putNextEntry(new ZipEntry(entry));
       InputStream is = new FileInputStream(tmpfile);
       copyStream(is, zos);
       is.close();

       zos.close();
   }

   public static void copyStream(InputStream in, OutputStream out) throws IOException {
       byte[] chunk = new byte[1024];
       int count;
       while ((count = in.read(chunk)) >=0 ) {
         out.write(chunk,0,count);
       }
   }
}
