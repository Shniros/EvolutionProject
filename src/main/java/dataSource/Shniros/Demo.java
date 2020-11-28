package dataSource.Shniros;

import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws SQLException {
        //TransactionCategoryDao category = new TransactionCategoryDao();
        //category.insert(new TransactionCategory().setId(1).setName("Leisure"), SingleConnectionManager.getConnection());
        //AccountDao dao = new AccountDao();
       // TransactionService tc = new TransactionService();
       // tc.CreateTransaction(new TransactionCategory().setId(1) ,2L,3L,BigDecimal.valueOf(2500));
        //System.out.println(dao.countAccountByPersonId(1));
       /* PersonServiceImpl user = new PersonServiceImpl();
        Scanner in = new Scanner(System.in);
        Person person = null;
        do{
            System.out.println("[1] login\n[2] registration\n");
            String ans = in.nextLine();
            System.out.println("Enter e-mail:");
            String email = in.nextLine();
            System.out.println("Enter password:");
            String password = in.nextLine();
            if (ans.equals("1")) {
                person = user.login(email, password);
            } else if (ans.equals("2")) {
                user.registration(new Person(email, password));
            }else {
                new Exception();
            }
        }while(person == null);
        System.out.println("Your data:\n" + person.toString());*/
    }
}
