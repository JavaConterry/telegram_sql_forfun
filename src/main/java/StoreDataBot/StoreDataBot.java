package StoreDataBot;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Async;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class StoreDataBot extends TelegramLongPollingBot {

    String BOT_TOKEN = "5775146274:AAEt7O_iCtFczoVv5nkU6QROP-naDlNgad0";
    ServerAccess dao = new ServerAccess();

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(update.getMessage().getText());

        System.out.println("message was pulled");
        try {
            execute(message);
            dao.saveToTable(update.getMessage().getFrom().getUserName(), message.getText());
            dao.showStorageTable();
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
}
