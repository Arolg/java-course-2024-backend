package edu.java.bot.telegram.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ListCommandTest {
    @Test
    void generateAnswerTest() {
        try {
            Method method = ListCommand.class.getDeclaredMethod("generateAnswer");
            method.setAccessible(true);
            mockStatic(Command.class);

            ListCommand list = new ListCommand();
            String result = (String) method.invoke(list);
            Assertions.assertEquals("Список отслеживаемых ссылок пуст", result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
