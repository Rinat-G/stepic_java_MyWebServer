package servlets;

import accounts.AccountService;
import chat.ChatService;
import chat.ChatWebSocket;
import dbService.dataSets.UsersDataSet;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final ChatService chatService;
    private final AccountService accountService;

    public WebSocketChatServlet(AccountService accountService) {
        this.chatService = new ChatService();
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String sessionID = req.getSession().getId();
        UsersDataSet user = accountService.getUserBySessionId(sessionID);
        if (user == null){
            resp.sendRedirect("http://192.168.0.101:8080/signin");
            resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            return;
        }
        pageVariables.put("username",user.getLogin());

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("chat.html", pageVariables));
        resp.setContentType("text/html;charset=utf-8");

    }



    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }
}