package com.ekz.ctt.eckctt.mvp.model.entity;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
//@SmartTable(name = "护理白板表")
@SmartTable()
public class HuliTable {
    public HuliTable(String colum1, String colum2) {
        this.colum1 = colum1;
        this.colum2 = colum2;
    }

    @SmartColumn(id = 0,autoMerge = true)
    private String colum1;
    @SmartColumn(id = 1)
    private String colum2;
    @SmartColumn(id = 2,autoMerge = true)
    private String colum3;
    @SmartColumn(id = 3)
    private String colum4;

    public HuliTable(String colum1, String colum2, String colum3, String colum4) {
        this.colum1 = colum1;
        this.colum2 = colum2;
        this.colum3 = colum3;
        this.colum4 = colum4;
    }
}