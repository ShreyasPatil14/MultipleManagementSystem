package validation;
import java.sql.*;


public class UserValidation 
{
    public String[] signIn_validation(String[] arr,String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String uname = DBInfo[1];

            String useIt  ="use Client ;";
            stm.execute(useIt);

            String search = "select Password from Accounts where Username = '"+uname+"' ;";
            ResultSet rs =stm.executeQuery(search);

            String[] ciphertext={""};
            boolean flag = false;

            while(rs.next())
            {
                ciphertext[0] = rs.getString("Password");
                flag=true;
            }
            
            
            if(flag)
            {
                return ciphertext;
            }
            else
            {
                String[] str={"NO_DATA_FOUND"};
                return str;
            }

        }

        catch(Exception e)
        {   
            String[] str1={"No data found"};
            return str1 ;
        }
    }  

    public String[] signup_validation(String[] arr ,String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String uname = DBInfo[1];

            String useIt  ="use Client ;";
            stm.execute(useIt);

            String search = "select * from Accounts where Username = '"+uname+"' ;";
            ResultSet rs =stm.executeQuery(search);

            String[] result ={""};
            
            while(rs.next())
            {
                result[0] = rs.getString("Username");
            }
            
            
            if(result[0].equals(uname))
            {
                String[] str = {"FALSE"};
                return str;
            }   
            
        }
                                                            
        catch(Exception e)
        {   
           System.out.println(e.getMessage());
        }
        String[] str2= {"TRUE"};
        return str2;
    }  
}