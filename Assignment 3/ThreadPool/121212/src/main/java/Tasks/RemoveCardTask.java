package Tasks;

import Bank.ATM;
import Exceptions.NoCardInsertedException;
import Results.CardRemovedResult;
import Results.Result;

public class RemoveCardTask implements Task {
    private ATM atm;

    @Override
    public Result run() throws Exception {
        Thread.sleep(2000);
        if (atm.isNoCardInserted())
            throw new NoCardInsertedException();
        atm.setCard(null);
        return new CardRemovedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
