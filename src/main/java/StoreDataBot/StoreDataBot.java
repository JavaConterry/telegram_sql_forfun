package StoreDataBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Function;

public class StoreDataBot extends TelegramLongPollingBot {
/** TODO: clean the code, terrible to read these all. Wrong view of program. Everything depends on user, not on command.
 *  TODO: {@link CommandHandler} the user is needed (to not).
 */
    String BOT_TOKEN = "5775146274:AAEt7O_iCtFczoVv5nkU6QROP-naDlNgad0";
    ServerAccess dao = new ServerAccess();
    List<BotCommand> commandList = new ArrayList<BotCommand>();



    @Override
    public void onUpdateReceived(Update update) {
        Message recievedMessage = update.getMessage();
        if(recievedMessage.getText().startsWith("/")){
            processCommand(recievedMessage);
        }
        else{
            processText(recievedMessage);
        }
    }

    private void processCommand(Message commandMessage) {
        String textValue = commandMessage.getText().substring(1, commandMessage.getText().length());
        CommandHandler.runCommand(textValue, commandMessage.getFrom().getUserName());
    }

    private void processText(Message textMessage){
        SendMessage message = new SendMessage();
        message.setChatId(textMessage.getChatId());
        message.setText(textMessage.getText());

        System.out.println("message was pulled");
        try {
            execute(message);
            dao.saveToTable(textMessage.getFrom().getUserName(), message.getText());
            dao.showStorageTable(textMessage.getFrom().getUserName());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return "Data Storage";
    }


    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onRegister() {
        //TODO(maybe): the initial command list does not work
        commandList.stream().forEach(v -> commandList.remove(v));
        commandList.add(new BotCommand("allfiles", "get all my files"));
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commandList);
        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        super.onRegister();
    }


}
