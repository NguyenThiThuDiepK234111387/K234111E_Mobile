package com.NguyenThiThuDiep.models;

/**
 * Model cho một dòng dữ liệu giá vàng từ API VnExpress
 * URL: https://gw.vnexpress.net/cr/?name=tygia_vangv202206
 *
 * Cấu trúc JSON thực tế:
 * {
 *   "buy": 147000,
 *   "sell": 144000,
 *   "date_label": "24/06/2026",
 *   "label": "Hà Nội PNJ"
 * }
 */
public class GoldRateAPI {

    private String label;      // Tên: "Hà Nội PNJ", "TP.HCM SJC", ...
    private String dateLabel;  // Ngày: "24/06/2026"
    private long   buy;        // Giá mua (số nguyên, đơn vị nghìn đồng)
    private long   sell;       // Giá bán (số nguyên)

    public GoldRateAPI(String label, String dateLabel, long buy, long sell) {
        this.label     = label;
        this.dateLabel = dateLabel;
        this.buy       = buy;
        this.sell      = sell;
    }

    public String getLabel()     { return label; }
    public String getDateLabel() { return dateLabel; }
    public long   getBuy()       { return buy; }
    public long   getSell()      { return sell; }

    @Override
    public String toString() {
        // Hiển thị trong ListView
        return label + " (" + dateLabel + ")"
                + "\n  Mua: " + String.format("%,d", buy)
                + "  |  Bán: " + String.format("%,d", sell);
    }
}