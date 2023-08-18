package authentication_;

import java.io.IOException;

import socket.ClientSocket;
import encryption.Encrypt;

public class Authentication
{
    
    ClientSocket cs = new ClientSocket();
    Encrypt enc = new Encrypt();


    // sign-in validator:
    public boolean aut_signIn(String[] signin) throws IOException
    {

        // code:
        String[] s_response = cs.request(signin);

        if(s_response[0].equals("NO_DATA_FOUND"))
        {
            return false;
        }
        
        String cipherPasswd = signin[2];

        if(!(s_response[0].equals(cipherPasswd)))
        {
            return false;
        }
        
        return true;

    }


    // sign-up validator:
    public boolean aut_signUp(String[] signup) throws IOException
    {

        // code:
        String[] s_response = cs.request(signup);

        if(s_response[0].equals("FALSE"))
        {
            return false;
        }

        return true;
    }
    
}
