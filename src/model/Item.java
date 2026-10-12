package model;

public class Item {
    private String nama;
    private int harga;
    private int qty;

    public Item(String nama, int harga, int qty) {
        this.nama = nama;
        this.harga = harga;
        this.qty = qty;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getQty() {
        return qty;
    }

    public int getTotal() {
        return harga * qty;
    }
}
