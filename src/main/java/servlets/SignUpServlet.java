package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rocky on 10.06.2017.
 */
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

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.instance().getPage("page2.html", pageVariables));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String userMail = login + "@example.mail";
        UserProfile userProfile = new UserProfile(login, pass, userMail);

        accountService.addNewUser(userProfile);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        //Вариант с выгрузкой json в ответе.
//        Gson gson = new Gson();
//        String json = gson.toJson(userProfile);
//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().println(json);
//        response.getWriter().println("<!DOCTYPE html>\n<p><a href=\"http://localhost:8080/signin\">Sign IN</a></p>\n</html>");
//        response.setStatus(HttpServletResponse.SC_OK);
    }
}
