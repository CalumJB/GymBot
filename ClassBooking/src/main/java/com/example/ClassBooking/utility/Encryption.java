package com.example.ClassBooking.utility;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

public class Encryption {

    public static String encryptPassword(String password, String seed){
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(seed);
            return encryptor.encrypt(password);
    }

    public static String decryptPassword(String password, String seed){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(seed);
        return encryptor.decrypt(password);
    }

}
