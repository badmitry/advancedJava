package hw7Chat.server;

public interface AuthService {
    String getNickName(String login, String password);
    boolean registration(String login, String password, String nickname);
}
