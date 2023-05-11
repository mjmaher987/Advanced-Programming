package Tasks;

import Bank.ATM;
import Bank.Card;
import Exceptions.WrongPasswordException;
import Results.AccountCreatedResult;
import Results.Result;

public class CreateAccountTask implements Task {
    ATM atm;
    String cardNo;
    String password;

    public CreateAccountTask (String cardNo, String password) {
        this.cardNo = cardNo;
        this.password = password;
    }

    @Override
    public Result run() throws Exception {
        Thread.sleep(2000);
        if (!password.matches("\\d{4}"))
            throw new WrongPasswordException();
        new Card(cardNo, password);
        return new AccountCreatedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
