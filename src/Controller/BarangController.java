/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Connection.ConnectionManager;
import Model.Barang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Barang;
import Connection.ConnectionManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Hanna Nathania
 */
public class BarangController {
    public int createBarang(Barang barang){
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakukan koneksi ke database dengan memanggil method getConnection()
            //query sql untuk memasukkan barang baru
            String query = "INSERT INTO barang(id_barang, nama, kategori, harga, stok) VALUES ('" + barang.getId_barang() + "', '" + barang.getNama() + "', '" + barang.getKategori()+ "', '" + barang.getHarga()+ "', '" + barang.getStok()+ "')"; //query untuk memasukkan data barang yang baru  
            String sama = "SELECT * FROM barang WHERE id_barang = '"+barang.getId_barang()+"'"; //melihat barang berdasarkan id_barang yanga da
            Statement st = con.createStatement(); //membuat statement untuk eksekusi query
            ResultSet rs = st.executeQuery(sama); //eksekusi query
            if(rs.next() == true){ //kondisi jika ada barang yang sama id_barangya
                hasil = -1;
            }
            try (Statement stm = con.createStatement()) { 
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex){ //error handling
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang.
    }
    
    public List<Barang> getAllBarang() {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        List<Barang> barangList = new ArrayList<>(); //membuat list barang
        try (Connection con = conman.getConnection(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
             Statement stmt = con.createStatement(); //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
             ResultSet rs = stmt.executeQuery("SELECT * FROM barang")) { //melakukan eksekusi query sql

            while (rs.next()) { //mengambil data dari ResultSet dan memasukkannya ke dalam objek Barang yang kemudian ditambahkan ke dalam list barangList.
                Barang barang = new Barang(); //membuat object barang untuk menambahkan barang baru pada list
                barang.setId_barang(rs.getString("id_barang"));
                barang.setNama(rs.getString("nama")); //mengambil data id_barang dari ResultSet dan mengesetnya ke dalam objek Barang yang telah dibuat sebelumnya.
                barang.setKategori(rs.getString("kategori")); //mengambil data nama dari ResultSet dan mengesetnya ke dalam objek Barang yang telah dibuat sebelumnya.
                barang.setHarga(rs.getInt("harga"));
                barang.setStok(rs.getInt("stok"));
                barangList.add(barang); //menambahkan objek Barang yang telah diisi dengan data dari ResultSet ke dalam list barangList.
            }

        } catch (SQLException ex) { //error handling
            System.out.println("Error mengambil data barang: " + ex.getMessage()); //pesan error 
        } finally {
            conman.closeConnection(); //memutus koneksi database
        }
        return barangList; //mengembalikan list barangList yang telah diisi dengan data barang dari database
    }
    
    public int updateBarang(Barang barang) {
        ConnectionManager conman = new ConnectionManager(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
            String query = "UPDATE barang SET nama = '" + barang.getNama() + "', kategori = '" + barang.getKategori() + "', harga = '" + barang.getHarga() + "', stok = '" + barang.getStok() + "' WHERE id_barang = '" + barang.getId_barang() + "'"; //eksekusi query sql untuk update data milik barang berdasarkan id
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) { //error handling
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang
    }
    
    public int deleteBarang(Barang barang) {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
            String query = "DELETE FROM barang WHERE id_barang = '" + barang.getId_barang() + "'"; //eksekusi query sql untuk delete data milik barang berdasarkan id
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang.
    }
    
    public int getStokbyId(String id_barang) {
        ConnectionManager conman = new ConnectionManager();
        int stok = 0;

        try (Connection con = conman.getConnection()) {
            String query = "SELECT stok FROM barang WHERE id_barang = ?";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, id_barang);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        stok = resultSet.getInt("stok");
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conman.closeConnection();
        }

        return stok;
    }
    
    public int updateStok(Barang barang) {
        ConnectionManager conman = new ConnectionManager(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
            String query = "UPDATE barang SET stok = '" + barang.getStok() + "' WHERE id_barang = '" + barang.getId_barang() + "'"; //eksekusi query sql untuk update data milik barang berdasarkan id
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) { //error handling
            Logger.getLogger(BarangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang
    }
        
}
