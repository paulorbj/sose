package br.com.sose.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ConteudoServlet extends HttpServlet
{
    private static final long serialVersionUID = 4355325334781642242L;
   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    	{
    	    String filename = URLDecoder.decode(request.getPathInfo(), "UTF-8");
    	    File file = new File("C:/arquivo_servilogi", filename);

    	    response.setContentType(getServletContext().getMimeType(file.getName()));
    	    response.setContentLength(new Long(file.length()).intValue());
    	    response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

    	    BufferedInputStream input = null;
    	    BufferedOutputStream output = null;

    	    try {
    	        input = new BufferedInputStream(new FileInputStream(file));
    	        output = new BufferedOutputStream(response.getOutputStream());

    	        byte[] buffer = new byte[8192];
    	        int length;
    	        while ((length = input.read(buffer)) > 0) {
    	            output.write(buffer, 0, length);
    	        }
    	    } finally {
    	        if (output != null) try { output.close(); } catch (IOException ignore) {}
    	        if (input != null) try { input.close(); } catch (IOException ignore) {}
    	    }
    	}
}
