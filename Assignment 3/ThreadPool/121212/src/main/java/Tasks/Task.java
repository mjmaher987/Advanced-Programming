package Tasks;

import Bank.ATM;
import Results.Result;

public interface Task {
    public Result run() throws Exception;
    public void setATM(ATM atm);
}
