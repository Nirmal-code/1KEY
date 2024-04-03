package com.example.d4_3a04.database;

import android.content.Context;
import android.os.StrictMode;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d4_3a04.SingleChatProvider.SingleChatManager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Cryptosystem extends AppCompatActivity {

    private static DatabaseHelper dbh;

    static Connection mysqlConnection;

    private static String key_val="JustAKey";

    public static void startDB(Context context){
        if (mysqlConnection == null){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try{
                Class.forName("com.mysql.jdbc.Driver");
                mysqlConnection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/testdb?useUnicode=true&characterEncoding=UTF-8", "root", "NemoC#1029");
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
    }

    public static void disconnectDB(){
        try{
            mysqlConnection.close();
            mysqlConnection=null;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void updateEntry(SingleChatManager provider, String employee_id, String other_employee){
        try {

            String encrypted_employee_id = encrypt(employee_id);
            String encrypted_other_employee = encrypt(other_employee);

            Statement statement = mysqlConnection.createStatement();
            String serialized = SingleChatManager.serializeToJson(provider);
            String encrypted_provider = encrypt(serialized);


            String COMMAND = String.format("INSERT INTO providers (this_employee, other_employee, provider) VALUES (\"%s\", \"%s\", \"%s\") ON DUPLICATE KEY UPDATE provider=\"%s\"", encrypted_employee_id, encrypted_other_employee, encrypted_provider, encrypted_provider);

            statement.execute(COMMAND);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public static List<String> getNames(String this_employee, String other_employee){
//        mysqlConnection = new Connection("localhost", "root", "NemoC#1029", 3306, "PROVIDERS", new IConnectionInterface());
//
        List<String> output = new ArrayList<>();
        try {
            // Ensure mysqlConnection is properly initialized and connected
            Statement statement = mysqlConnection.createStatement();

            String encrypted_this_employee = encrypt(this_employee);
            String encrypted_other_employee = encrypt(other_employee);


            String COMMAND = String.format("SELECT provider " +
                    "FROM providers " +
                    "WHERE this_employee=\"%s\" AND other_employee=\"%s\";", encrypted_this_employee, encrypted_other_employee);

            ResultSet result = statement.executeQuery(COMMAND);

            // Move the cursor to the first row
            if (result.next()) {
                // Retrieve the value from the first column of the current row
                String encrypted_provider = result.getString(1);
                String provider = decrypt(encrypted_provider);
                output.add(provider);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;

//        return dbh.getNames(this_employee, other_employee);
    }

    // Encrypt and Decrypt code is obtained from open source repository: https://github.com/saeed74/Android-DES-Encryption/tree/master?tab=Apache-2.0-1-ov-file#readme
    // Repository has apache-2.0 licence. So its fine to use their code.
    // Uses DNS protocol.

    private static String decrypt(String value){
        String coded;
        if(value.startsWith("code==")){
            coded = value.substring(6,value.length()).trim();
        }else{
            coded = value.trim();
        }

        String result = null;
        try {
            // Decoding base64
            byte[] bytesDecoded = Base64.decode(coded.getBytes("UTF-8"),Base64.DEFAULT);

            SecretKeySpec key = new SecretKeySpec(key_val.getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Decrypt the text
            byte[] textDecrypted = cipher.doFinal(bytesDecoded);

            result = new String(textDecrypted);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (BadPaddingException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        return result;
    }

    private static String encrypt(String value) {

        String crypted = "";

        try {

            byte[] cleartext = value.getBytes("UTF-8");

            SecretKeySpec key = new SecretKeySpec(key_val.getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.ENCRYPT_MODE, key);

            crypted = Base64.encodeToString(cipher.doFinal(cleartext), Base64.DEFAULT);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Encrypt Error";
        } catch (Exception e) {
            e.printStackTrace();
            return "Encrypt Error";
        }

        //return "code==" + crypted;
        return crypted;
    }

}
