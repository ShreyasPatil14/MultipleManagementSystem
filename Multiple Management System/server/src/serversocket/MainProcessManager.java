package serversocket;

import java.sql.*;
import validation.*;
import metedata.*;
import databasQuery.*;

public class MainProcessManager {
    public String[] getProcess(String[] str1) {
        Object[][] table = null;
        try {
            // path to database:
            String url = "jdbc:mysql://localhost:3306/";
            // connection info.:
            String username = "root";
            String password = "Shreyas@50";

            // creating connection:
            Connection conn = DriverManager.getConnection(url, username, password);

            // creating statements:(Query)
            Statement stm = conn.createStatement();

            String[] arr = new String[3];
            arr[0] = url;
            arr[1] = username;
            arr[2] = password;

            // object of user defined classes
            SQlQuery sql_obj = new SQlQuery();
            UserValidation user_val_object = new UserValidation();
            MetaDataInfo meta_data_object = new MetaDataInfo();

            String[] ret_true = { "TRUE" };
            String[] ret_false = { "FALSE" };

            if (str1[0].equals("signin")) {
                System.out.println();
                System.out.println("REQUEST: signin");
                System.out.println();
                String[] sign_in = user_val_object.signIn_validation(arr, str1);
                System.out.println("Sign in: " + sign_in[0]);
                System.out.println();
                return sign_in;

            }

            if (str1[0].equals("signup")) {
                System.out.println();
                System.out.println("REQUEST: signup");
                System.out.println();
                for (int i = 0; i < str1.length; i++) {
                    System.out.println(" " + str1[i]);
                }

                String[] sign_up = user_val_object.signup_validation(arr, str1);

                if (sign_up[0].equals("FALSE")) {
                    System.out.println();
                    System.out.println(">>>USER ALREADY EXIST");
                    return ret_false;
                } else {
                    meta_data_object.insertAccounts(arr, str1[1], str1[2]);
                    System.out.println();
                    System.out.println(">>>USER " + str1[1] + " ADDED");
                }
                System.out.println();

                return sign_up;
            }

            if (str1[0].equals("CREATE_DATABASE")) {
                System.out.println();
                System.out.println("REQUEST: CREATE_DATABASE");
                String user = str1[1];
                String DBName = str1[2];
                int tbCount2 = Integer.parseInt(str1[3]);

                meta_data_object.insertDBInfo(arr, user, DBName, tbCount2);
                sql_obj.createDB(arr, DBName);
                return ret_true;

            }

            if (str1[0].equals("CREATE_TABLE")) {
                System.out.println();
                System.out.println("REQUEST: CREATE_TABLE");
                for (int i = 0; i < str1.length; i++) {
                    System.out.println(str1[i]);
                }
                String res = sql_obj.createTB(arr, str1);
                String[] retValue = new String[1];
                retValue[0] = res;
                return retValue;
            }

            if (str1[0].equals("DELETE_DB")) {
                System.out.println();
                System.out.println("REQUEST: DELETE_DB");
                System.out.println();
                sql_obj.dropDB(arr, str1);
                return ret_true;

            }

            if (str1[0].equals("UPDATE_RECORD")) {
                System.out.println();
                System.out.println("REQUEST: UPDATE_RECORD");
                System.out.println();
                sql_obj.updateRecord(arr, str1);
                return ret_true;
            }

            
            if (str1[0].equals("GET_NEXT_RECORD")) {
                System.out.println();
                System.out.println("REQUEST: GET_NEXT_RECORD");
                System.out.println();
                String[] record;
                String count_str = str1[4];
                int count = Integer.parseInt(count_str);

                if (table == null) {
                    int cols = meta_data_object.getAttributeCount(arr, str1[2], str1[3]);
                    table = new Object[meta_data_object.getRecordCount(arr, str1[2], str1[3])][cols];

                    table = sql_obj.displayRecords(arr, str1);

                }

                record = new String[table[count].length];

                for (int i = 0; i < record.length; i++) {
                    record[i] = table[count][i].toString();
                }

                return record;

            }

            if (str1[0].equals("GET_ATTRIBUTES")) {
                String[] attr = sql_obj.getAttributes(arr, str1);
                System.out.println("REQUEST: GET_ATTRIBUTES");
                for (int i = 0; i < attr.length; i++) {
                    System.out.println(attr[i]);
                }
                return attr;
            }

            if (str1[0].equals("GET_ATTRI_COUNTS")) {
                Integer attr_count = meta_data_object.getAttributeCount(arr, str1[2], str1[3]);
                System.out.println("REQUEST: GET_ATTRI_COUNTS");
                System.out.println("COUNT OF ATTRIBUTES: " + attr_count);

                String cnt = attr_count.toString();
                String[] at_count = new String[1];
                at_count[0] = cnt;
                return at_count;
            }

            if (str1[0].equals("GET_RECORD_COUNTS")) {
                Integer record_count = meta_data_object.getRecordCount(arr, str1[2], str1[3]);
                System.out.println("REQUEST: GET_RECORD_COUNTS");
                System.out.println("RECORD COUNT: " + record_count);

                String cnt = record_count.toString();
                String[] at_count = new String[1];
                at_count[0] = cnt;

                return at_count;

            }

            if (str1[0].equals("ADD_RECORD")) {
                System.out.println("REQUEST: ADD_RECORD");
                sql_obj.insertRecord(arr, str1);
                System.out.println();
                return ret_true;
            }

            
            if (str1[0].equals("GET_DBS")) {
                System.out.println("REQUEST: GET_DBS");

                String user = str1[1];

                String[] result = meta_data_object.getDB(arr, user);

                for (int i = 0; i < result.length; i++) {
                    System.out.println(result[i] + "\t");
                }

                return result;

            }
            if (str1[0].equals("GET_TABLE_COUNT")) {
                String DBName = str1[2];
                System.out.println("REQUEST: GET_TABLE_COUNT");
                Integer tbCount = meta_data_object.tableCount(arr, DBName);
                String[] tbc = new String[1];
                tbc[0] = tbCount.toString();

                return tbc;
            }

            if (str1[0].equals("GET_TABLES")) {
                String user = str1[1];
                String DBName = str1[2];
                String[] result = meta_data_object.getTableNames(arr, user, DBName);
                for (int i = 0; i < result.length; i++) {
                    System.out.println("    " + result[i] + "  ");
                }

                return result;
            }

            if (str1[0].equals("DELETE_RECORD")) {
                System.out.println("REQUEST: DELETE_RECORD");
                String retValue = sql_obj.deleteRecord(arr, str1);
                String[] retArr = new String[1];
                retArr[0] = retValue;
                return retArr;
            }

            if (str1[0].equals("ABORT_DB_CREATION")) {
                String DBName = str1[2];

                String query = "drop database " + DBName + " ;";
                stm.execute(query);
                System.out.println("REQUEST: ABORT_DB_CREATION" + DBName + " ...");
            }

            conn.close();

            return ret_false;

        } catch (Exception e) {

            System.out.println("ERROR ! [ connection failed ]");
            System.out.println(e.getMessage());
            String[] fal = { "FALSE" };
            return fal;
        }
    }

}