package servlets;

import accounts.AccountService;
import chat.ChatService;
import chat.ChatWebSocket;
import configuration.SrvConfig;
import dbService.dataSets.UsersDataSet;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
        if (user == null) {
            resp.sendRedirect(SrvConfig.getInstance().getHttpHostPort() +
                    "/signin");
            resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            return;
        }
        pageVariables.put("username", user.getLogin());
        pageVariables.put("hostPort", SrvConfig.getInstance().getHostPort());
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(PageGenerator.instance().getPage("chat.html", pageVariables));


    }


    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }
}