package Tasks;

import Bank.ATM;
import Exceptions.NoCardInsertedException;
import Exceptions.NotEnoughBalanceException;
import Results.BalanceReturnedResult;
import Results.Result;

public class GetBalanceTask implements Task {
    private ATM atm;

    @Override
    public Result run() throws Exception {
        Thread.sleep(1000);
        if (atm.isNoCardInserted())
            throw new NoCardInsertedException();
        if (atm.getCard().getBalance() < 10)
            throw new NotEnoughBalanceException();
        atm.getCard().decreaseBalance(10);
        return new BalanceReturnedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
