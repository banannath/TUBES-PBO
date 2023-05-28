/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hanna Nathania
 */
public class ConnectionManager {
    private String user = "root"; //memasukkan user daatabase
    private String password = ""; //memasukkan password dari user database
    private String url = "jdbc:mysql://localhost:3306/mamaniabakery?zeroDateTimeBehavior=CONVERT_TO_NULL"; //memasukkan link database
    private Connection c; //inisialisasi variabel c sebagai Connection
    
    public Connection getConnection() {        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //mencoba membuat driver jdbc untuk mysql
            try {
                c = DriverManager.getConnection(url, user, password); //mencoba untuk mendapatkan koneksi ke database dengan parameter user, password, dan url
            } catch (SQLException ex) { //error handling bila terjadi kegagalan saat connect ke datatbase
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex); //logger berfungsi untuk mencatat kesalahan yang terjadi
            }
        } catch (ClassNotFoundException ex) { //error handling bila driver jdbc tidak dapat ditemukan
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);//logger berfungsi untuk mencatat kesalahan yang terjadi
        }        
        if (c != null) { //memeriksa kondisi apakah database connect atau tidak
            System.out.println("Database connected");
        } else {
            System.out.println("Database not connected");
        }
        return c; //mengembalikan nilai c yang sudah didapat agar dapat melakukan operasi pada database
    }
    
    public void closeConnection(){       
        try {
            c.close(); //menutup koneksi ke database
        } catch (SQLException ex) { //error handling jika terjadi kesalahan saat mencoba untuk menutup koneksi ke database
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex); //logger berfungsi untuk mencatat kesalahan yang terjadi
        }
    }
}
