package contraller.account;

import model.Account;
import model.Employee;
import util.CrudUtil;

import java.sql.SQLException;

public class AccountContraller implements AccountService{
    @Override
    public boolean createAccount(Account account) {
        if (checkEmail(account.getEmp_ID(),account.getEmail())){
            try {
               return CrudUtil.execute("insert into account values (?,?,?)",account.getEmp_ID(),account.getEmail(),account.getPassword());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }

    }
    boolean checkEmail(String id,String email){
        try {
            Employee emp = CrudUtil.execute("select * from employee where Emp_ID = '" + id + "'");
            if(emp.getEmail().equals(email)){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
