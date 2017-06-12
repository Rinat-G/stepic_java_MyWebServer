package servlets;

import accounts.AccountService;
import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SignInServlet extends HttpServlet {
    private final AccountService accountService;
    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("title", "Sing IN!");
        pageVariables.put("actionUrl", "/signin");
        pageVariables.put("welcomeText","For sign in enter you creds:");
        pageVariables.put("actionButton", "Sing IN!");
        pageVariables.put("NotYetRegistered", "Not yet registered? <a href=\"http://192.168.0.101:8080/signup\">Sign up!</a>");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("page2.html", pageVariables));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UsersDataSet profile;
        try {
            profile = accountService.getUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
            response.getWriter().println(e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }


        if (profile == null || !profile.getPassword().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            return;
        }

        accountService.addSession(request.getSession().getId(), profile);
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("http://192.168.0.101:8080/chat");
//        response.getWriter().println("Authorized: " + login);
    }
}
