package metadata;

import java.io.IOException;

import socket.ClientSocket;

public class Metadata
{
    ClientSocket cs = new ClientSocket();


    public String userName = "";
    public String[] dataBases = new String[4]; 

    public String selectedDB = "";
    


    // request for users data bases:
    public void getDataBases() throws IOException
    {
        String[] request = new String[3];
        request[0] = "GET_DBS";
        request[1] = userName;
        request[2] = "4";

        dataBases = cs.request(request); 
    }


    // clear all metadata:
    public void clearMetaData()
    {
        userName = "";
        selectedDB = "";

        
        for(int i=0; i<4; i++)
        {
            dataBases[i] = " ";
        }

    }
}
