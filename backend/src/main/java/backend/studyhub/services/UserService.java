package backend.studyhub.services;

import backend.studyhub.data.DataAccess;

public class UserService {
    static public long userRegister(String name, String email, String password) {
        return DataAccess.getInstance().getDataStore().createUser(name, email, password).getId();
    }
}
