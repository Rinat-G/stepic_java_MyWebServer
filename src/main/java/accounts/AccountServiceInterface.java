package accounts;

import dbService.DBException;
import dbService.dataSets.UsersDataSet;

/**
 * Created by R0cky on 15.06.2017.
 */
public interface AccountServiceInterface {

    void addNewUser(UsersDataSet usersDataSet) throws DBException;

    void removeUser();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();
}
