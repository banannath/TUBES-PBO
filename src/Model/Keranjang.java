/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.*;

/**
 *
 * @author Hanna Nathania
 */
public class Keranjang {
    private String id_barang, username, namabarang, kategoribarang;
    private int hargabarang, qty, totalharga;

    public Keranjang() {
        
    }

    public Keranjang(String id_barang, String username, String namabarang, String kategoribarang, int hargabarang, int qty, int totalharga) {
        this.id_barang = id_barang;
        this.username = username;
        this.namabarang = namabarang;
        this.kategoribarang = kategoribarang;
        this.hargabarang = hargabarang;
        this.qty = qty;
        this.totalharga = totalharga;
    }
    
    public Keranjang(String id_barang, String username, String namabarang, String kategoribarang, int hargabarang, int qty) {
        this.id_barang = id_barang;
        this.username = username;
        this.namabarang = namabarang;
        this.kategoribarang = kategoribarang;
        this.hargabarang = hargabarang;
        this.qty = qty;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public String getKategoribarang() {
        return kategoribarang;
    }

    public void setKategoribarang(String kategoribarang) {
        this.kategoribarang = kategoribarang;
    }

    public int getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(int hargabarang) {
        this.hargabarang = hargabarang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(int totalharga) {
        this.totalharga = totalharga;
    }
  
}
