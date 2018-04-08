package ru.apolyakov.servlets;

import com.google.gson.Gson;
import ru.apolyakov.accounts.AccountService;
import ru.apolyakov.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    // add new user
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || accountService.getUserByLogin(login) != null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile profile = password == null ? new UserProfile(login) : new UserProfile(login, password);
        accountService.addNewUser(profile);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
