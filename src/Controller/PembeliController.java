package Controller;

import Model.Pembeli;
import Connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PembeliController {

    private ConnectionManager connection;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public Pembeli getId(String username){
       ConnectionManager conman = new ConnectionManager(); //membuat objek koneksi ke databse
       Connection con = conman.getConnection(); //koneksi ke database
       Pembeli pem = null; //inisialisasi objek mahasiswa sebagai null
       String query = "SELECT * FROM pembeli WHERE username = '" + username + "'"; //mengambil data dari database
       try {
           Statement stm = con.createStatement(); //membuat statement untuk eksekusi query
           ResultSet rs = stm.executeQuery(query); //eksekusi query
           if (rs.next()){
               String usernamepem = rs.getString("username"); //mendapatkan value "username" dari result set
               String nama = rs.getString("nama"); //mendapatkan value "nama" dari result set
               String email = rs.getString("email"); //mendapatkan value "email" dari result set
               String alamat = rs.getString("alamat");
               String password = rs.getString("password");
               pem = new Pembeli(usernamepem,nama,email,alamat,password); //membuat object baru berdasarkan value dari result set
           }
       } catch (SQLException ex) {
           Logger.getLogger(PembeliController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
       }
       conman.closeConnection(); //menutup koneksi
       return pem; //mengembalikan nilai pem
   }

    public int createPembeli(Pembeli pembeli) {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0; //variabel unutk menyimpan hasil operasi database
        try (Connection con = conman.getConnection()) { //mencoba untuk melakukan koneksi ke database dengan memanggil method getConnection()
            //query sql untuk memasukkan pembeli baru
            String query = "INSERT INTO pembeli(username, nama, email, alamat, password) VALUES ('" + pembeli.getUsername() + "', '" + pembeli.getNama() + "', '" + pembeli.getEmail() + "', '" + pembeli.getAlamat() + "', '" + pembeli.getPassword() + "')"; //query untuk memasukkan data pembeli yang baru  
            String sama = "SELECT * FROM pembeli WHERE username = '"+pembeli.getUsername()+"'"; //melihat pembeli berdasarkan yanga da
            Statement st = con.createStatement(); //membuat statement untuk eksekusi query
            ResultSet rs = st.executeQuery(sama); //eksekusi query
            if(rs.next() == true){ //kondisi jika ada pembeli yang sama username
                hasil = -1;
            }
            try (Statement stm = con.createStatement()) { 
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex){ //error handling
            Logger.getLogger(PembeliController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil;
    }

    public List<Pembeli> getAllPembeli() {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        List<Pembeli> pembeliList = new ArrayList<>(); //membuat list pembeli
        try (Connection con = conman.getConnection(); //mencoba untuk melakuukan koneksi ke database dengan memanggil method getConnection()
             Statement stmt = con.createStatement(); //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
             ResultSet rs = stmt.executeQuery("SELECT * FROM pembeli")) { //melakukan eksekusi query sql

            while (rs.next()) { //mengambil data dari ResultSet dan memasukkannya ke dalam objek Mahasiswa yang kemudian ditambahkan ke dalam list pembeliList.
                Pembeli pembeli = new Pembeli(); //membuat object pembeli untuk menambahkan pembeli baru pada list
                pembeli.setUsername(rs.getString("username"));
                pembeli.setPassword(rs.getString("password"));
                pembeli.setNama(rs.getString("nama"));
                pembeli.setEmail(rs.getString("email"));
                pembeli.setAlamat(rs.getString("alamat"));
                pembeliList.add(pembeli); //menambahkan objek Mahasiswa yang telah diisi dengan data dari ResultSet ke dalam list pembeliList.
            }

        } catch (SQLException ex) { //error handling
            System.out.println("Error mengambil data pembeli: " + ex.getMessage()); //pesan error 
        } finally {
            conman.closeConnection(); //memutus koneksi database
        }
        return pembeliList; //mengembalikan list pembeliList yang telah diisi dengan data pembeli dari database
    }

    public int deletePembeli(Pembeli pembeli) throws SQLException {
        ConnectionManager conman = new ConnectionManager(); //membuat objek untuk koneksi ke database dengan memanggil method getConnection()
        int hasil = 0;
        try (Connection con = conman.getConnection()) {
            // Menyiapkan pernyataan SQL
            String query = "DELETE FROM pembeli WHERE username = '" + pembeli.getUsername() + "'";
            try (Statement stm = con.createStatement()) { //menampung hasil eksekusi dari sebuah query SQL yang akan dijalankan.
                hasil = stm.executeUpdate(query); //eksekusi query sql yang telah dibuat sebelumnya
            }
        } catch (SQLException ex) {
            Logger.getLogger(PembeliController.class.getName()).log(Level.SEVERE, null, ex); //mencatat pesan kesalalahan  
        } finally {
            conman.closeConnection(); //menutup koneksi database
        }
        return hasil;
    }
    
    public int updatePembeli(Pembeli pembeli) {
        ConnectionManager conman = new ConnectionManager();
        int hasil = 0;
        Connection con = conman.getConnection();
        try {
            String query = "UPDATE pembeli SET nama = '"+ pembeli.getNama()+ "', email = '" + pembeli.getEmail() + "', alamat = '" + pembeli.getAlamat() + "', password = '" + pembeli.getPassword()+ "' WHERE username = '" +  pembeli.getUsername() + "'";
            Statement stm = con.createStatement();
            hasil = stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(PembeliController.class.getName()).log(Level.SEVERE, null, ex);
        }
        conman.closeConnection();
        return hasil;
    }
    
    public boolean validatePembeli(Pembeli loginUser) {
        try {
            // Menyiapkan pernyataan SQL
            ConnectionManager conman = new ConnectionManager();
            Connection con = conman.getConnection();
            Statement stm = con.createStatement();
            String sql = "SELECT * FROM pembeli WHERE username = '"+ loginUser.getUsername() + "' AND password = '" + loginUser.getPassword() + "'" ;
            // Menjalankan pernyataan SQL
            ResultSet resultSet = stm.executeQuery(sql);
            System.out.println(resultSet);
            if (resultSet.next()) {
                // Jika pengguna dengan nama pengguna dan kata sandi yang cocok ditemukan
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

}
