package ru.apolyakov.servlets;

import ru.apolyakov.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MirrorServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException
    {
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String message = request.getParameter("key");
        response.setContentType("text/html;charset=utf-8");
        if(message == null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else
        {
            response.getWriter().println(message);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {

    }
}
