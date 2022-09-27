import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class PrinterTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private String outputString;
    private String accountName = "Max Smith";
    private String errorMessage = "Cannot print. There is a name error";
    private Account account;
    private Printer printer;

    @Before
    @Step("Создание аккаунта, перехват сообщения консоли")
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        account = new Account(accountName);
        printer = new Printer();
    }

    @Test
    @DisplayName("Вывод в консоль имени при печати валидного имени")
    public void checkThatValidNamePrintsName() {
        printer.printName(account);
        outputString = outputStreamCaptor.toString().trim();
        assertTrue(outputString.contains(accountName));
    }

    @Test
    @DisplayName("Вывод в консоль ошибки при попытке печатать невалидное имя")
    public void checkThatInvalidNamePrintsErrorMessage() {
        Account mockAccount = Mockito.spy(account);
        Mockito.when(mockAccount.checkName()).thenReturn(false);
        printer.printName(mockAccount);
        outputString = outputStreamCaptor.toString().trim();
        assertTrue(outputString.contains(errorMessage));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
}
