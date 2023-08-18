package socket;

import java.net.Socket;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class ClientSocket
{
    
    
    public String[] request(String[] sendReq) throws IOException
    {
        
        for(int i=0; i<sendReq.length; i++)
        {
            System.out.print(sendReq[i] + " ");
        }
        System.out.println();

        String ip = "localhost";
        int port = 8888;

        Socket s;

        while(true)
        {
            
            try {
                // creating a socket to connect with the server:
                s  = new Socket(ip, port); // socket consist of unique port no.and server ip address.
                

                // string[] to string:
                String send = "";
                for(int i=0; i<sendReq.length; i++)
                {
                    send = send + sendReq[i] + " ";
                }

                // sending data (as string):
                OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                PrintWriter out = new PrintWriter(osw);
                out.println(send);
                osw.flush();


                // receiving data (as string):
                BufferedReader br = new BufferedReader( new InputStreamReader(s.getInputStream()));
                String received = br.readLine();

                // string to string[]:
                String word = "";
                String response[] = new String[Integer.parseInt(sendReq[sendReq.length-1])]; // recieving response array-size.
                int responseIdx = 0;
                for(int i=0;i<received.length();i++)
                {
                    if(received.charAt(i) == ' ')
                    {
                        response[responseIdx] = word;
                        responseIdx += 1;
                        word = "";
                        continue;
                    }
                    word = word + received.charAt(i);
                }

                // filtering response:
                for(int i=0; i<response.length; i++)
                {
                    if(response[i] == "null")
                    {
                        response[i] = " ";
                    }
                }
                        

                s.close();

                for(int i=0; i<response.length; i++)
                {
                    System.out.print(response[i] + " ");
                }
                System.out.println();System.out.println();

                return response;  

            } catch (Exception e) {
                System.out.println("Re-Connecting...");
            }
        }

        
        
    }  
    
}
