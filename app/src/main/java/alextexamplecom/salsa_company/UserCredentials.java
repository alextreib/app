package alextexamplecom.salsa_company;

//Class for UserCredential handling (email + pwd)

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static android.R.attr.data;

public class UserCredentials {
    //unique identifier is the m_email -> getting through login
    private String m_email;
    private String m_pwd;

    private Cipher m_cipher;
    private KeyGenerator m_keygen;
    private SecretKey m_key;

    private static final String CredentialsFileName = "Credentials.salsa";

    public UserCredentials() {
        this.m_email = "";
        this.m_pwd = "";
    }

    public UserCredentials(String firstName, String lastName) {
        this.m_email = firstName;
        this.m_pwd = lastName;
    }

    public boolean IsEmpty() {
        return "".equals(this.m_email) || ("".equals(this.m_pwd));
    }

    public boolean WriteCredentialsToFile( File parentPath) {
        File file = new File(parentPath, CredentialsFileName);

        try {
            if (m_cipher == null) {
                createCipher();
            }
            m_cipher.init(Cipher.ENCRYPT_MODE, m_key);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            CipherOutputStream cos = new CipherOutputStream(bos, m_cipher);
            ObjectOutputStream oos = new ObjectOutputStream(cos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserCredentials ReadCredi(File parentPath) {
        //returns an empty UserCred if no r
        File file = new File(parentPath, CredentialsFileName);

        if (file.exists()) {
            try {
                if (m_cipher == null) {
                    createCipher();
                }
                m_cipher.init(Cipher.ENCRYPT_MODE, m_key);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                CipherInputStream cis = new CipherInputStream(bis, m_cipher);
                ObjectInputStream ois = new ObjectInputStream(cis);
                UserCredentials userCred = (UserCredentials) ois.readObject();
                ois.close();
                return userCred;
            } catch (Exception e) {
                e.printStackTrace();
                return new UserCredentials();
            }
        } else {
            return new UserCredentials();
        }
    }

    private void createCipher() throws Exception {
        m_cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        m_keygen = KeyGenerator.getInstance("AES");
        m_key = m_keygen.generateKey();
    }
}