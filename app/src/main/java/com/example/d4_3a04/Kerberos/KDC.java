package com.example.d4_3a04.Kerberos;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class KDC {
    private static Connection dbConnection;

    private static String keyTGS = "some really internal secure key";

    public static void main(String[] args){
        try {
            // Initialization
            dbConnection = DriverManager.getConnection("jdbc:mysql://roundhouse.proxy.rlwy.net:15416/railway?", "root", "vXkxjSryhPjEvhRLZoJSyIDZgJzLXJUU");
            ServerSocket socket = new ServerSocket(55555);

            while(true){
                Socket client = socket.accept();

                // Once a connection is received, start a thread to authenticate the connection
                KerberosAuthenticator kerb = new KerberosAuthenticator(client);
                Thread kerbThread = new Thread(kerb);
                kerbThread.start();
            }
        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Runnable class to handle all requests directed at the KDC. Handles both
     * authentication and authorization requsts (acting as both the Authorization
     * Service and the Ticket-Granting Service).
     */
    static class KerberosAuthenticator implements Runnable{
        Socket client;

        public KerberosAuthenticator(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try {
                InputStreamReader input = new InputStreamReader(client.getInputStream());

                if(input.read() == 0){ // Authentication step/service
                    authenticationService(input);
                } else{ // Ticket Granting step/service
                    ticketGrantingService(input);
                }

            } catch (IOException | SQLException e){
                e.printStackTrace();
            }
        }

        /**
         * Function for performing the authentication service. Acts as an Authentication
         * Server to authenticate whether the user is allowed to access the resources.
         */
        private void authenticationService(InputStreamReader input) throws SQLException, IOException {
            OutputStreamWriter output = new OutputStreamWriter(client.getOutputStream());

            // Step 1: Get requesting user id
            String uid= "";
            while(true){
                char c = (char) input.read();
                if(c == '|') // temporary EOF indicator
                    break;
                uid += c;
            }

            // Step 2: Verify user is in database.
            String cmd = "SELECT password FROM users WHERE email=" + uid;
            ResultSet result = dbConnection.createStatement().executeQuery(cmd);
            if(!result.next()) { // User not in DB
                output.write("F"); // Indicates failure
                return;
            }

            // If they are, generate the client-TGS key and encrypt it using their (hashed/encrypted) password.
            String clientTGSKey = "pretendItsRandom";
            String encPassword = result.getString(1);
            String encSessionKey = encrypt(clientTGSKey, encPassword);

            // Also create the Ticket Granting Ticket, encrypted using a server-side Ticket Granting Service Key
            // The ticket is valid for 1 day. Client address is not included.
            @SuppressLint({"NewApi", "LocalSuppress"}) String ticketGrantingTicket  = clientTGSKey + " " + uid + " " + OffsetDateTime.now().plusDays(1);
            // Error is because IDE assumes this will be ran on android. This KDC in practical application will be ran on a secure server.
            String encTGT = encrypt(ticketGrantingTicket, keyTGS);

            // Step 3: Send messages, decrypt and process using the new key on the client side.
            output.write("S" + encSessionKey + "|" + encTGT + "|");
        }

        private void ticketGrantingService(InputStreamReader input) throws IOException {
            OutputStreamWriter output = new OutputStreamWriter(client.getOutputStream());

            // Step 1: Receive encrypted TGT and Authenticator message
            String TGT = "";
            String authoReq = "";
            while(true){
                char c = (char) input.read();
                if(c == '|')
                    break;
                TGT += c;
            }
            while(true){
                char c = (char) input.read();
                if(c == '|')
                    break;
                authoReq += c;
            }

            // Step 2: Decrypt the messages
            // TGT: decrypt using TGS key
            String decTGT = decrypt(TGT, keyTGS);
            String[] tokenizedTGT = decTGT.split(" ");

            // Authenticator message: decrypt using session key from TGT
            String decAuthoReq = decrypt(authoReq, tokenizedTGT[0]);
            String[] tokenizedDecAuthoReq = decAuthoReq.split(" ");

            if(tokenizedTGT[1] != tokenizedDecAuthoReq[0]){  // Auth fail
                output.write("F");
                return;
            }

            // Step 3: Send session key and service ticket
            String clientServiceKey = "pretendItsRandom2";
            String serviceTicket = clientServiceKey + " " + tokenizedTGT[1] + " " + tokenizedTGT[2];
            String encSessionKey = encrypt(clientServiceKey, tokenizedTGT[0]);

            output.write("S" + serviceTicket + "|" + encSessionKey + "|");
        }
    }
    // Encrypt and Decrypt code is obtained from open source repository: https://github.com/saeed74/Android-DES-Encryption/tree/master?tab=Apache-2.0-1-ov-file#readme
    // Repository has apache-2.0 licence. So its fine to use their code.
    // Uses DNS protocol.

    private static String decrypt(String value, String key){
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

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // Decrypt the text
            byte[] textDecrypted = cipher.doFinal(bytesDecoded);

            result = new String(textDecrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return "Decrypt Error";
        }
        return result;
    }

    private static String encrypt(String value, String key) {

        String crypted = "";

        try {

            byte[] cleartext = value.getBytes("UTF-8");

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/ZeroBytePadding");

            // Initialize the cipher for decryption
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            crypted = Base64.encodeToString(cipher.doFinal(cleartext), Base64.DEFAULT);


        } catch (Exception e) {
            e.printStackTrace();
            return "Encrypt Error";
        }
        //return "code==" + crypted;
        return crypted;
    }
}
