package Server;

public class Main {
    public static void main(String[] args) {
        NetworkService networkService= NetworkService.getInstance();
        networkService.init(7660);
        networkService.acceptNewClient();
        networkService.acceptNewClient();
        networkService.acceptNewClient();
        networkService.closeWelcomeSocket();
    }
}
