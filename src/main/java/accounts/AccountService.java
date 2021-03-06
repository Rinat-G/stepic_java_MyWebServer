package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService implements AccountServiceInterface{
    //    private final Map<String, UsersDataSet> loginToProfile;
    private final Map<String, UsersDataSet> sessionIdToProfile;
    private final DBService dbService;

    public AccountService() {
//        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbService = new DBService();
        dbService.printConnectInfo();
    }

    @Override
    public void addNewUser(UsersDataSet usersDataSet) throws DBException {
        dbService.addUser(usersDataSet);
    }

    public UsersDataSet getUserByLogin(String login) throws DBException {
        return dbService.getUser(login);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet usersDataSet) {
        sessionIdToProfile.put(sessionId, usersDataSet);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    @Override
    public void removeUser() {

    }

    @Override
    public int getUsersLimit() {
        return 0;
    }

    @Override
    public void setUsersLimit(int usersLimit) {

    }

    @Override
    public int getUsersCount() {
        return 0;
    }
}
