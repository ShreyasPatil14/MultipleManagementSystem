package databasQuery;
import java.sql.*;
public class AccountsDB
{   
    public int getAttributeCount(String[] arr,String TBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useIt  ="use Client ;";
            stm.execute(useIt);
            System.out.println("DATA : "+TBName);

            String query = "select no_of_attribute from tbinfo where TBName = '"+TBName+"';";
            ResultSet rs =stm.executeQuery(query);
            int x=0;
            while(rs.next())
            {
                x = rs.getInt("no_of_attribute");
            }
            return x;
            
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    public void ClientDB(String[] arr)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String dbName = "create database Client;";
            stm.execute(dbName);

            String useIt  ="use Client ;";
            stm.execute(useIt);

            String tbName = "create table Accounts(Username varchar(80),Password varchar(80)) ;";
            stm.execute(tbName);
        }
        catch (Exception e) 
        {
            System.out.println("Can't Use It");
            e.printStackTrace();
        }
    }
    public void EntryIntoAccountsTB(String[] arr,String uname,String pass)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useIt  ="use Client ;";
            stm.execute(useIt);

            String record = "insert into Accounts values ('"+uname+"' , '"+pass+"' )";
            stm.execute(record);

            System.out.println("Record Inserted Successfully");

        }
        catch (Exception e)
        {   
            System.out.println("Can't Insert");
            e.printStackTrace();
        }
    }
    public void ClientDBInfo(String[] arr)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useIt="use ClientDBInfo ;";
            stm.execute(useIt);
        }
        catch(Exception e)
        {

        }
    } 
    
}