package backend.studyhub.services;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.studyhub.data.DataAccess;
import backend.studyhub.data.DataStore;
import backend.studyhub.entities.User;
import backend.studyhub.exceptions.HttpBadRequestException;
import backend.studyhub.exceptions.UnauthorizedException;

public class UserService {

    private static Map<String, Long> tokenToUserId = new HashMap<>();

    static private String generateToken(long userId) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[30];
        random.nextBytes(bytes);
        String token = bytes.toString();
        tokenToUserId.put(token, userId);
        return token;
    }

    static private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static public long getUserIdFromToken(String token) throws UnauthorizedException {
        if (!tokenToUserId.containsKey(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        return tokenToUserId.get(token);
    }

    static public String userRegister(String name, String email, String password) throws HttpBadRequestException {
       DataStore dataStore = DataAccess.getInstance().getDataStore();
        if (dataStore.emailExists(email)) {
            throw new HttpBadRequestException("Email already in use");
        } else if (!isValidEmail(email)) {
            throw new HttpBadRequestException("Invalid email");
        }
        long userId = dataStore.createUser(name, email, password).getId();
        return generateToken(userId);
    }

    static public String userLogin(String email, String password) throws UnauthorizedException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        if (!dataStore.emailExists(email)) {
            throw new UnauthorizedException("Invalid email");
        }
        long userId = dataStore.getUser(email).getId();
        if (!dataStore.getUser(userId).testPassword(password)) {
            throw new UnauthorizedException("Invalid password");
        }
        return generateToken(userId);
    }

    static public void userLogout(String token) throws UnauthorizedException {
        if (!tokenToUserId.containsKey(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        tokenToUserId.remove(token);
    }

    static public User getUser(long id) {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        return dataStore.getUser(id);
    }

}
