package servlets;

import accounts.AccountService;
import com.google.gson.Gson;
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


public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("title", "Sing UP!");
        pageVariables.put("actionUrl", "/signup");
        pageVariables.put("welcomeText","For sign up enter you creds:");
        pageVariables.put("actionButton", "Sing UP!");
        pageVariables.put("NotYetRegistered", "");

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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String email = login + "@example.mail";
        UsersDataSet userProfile = new UsersDataSet(login, password, email);

        try {
            accountService.addNewUser(userProfile);
        } catch (DBException e) {
            e.printStackTrace();
            response.getWriter().println(e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(new StringBuilder("Account created! Login:").append(login)
                .append(" E-mail:").append(email).append("\n<a href=\"http://192.168.0.101:8080/signin\">And now Sign in!</a>").toString());
        //Вариант с выгрузкой json в ответе.
//        Gson gson = new Gson();
//        String json = gson.toJson(userProfile);
//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().println(json);
//        response.getWriter().println("<!DOCTYPE html>\n<p><a href=\"http://localhost:8080/signin\">Sign IN</a></p>\n</html>");
//        response.setStatus(HttpServletResponse.SC_OK);
    }
}
