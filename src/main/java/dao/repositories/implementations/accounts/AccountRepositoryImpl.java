package dao.repositories.implementations.accounts;


import entety.Consumer;
import entety.accounts.Account;
import dao.ConnectionBank;
import dao.repositories.enums.SQLByBankAcc;
import dao.repositories.enums.SQLByID;
import dao.repositories.interfaces.AccountRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository<Account> {

    private HandlerRepository handler = HandlerRepository.getInstance();


    @Override
    public Account findAccountByBankAccount(long bankAcc) {
        return handler.giveAcc(SQLByBankAcc.ACC.getSql() + bankAcc);
    }

    @Override
    public List<Account> findAccountsByConsumerId(int consumerId) {
        final String SQL = "SELECT * FROM accounts WHERE idCunsumer = " + consumerId;
        return getListAccByIdConsumer(SQL);
    }

    @Override
    public Account findAccountById(int id) { return handler.giveAcc(SQLByID.ACC.getSql() + id); }

    @Override
    public List<Account> findAccountsConsumer(Consumer consumer) {
        final String SQL = "SELECT * FROM accounts WHERE idCunsumer = " + consumer.getId();
        return getListAccByIdConsumer(SQL);
    }

    private List<Account> getListAccByIdConsumer(String sql){
        List<Account> list = new ArrayList<>();
        try(Connection connection = ConnectionBank.getConn();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int idConsumer = resultSet.getInt("idCunsumer");
                double balance = resultSet.getDouble("balance");
                long bankAcc = resultSet.getLong("bank_acc");

                Account account = new Account();
                account.setId(id);
                account.setIdConsumer(idConsumer);
                account.setBalance(balance);
                account.setBankAccount(bankAcc);
                list.add(account);
            }
            if (list.isEmpty())
                return Collections.emptyList();

        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

}