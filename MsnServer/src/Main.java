import br.senac.rj.msn.controller.ServerController;
import br.senac.rj.msn.model.User;
import br.senac.rj.msn.view.ServerChatView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        User serverUser = new User("Servidor");
        ServerController server = new ServerController(serverUser);
        ServerChatView chatView = new ServerChatView(server);

        server.connectClient(chatView);

    }
}