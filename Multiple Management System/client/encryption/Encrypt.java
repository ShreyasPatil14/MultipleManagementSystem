package encryption;

public class Encrypt
{
    
    public String getCipherText(String plaintext)
    {
        
        String cipher = "";

        for(int i=0; i<plaintext.length(); i++)
        {
            cipher += (char)(  97 + ((int)plaintext.charAt(i)%26) ); 
        }
        return cipher;
        
    } 
}
