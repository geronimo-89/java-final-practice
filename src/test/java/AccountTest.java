import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AccountTest {
    private Account account;
    private String name;
    private String printMessage;
    private boolean valid;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private String outputString;

    public AccountTest(String name, String printMessage, boolean valid) {
        this.name = name;
        this.printMessage = printMessage;
        this.valid = valid;
    }

    @Parameterized.Parameters(name = "Test data: name - {0}, is valid - {2}, message - {1}")
    public static Object[][] data() {
        return new Object[][]{
                {" Max Smith", Account.BEGIN_END_SPACE, false},
                {"Max Smith ", Account.BEGIN_END_SPACE, false},
                {"Max Smith", Account.VALID_NAME, true},
                {"Ma", Account.NAME_LENGTH, false},
                {"Maximillian Johnson", Account.VALID_NAME, true},
                {"Maxximillian Johnson", Account.NAME_LENGTH, false},
                {"MaxSmith", Account.NO_SPACE, false},
                {"Max John Smith", Account.ONE_SPACE, false}
        };
    }

    @Before
    @Step("Создание аккаунта, перехват сообщения консоли")
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        account = new Account(name);
    }

    @Test
    @DisplayName("Проверка правил валидности имени")
    @Description("В строке не меньше 3 и не больше 19 символов, в строке есть только один пробел, пробел стоит не в начале и не в конце строки.")
    public void testNameValidationRules() {
        outputString = outputStreamCaptor.toString().trim();
        assertEquals(valid, account.checkName());
    }

    @Test
    @DisplayName("Проверка сообщений об ошибке")
    public void testMessage() {
        account.checkName();
        outputString = outputStreamCaptor.toString().trim();
        assertEquals(printMessage, outputString);
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }
}
