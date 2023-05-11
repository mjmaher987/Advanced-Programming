package Tasks;

import Bank.ATM;
import Exceptions.NoCardInsertedException;
import Exceptions.WrongPasswordException;
import Results.PasswordChangedResult;
import Results.Result;

public class ChangePasswordTask implements Task {
    private ATM atm;
    private String newPass;

    public ChangePasswordTask (String newPassword) {
        this.newPass = newPassword;
    }
    @Override
    public Result run() throws Exception {
        Thread.sleep(500);
        if (atm.isNoCardInserted())
            throw new NoCardInsertedException();
        if (!newPass.matches("\\d{4}"))
            throw new WrongPasswordException();
        atm.getCard().setPassword(newPass);
        return new PasswordChangedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
