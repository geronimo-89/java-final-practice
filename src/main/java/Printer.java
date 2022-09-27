public class Printer {

    public void printName(Account account) {
        if (account.checkName())
            System.out.println(account.getName());
        else System.out.println("Cannot print. There is a name error");
    }

}