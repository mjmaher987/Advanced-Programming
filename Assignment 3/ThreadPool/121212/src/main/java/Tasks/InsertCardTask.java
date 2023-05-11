package Tasks;

import Bank.ATM;
import Bank.Card;
import Exceptions.WrongPasswordException;
import Results.CardInsertedResult;
import Results.Result;

public class InsertCardTask implements Task {
    private ATM atm;
    private Card card;
    private String password;

    public InsertCardTask(Card card, String password) {
        this.card = card;
        this.password = password;
    }

    @Override
    public Result run() throws Exception {
        Thread.sleep(2000);
        if (!card.getPassword().equals(password))
            throw new WrongPasswordException();
        atm.setCard(card);
        return new CardInsertedResult();
    }

    @Override
    public void setATM(ATM atm) {
        this.atm = atm;
    }
}
