package metedata;

import java.sql.*;

public class MetaDataInfo
{

    public void insertAccounts(String[] arr,String uname,String pass)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useit ="use client;";
            stm.execute(useit);

            String query = "insert into accounts values ('"+uname+"' , '"+pass+"' );";
            stm.execute(query);

            System.out.println("USERNAE : "+uname +" ENTERED INTO ACOUNTS SUCESSFULLY");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());

        }
    }
    
    public void insertClientDBInfo(String[] arr,String uname)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            MetaDataInfo obj= new MetaDataInfo();
            int no_of_DB=obj.databaseCount(arr, uname);

            String useIt ="use client ;";
            stm.execute(useIt);

            String insert1 = "insert into clientdbinfo values ('"+uname+"' , "+no_of_DB+" );";
            stm.execute(insert1);
             
            System.out.println("INSERTED USERNAME"+uname+" AND "+no_of_DB+" INTO CLIENTDBINFO TABLE SUCESSFULLY");

        }

        catch(Exception e)
        {
            System.out.println("......NOT INSERTED INTO CLIENTDBINFO.......");
            System.out.println(e.getMessage());

        }

    }

    public void insertDBInfo(String[] arr,String uname, String DBName ,int no_of_tb)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useIt ="use client ;";
            stm.execute(useIt);

            String insert1 = "insert into dbinfo values ('"+uname+"' , '"+DBName+"' , "+no_of_tb+" )";
            stm.execute(insert1);
             
            System.out.println("INSERTED USERNAME : "+uname+"DATABASE NAME : "+DBName+" NUMBER OF TABLES : "+no_of_tb+" INTO DBINFO SUCESSFULLY");

        }

        catch(Exception e)
        {
            System.out.println(".......NOT INSERTED INTO DBINFO .......");
        } 

    }

    public void insertTBInfo(String[] arr, String DBName,String TBName,int no_of_attr)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useIt ="use client ;";
            stm.execute(useIt);

            String insert1 = "insert into tbinfo values ('"+DBName+"' , '"+TBName+"' , "+no_of_attr+" )";
            stm.execute(insert1);
             
            System.out.println("INSERTED INTO TBINFO SUCESSFULLY");

        }

        catch(Exception e)
        {
            System.out.println(".......NOT INSERTED INTO TBINFO.......");
            System.out.println(e.getMessage());
        }

    }

    public int tableCount(String[] arr,String DBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useit = "use client ;";
            stm.execute(useit);

            String query ="select no_of_TB from dbinfo where DBname = '"+DBName+"' ; ";
            ResultSet rs = stm.executeQuery(query);

            int num=0;
            while(rs.next())
            {
                num=rs.getInt(1);
            }

            System.out.println("THE COUNT OF TABLES FROM TBINFO TABLE IS : "+num);
            return num;

        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }

    }

    public int databaseCount(String[] arr, String user)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useit = "use client ;";
            stm.execute(useit);

            String query ="select count(distinct DBName) from dbinfo where username ='"+user+"' ;";
            ResultSet rs = stm.executeQuery(query);

            int num=0;
            while(rs.next())
            {
                num=rs.getInt(1);
            }
            System.out.println("THE COUNT OF DATABASES FROM DBINFO TABLE IS :"+num);
            return num;

        } 

        catch(Exception e)
        {
            return -1;
        }

    }

    public int getRecordCount(String[] arr,String DBName,String TBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use "+DBName+";";
            stm.execute(useit);

            String query = " select count(*) from  "+TBName+";";
            ResultSet rs = stm.executeQuery(query);

            int x=0;
            while(rs.next())
            {
                x=rs.getInt("count(*)");
            }
            System.out.println("VALUE X : "+x);
            return x;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int getAttributeCount(String[] arr,String DBName,String TBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
        

            String useit = "use "+DBName+";";
            stm.execute(useit);

            String query ="select * from " + TBName+";";
            ResultSet rs = stm.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            
            int no_of_attr = rsmd.getColumnCount();
            return no_of_attr;

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public String getRecordClientDBInfo(String[] arr,String user)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);
            
            String str="";

            String query = "select username from clientdbinfo where username = '"+user+"' ;";
            ResultSet rs = stm.executeQuery(query);

            while(rs.next())
            {
                str = str+rs.getString("username");
            }

            if(str.equals(user))
            {
                return "USER_EXIST";
            }

            return "USER_NOT_EXIST";

        }
        
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "NOT_FOUND";
        }
    }

    public String[] getRecordDBInfo(String[] arr,String user,String DBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);
            
            String[] str=new String[3];
            String str1="";
            String str2="";
            int x=0;

            String query = "select * from dbinfo where username = '"+user+"' and DBName = '"+DBName+"' ;";
            ResultSet rs = stm.executeQuery(query);

            while(rs.next())
            {
                str1=str1+rs.getString(1);
                str2 = str2+rs.getString(2);
                x=rs.getInt(3);
            }
            System.out.println("STR1"+str1);
            System.out.println("STR2"+str2);
            System.out.println("X "+x);

            String str3 = String.valueOf(x);

            str[0]=str1;
            str[1]=str2;
            str[2]=str3;

            System.out.println("EXCUTED");
            return str;   
            
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            String[] ms ={"",""};
            return ms;
            
        }
    }

    public String getRecordClientDBInfo(String[] arr,String user,int DB_Count)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);

            String query =" select * from clientdbinfo where username ='"+user+"' and no_of_DB ='"+DB_Count+"' ;";
            ResultSet rs = stm.executeQuery(query);

        
            String str1="";
            int result =0;
            while(rs.next())
            {
                str1 = str1+rs.getString(1);
                result = rs.getInt(2);
            }

            if(str1.equals(user))
            {
                if(result==DB_Count)
                {
                    return "FALSE";
                }
            }
            return "TRUE";
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return "TRUE";
        }
    } 

    public void update_metadata(String[] arr, String user,String DB, String TB,int no_of_attr)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);

            MetaDataInfo obj = new MetaDataInfo();
            obj.insertTBInfo(arr, DB, TB,no_of_attr);

            String [] ans = obj.getRecordDBInfo(arr, user, DB);

            if(ans[0].equals(user) && ans[1].equals(DB))
            {

                stm.execute(useit);
                String query = "update  dbinfo set no_of_Tb = no_of_Tb+1 " ;
                stm.execute(query);
                
            }

            else
            {
                stm.execute(useit);
                String query = "insert into dbinfo values ('"+user+"' ,'"+DB+"' , 1 );";
                stm.execute(query);
            }

            int total_DB = obj.databaseCount(arr, user);
            String str = obj.getRecordClientDBInfo(arr, user, total_DB);

            if(str=="FALSE")
            {
                System.out.println("RECORD IS CORRECT");
            }
            else
            {
                String query = "update clientdbinfo  set no_of_DB = no_of_DB+1 ;" ;
                stm.execute(query);
                System.out.println("RECORD FROM CLIENTDBINFO UPDATED SUCESSFULLY ....");
            }
        }
        catch(Exception e)
        {

        }
    }

    public String[] getDB(String[] arr,String user)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);

            String query = "select DBName from dbinfo where username = '"+user+"' ;";
            ResultSet rs = stm.executeQuery(query);

            String[] result = new String[4];
            int i=0;

            while(rs.next())
            {
                result[i]=rs.getString("DBName");
                i++;
            }

            for(int j=i;j<result.length;j++)
            {
                result[j]="X";
            }

            return result;

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            String[] fal = {""};
            return fal;
        }
    }

    public String[] getTableNames(String[] arr,String user,String DBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String useit = "use client;";
            stm.execute(useit);

            String query = "select distinct tbname  from tableNames where username = '"+user+"' and dbname = '"+DBName+"' ;";
            ResultSet rs = stm.executeQuery(query);

            MetaDataInfo obj = new MetaDataInfo();

            String[] result = new String[obj.tableCount(arr, DBName)];

            int i=0;
            
            while(rs.next())
            {
                result[i]=rs.getString("tbname");
                i++;
            }

            return result;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            String[] fal ={""};
            return fal;
        }
    }

    public String getFirstAttribute(String[] arr,String DBName,String TBName)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useit = "use "+DBName+" ;";
            stm.execute(useit); 
            
            String query = "select * from "+TBName+" ;";

            ResultSet rs = stm.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            String columnLabel = rsmd.getColumnLabel(1);

            System.out.println();
            System.out.println("First Column from "+TBName+" is : "+columnLabel);
            System.out.println();

            return columnLabel;
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "FALSE";
        }
        
    }
}