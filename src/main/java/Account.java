import lombok.Getter;

public class Account {

    @Getter
    private final String name;
    public static final String BEGIN_END_SPACE = "Name cannot begin or end with a space";
    public static final String NAME_LENGTH = "Name length must be between 3 and 19 characters";
    public static final String NO_SPACE = "There must be a space between name and surname";
    public static final String ONE_SPACE = "There must only be one space between name and surname";
    public static final String VALID_NAME = "Valid name";

    public Account(String name) {
        this.name = name;
    }

    public boolean checkName() {
        if (Character.isWhitespace(name.charAt(0)) || Character.isWhitespace(name.charAt(name.length() - 1))) {
            System.out.println(BEGIN_END_SPACE);
            return false;
        }
        if (name.length() < 3 || name.length() > 19) {
            System.out.println(NAME_LENGTH);
            return false;
        }
        int spaceCount = 0;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isWhitespace(name.charAt(i))) spaceCount++;
        }
        if (spaceCount == 0) {
            System.out.println(NO_SPACE);
            return false;
        }
        if (spaceCount > 1) {
            System.out.println(ONE_SPACE);
            return false;
        }
        System.out.println(VALID_NAME);
        return true;
    }

}