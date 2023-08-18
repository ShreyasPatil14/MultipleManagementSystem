package databasQuery;
import java.sql.*;

import metedata.MetaDataInfo;

public class SQlQuery
{
    MetaDataInfo meta_data_obj = new MetaDataInfo();
    public void createDB(String[] arr,String DBName)
    {
        try
        {
            
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();


            String create_Database = "create database "+DBName+";";
            stm.execute(create_Database);
            System.out.println();

            System.out.println("DATABASE "+DBName+" CREATED SUCESSFULLY");
            System.out.println();

        }
        catch(Exception e)
        {
            System.out.println("Database Already Exists");
        }
    }
    
    public String createTB(String[] arr, String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username=arr[1];
            String password= arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            int size=DBInfo.length;
            System.out.println("SIZE OF REQUEST ARRAY IS " + size);

            String user = DBInfo[1];
            String DBName =DBInfo[2];
            String TBName =DBInfo[3];
            String[] attributes=new String[size-5];

            System.out.println("SIZE OF ATTR "+attributes.length);

            int j=0;

            for(int i=4; i<DBInfo.length-1 ; i++)
            {
                attributes[j]= DBInfo[i];
                j++;
            }

            System.out.println("ATTRIBUTE EXTRACTED ");

            String query2 = "use "+DBName+" ;";
            stm.execute(query2);

            String query3 = "create table "+TBName+" ( ";
            String dType = " varchar(80) ";
            String space = " ";
            String comma = " , ";

            for(int i=0;i<attributes.length;i++)
            {
                if(i==attributes.length-1)
                {
                    query3 = query3+attributes[i]+ space + dType + " ) ";
                    break;
                }
                else
                {
                    query3 = query3 + attributes[i] + space + dType + comma ;
                }
                System.out.println(attributes[i]);
            }

            System.out.println();
            System.out.println(query3);
            System.out.println();

            stm.execute(query3);

            System.out.println("TABLE CREATED SUCEESFULLY");

            String useit = "use client ;";
            stm.execute(useit);

            String query = " insert into tableNames values ('"+user+"' , '"+DBName+"' , '"+TBName+"') ;";
            stm.execute(query);

            return "TRUE";

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "FALSE";
        }
    }

    public void insertRecord(String[] arr, String[] DBinfo)
    {
        try
        {
            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String DBName = DBinfo[2];
            String TBName = DBinfo[3];
            int size = DBinfo.length;
            String[] attributes=new String[size-5];
            int j=0;

            for(int i=4;i<DBinfo.length-1;i++)
            {
                attributes[j]=DBinfo[i];
                j++;
            }

            String useit="use "+DBName+" ;";
            stm.execute(useit);

            String query = " insert into "+TBName+" values (";
            String str="";
            String space =" ";
            String comma = " , ";

            for(int i=0;i<attributes.length;i++)
            {
                if(i==attributes.length-1)
                {
                    str=str+"'"+attributes[i]+"'"+space;
                }
                else
                {
                    str=str+"'"+attributes[i]+"'"+space+comma;
                }
            }
            query=query+str+" );";
            stm.execute(query);

            System.out.println("RECORD INSERTED SUCESSFULLY!");

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


    }

    public void dropDB(String[] arr,String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

           
            String DBName = DBInfo[2];

            String useit=" use "+DBName+" ;";
            stm.execute(useit);

            String useit2=" use client ;";
            stm.execute(useit2);

            String query2 = "delete from tbinfo where DBName = '"+DBName+"' ;";
            stm.execute(query2);
            System.out.println("DATABASE "+DBName+" DELETED FROM TBINFO TABLE .....");

            String query3 = "delete from dbinfo where DBName = '"+DBName+"' ;";
            stm.execute(query3);
            System.out.println("DATABASE "+DBName+" DELETED FROM DBINFO TABLE .....");
            

            String query = "drop database "+DBName+" ;";
            stm.execute(query);

            System.out.println("DATABASE "+DBName+" DELETED SUCESSFULLY");
            
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


    }

    public String deleteRecord(String[] arr,String[] DBInfo)
    {   
        try
        {
            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String DBName = DBInfo[2];
            String TBName = DBInfo[3];
            String attrValue = DBInfo[4];

            String useit= "use "+DBName+" ;";
            stm.execute(useit);

            String select = " select * from "+TBName+";";
            ResultSet rs = stm.executeQuery(select);

            ResultSetMetaData rsmd = rs.getMetaData();

            String attrName="";

            while(rs.next())
            {
                attrName = rsmd.getColumnLabel(1);
            }

            String dltRecord = "delete from "+TBName+" where "+attrName+" = '"+attrValue+"' ;";
            stm.execute(dltRecord);

            System.out.println("RECORD DELETED FROM "+TBName+" SUCESSFUILLY");
            return "TRUE";

        }
        catch(Exception e)
        {
            System.out.println("CAN'T DELETE RECORD");
            System.out.println(e.getMessage());
            return "FALSE";
        }
    }

    public Object[][] displayRecords(String[] arr, String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String DBName = DBInfo[2];
            String TBName = DBInfo[3];
            
            String useit = "use "+DBName+";";
            stm.execute(useit);

            String firstcolumn = meta_data_obj.getFirstAttribute(arr, DBName,TBName);
            String query = " select * from "+TBName+" order by "+firstcolumn+" ;";

            PreparedStatement stmt = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();


            rs.first();
            int rowcount = 0;
            do 
            {
                rowcount++;
            } while (rs.next());

            rs.first();
            int rowindex = 0;

            Object array_2D[][] = new Object[rowcount][];

            do 
            {
                array_2D[rowindex] = new Object[numberOfColumns];

                for (int i = 0; i < numberOfColumns; i++)
                {
                    array_2D[rowindex][i] = rs.getObject(i + 1);
                    
                }

                rowindex++;

            } while (rs.next());

            return array_2D;
        }

        catch(Exception e)
        {
            System.out.println("ERROR OCCURED");
            System.out.println(e.getMessage());
            String[][] error = {{"","",""},{""}};
            return error; 
        }
    }

    

    public void updateRecord(String[] arr,String[] DBInfo)
    {
        try
        {
            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();

            String useit = "use "+DBInfo[2]+" ;";
            stm.execute(useit);

            String retriveData = "select * from "+DBInfo[3]+" ; ";
            ResultSet rs = stm.executeQuery(retriveData);

            ResultSetMetaData mtdt = rs.getMetaData();
            int columnCount = mtdt.getColumnCount();

            System.out.println("Column Count is : " + columnCount);

            String attrName = mtdt.getColumnLabel(1);
            System.out.println("Attribute is : " + attrName);

            String dltQuery = "delete from "+DBInfo[3]+" where "+attrName+" = '"+DBInfo[4]+"' ; ";
            stm.execute(dltQuery);

            System.out.println("Record Deletes Successfully");

            int attr_count = mtdt.getColumnCount();

            String[] attributes=new String[attr_count];
            int j=0;

            for(int i=5;i<DBInfo.length-1;i++)
            {
                attributes[j]=DBInfo[i];
                j++;
            }

            String addRecord = " insert into "+DBInfo[3]+" values (";
            String space =" ";
            String comma = " , ";

            System.out.println("Attribute length : "+attributes.length);

            for(int i=0; i<attributes.length; i++)
            {
                System.out.println("Attr : "+attributes[i]);
            }

            for(int i=0;i<=attributes.length-1;i++)
            {
                if(i==attributes.length-1)
                {
                    addRecord+="'"+attributes[i]+"'"+space;
                }
                else
                {
                    addRecord+="'"+attributes[i]+"'"+space+comma;
                }
            }
            addRecord=addRecord+" );";

            System.out.println("AddRecord : "+addRecord);

            System.out.println();
            System.out.println();
            System.out.println("UPDATE : "+addRecord);
            System.out.println();
            System.out.println();

            System.out.println("query to insert is  : "+addRecord);
            stm.execute(addRecord);

            System.out.println("RECORD INSERTED AND UPDATED SUCESSFULLY....");


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String[] getAttributes(String[] arr,String[] DBInfo)
    {
        try
        {

            String url = arr[0];
            String username = arr[1];
            String password = arr[2];

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            
            String DBName = DBInfo[2];
            String TBName = DBInfo[3];

            MetaDataInfo obj = new MetaDataInfo();
            int x = obj.getAttributeCount(arr, DBName, TBName);

            System.out.println("NO : " + x);

            String[] attr_list = new String[x];

            String useit = "use "+DBName+";";
            stm.execute(useit);

            String query = "select * from "+TBName+";";             
            ResultSet rs = stm.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();

            int j=0;
            
            for(int i=1;i<=rsmd.getColumnCount();i++)
            {
                attr_list[j]=rsmd.getColumnName(i);
                // System.out.println(attr_list[j]);
                j++;
            }

            return attr_list;
        }

        catch(Exception e)
        {
            String[] empty = {"","","","",""};
            System.out.println(e.getMessage());
            return empty;

        }
    }
}