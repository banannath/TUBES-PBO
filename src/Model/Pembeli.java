package Model;

public class Pembeli {
    private String username;
    private String nama;
    private String email;
    private String alamat;
    private String password;

    public Pembeli() {
        
    }
    
    public Pembeli(String username, String nama, String email, String alamat, String password) {
        this.username = username;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
