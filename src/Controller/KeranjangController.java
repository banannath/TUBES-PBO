/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Connection.ConnectionManager;
import Model.Keranjang;
import Model.Keranjang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KeranjangController {
    public int createKeranjang(Keranjang keranjang){
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakukan koneksi ke database dengan memanggil method getConnection()
            //query sql untuk memasukkan keranjang baru
            String query = "INSERT INTO keranjang(id_barang, username, namabarang, kategoribarang, hargabarang, qty, total) VALUES ('" + keranjang.getId_barang() + "', '" + keranjang.getUsername() + "', '" + keranjang.getNamabarang()+ "', '" + keranjang.getKategoribarang()+ "', '" + keranjang.getHargabarang()+ "', '" + keranjang.getQty()+ "', '" + keranjang.getTotalharga()+ "')"; //query untuk memasukkan data keranjang yang baru  
            String sama = "SELECT * FROM keranjang WHERE id_barang = '"+keranjang.getId_barang()+"'"; //melihat keranjang berdasarkan id_keranjang yanga da
            Statement st = con.createStatement(); //membuat statement untuk eksekusi query
            ResultSet rs = st.executeQuery(sama); //eksekusi query
            if(rs.next() == true){ //kondisi jika ada keranjang yang sama id_keranjangya
                hasil = -1;
            }
            try (Statement stm = con.createStatement()) { 
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex){ //error handling
            Logger.getLogger(KeranjangController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data keranjang.
    }
    
    public List<Keranjang> getAllKeranjang() {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        List<Keranjang> keranjangList = new ArrayList<>(); //membuat list keranjang
        try (Connection con = conman.getConnection(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
             Statement stmt = con.createStatement(); //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
             ResultSet rs = stmt.executeQuery("SELECT * FROM keranjang")) { //melakukan eksekusi query sql

            while (rs.next()) { //mengambil data dari ResultSet dan memasukkannya ke dalam objek Keranjang yang kemudian ditambahkan ke dalam list keranjangList.
                Keranjang keranjang = new Keranjang(); //membuat object keranjang untuk menambahkan keranjang baru pada list
                keranjang.setId_barang(rs.getString("id_barang"));
                keranjang.setUsername(rs.getString("username"));
                keranjang.setNamabarang(rs.getString("namabarang"));
                keranjang.setKategoribarang(rs.getString("kategoribarang"));
                keranjang.setHargabarang(rs.getInt("hargabarang"));
                keranjang.setQty(rs.getInt("qty"));
                keranjang.setTotalharga(rs.getInt("total"));
                keranjangList.add(keranjang); //menambahkan objek Keranjang yang telah diisi dengan data dari ResultSet ke dalam list barangList.
            }

        } catch (SQLException ex) { //error handling
            System.out.println("Error mengambil data keranjang: " + ex.getMessage()); //pesan error 
        } finally {
            conman.closeConnection(); //memutus koneksi database
        }
        return keranjangList; //mengembalikan list keranjangList yang telah diisi dengan data barang dari database
    }
    
    public Keranjang getId(String id_barang){
       ConnectionManager conman = new ConnectionManager(); //membuat objek koneksi ke databse
       Connection con = conman.getConnection(); //koneksi ke database
       Keranjang krg = null; //inisialisasi objek mahasiswa sebagai null
       String query = "SELECT * FROM keranjang WHERE id_barang = " + id_barang; //mengambil data mahasiswa dari database
       try {
           Statement stm = con.createStatement(); //membuat statement untuk eksekusi query
           ResultSet rs = stm.executeQuery(query); //eksekusi query
           if (rs.next()){
               String id_brg, usernamepembeli, namabarang, kategoribarang;
               int hargabarang, qty, total;
               
               id_brg = rs.getString("id_barang");
               usernamepembeli = rs.getString("username");
               namabarang = rs.getString("namabarang");
               kategoribarang = rs.getString("kategoribarang");
               hargabarang = rs.getInt("hargabarang");
               qty = rs.getInt("qty");
               total = rs.getInt("total");
               
               krg = new Keranjang(id_brg,usernamepembeli,namabarang,kategoribarang,hargabarang,qty,total);
           }
       } catch (SQLException ex) {
           Logger.getLogger(KeranjangController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
       }
       conman.closeConnection(); //menutup koneksi
       return krg; //mengembalikan nilai krg
   }
    
    public int updateKeranjang(Keranjang keranjang) {
        ConnectionManager conman = new ConnectionManager(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
        
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
            String query = "UPDATE keranjang SET id_barang = '" + keranjang.getId_barang() 
                            + "', username = '" + keranjang.getUsername() 
                            + "', namabarang = '" + keranjang.getNamabarang()
                            + "', kategoribarang = '" + keranjang.getKategoribarang() 
                            + "', hargabarang = '" + keranjang.getHargabarang()
                            + "', qty = '" + keranjang.getQty()
                            + "', total = '" + keranjang.getTotalharga()
                            + "' WHERE id_barang = '" + keranjang.getId_barang() + "'"; //eksekusi query sql untuk update data milik barang berdasarkan id
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) { //error handling
            Logger.getLogger(KeranjangController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil; //mengembalikan hasil dari operasi penyimpanan data barang
    }
    
        public int deleteKeranjang(){
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0;
        try (Connection con = conman.getConnection()) {
            // Menyiapkan pernyataan SQL
            String query = "DELETE FROM keranjang";
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) {
            Logger.getLogger(KeranjangController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil;
    }
}
