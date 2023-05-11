package Tasks;

import Bank.ATM;
import Exceptions.InvalidCashAmountException;
import Exceptions.NoCardInsertedException;
import Results.CashDepositedResult;
import Results.Result;

public class DepositCashTask implements Task {
    private ATM atm;
    private int amount;


    public DepositCashTask (int amount) {
        this.amount = amount;
    }
    @Override
    public Result run() throws Exception {
        Thread.sleep(1000);
        if (atm.isNoCardInserted())
            throw new NoCardInsertedException();
        if (amount <= 0)
            throw new InvalidCashAmountException();
        atm.getCard().increaseBalance(amount);
        return new CashDepositedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
