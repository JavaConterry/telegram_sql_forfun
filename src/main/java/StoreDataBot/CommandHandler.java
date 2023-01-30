package StoreDataBot;


import java.sql.SQLException;

//TODO get rid of case close.
public class CommandHandler {
    // recieve name, handle the needed func
    public static void runCommand(String command, String username){ //fixme: user is needed
        switch (command){
            case "allfiles":
                executeAllFiles(username);
                break;
            case "...":
                break;
        }
    }

    private static void executeAllFiles(String username) { //TODO#1! By doint User-first dependancy resolve the problem of non executing text from here.
        ServerAccess dao = new ServerAccess();
        try {
            dao.showStorageTable(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
