import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import serversocket.*;


public class DatabaseManager     
{
    public static void main(String args[])
    {
        MainProcessManager main_proc = new MainProcessManager();
        
        try
        {
            while (true)
            {
                int port = 8888;

                System.out.println();
                System.out.println("[ server is on ]");
                ServerSocket ss=new ServerSocket(port);

                System.out.println("[ server is waiting ]");
                Socket s = ss.accept(); // recieve client request and create new socket obj. 

                System.out.println("[ client connected ]");
                System.out.println();

                BufferedReader br = new BufferedReader( new InputStreamReader(s.getInputStream()));
                String str = br.readLine();

                // string to string[]
                int count=0;
                for(int i=0;i<str.length();i++)
                {
                    if(str.charAt(i) == ' ')
                    {
                        count++;
                    }
                }

                String word = "";
                String arr[] = new String[count];
                int arrIdx = 0;
                for(int i=0;i<str.length();i++)
                {
                    if(str.charAt(i) == ' ')
                    {
                        arr[arrIdx] = word;
                        arrIdx += 1;
                        word = "";
                        continue;
                    }
                    word = word + str.charAt(i);
                }
                
                
                String[] responce = main_proc.getProcess(arr);
                String result="";
                for(int i=0;i<responce.length;i++)
                {
                    result= result+ responce[i]+" ";
                }

                OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                PrintWriter out = new PrintWriter(osw);
                out.println(result);
                osw.flush();
            
                ss.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }    
}