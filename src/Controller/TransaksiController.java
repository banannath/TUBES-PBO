/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Connection.ConnectionManager;
import Model.Barang;
import Model.Keranjang;
import Model.Transaksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hanna Nathania
 */
public class TransaksiController {
    public int createTransaksi(Transaksi transaksi){
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        LocalDateTime waktutransaksi = LocalDateTime.now();
        try (Connection con = conman.getConnection()) { //mencoba untuk melakukan koneksi ke database dengan memanggil method getConnection()
            //query sql untuk memasukkan barang baru
            String query = "INSERT INTO transaksi(id_barang, username, kategori, qty, harga, waktu, totalharga) VALUES ('" 
                    + transaksi.getId_barang() + "', '" 
                    + transaksi.getUsername() + "', '" 
                    + transaksi.getKategoribarang()+ "', '" 
                    + transaksi.getQty()+ "', '" 
                    + transaksi.getHargabarang()+ "', '" 
                    + waktutransaksi + "', '" 
                    + transaksi.getTotalharga() + "')"; 
            try (Statement stm = con.createStatement()) { 
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex){ //error handling
            Logger.getLogger(TransaksiController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang.
    }
    
    public List<Transaksi> getAllTransaksi() {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        List<Transaksi> transaksiList = new ArrayList<>(); //membuat list barang
        try (Connection con = conman.getConnection(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
             Statement stmt = con.createStatement(); //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
             ResultSet rs = stmt.executeQuery("SELECT * FROM transaksi")) { //melakukan eksekusi query sql

            while (rs.next()) { //mengambil data dari ResultSet dan memasukkannya ke dalam objek Barang yang kemudian ditambahkan ke dalam list barangList.
                Transaksi transaksi = new Transaksi(); //membuat object barang untuk menambahkan barang baru pada list
                transaksi.setId_transaksi(rs.getInt("id"));
                transaksi.setId_barang(rs.getString("id_barang"));
                transaksi.setUsername(rs.getString("username")); //mengambil data id_barang dari ResultSet dan mengesetnya ke dalam objek Barang yang telah dibuat sebelumnya.
                transaksi.setKategoribarang(rs.getString("kategori")); //mengambil data nama dari ResultSet dan mengesetnya ke dalam objek Barang yang telah dibuat sebelumnya.
                transaksi.setHargabarang(rs.getInt("harga"));
                transaksi.setQty(rs.getInt("qty"));
                transaksi.setWaktutransaksi(rs.getTimestamp("waktu").toLocalDateTime());
                transaksi.setTotalharga(rs.getInt("totalharga"));
                transaksiList.add(transaksi); //menambahkan objek Barang yang telah diisi dengan data dari ResultSet ke dalam list barangList.
            }

        } catch (SQLException ex) { //error handling
            System.out.println("Error mengambil data barang: " + ex.getMessage()); //pesan error 
        } finally {
            conman.closeConnection(); //memutus koneksi database
        }
        return transaksiList; //mengembalikan list barangList yang telah diisi dengan data barang dari database
    }
    
}
