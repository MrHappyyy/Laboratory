package windows;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BasketProductTableModel extends AbstractTableModel {
    private int columnCount = 4;
    private ArrayList<String[]> dataArrayList;

    public ArrayList<String[]> getDataArrayList() {
        return dataArrayList;
    }

    public BasketProductTableModel() {
        dataArrayList = new ArrayList<String[]>();
        for (int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.add(new String[getColumnCount()]);
        }
    }

    @Override
    public int getRowCount() {
        return dataArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int column) {

        switch (column) {
            case 0: return "№";
            case 1: return "Ім’я товару";
            case 2: return "Кількість";
            case 3: return "Ціна";
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        dataArrayList.get(rowIndex)[columnIndex] = String.valueOf(aValue);
    }

    public void addDate(String[] rows) {
        String[] rowTable = new String[getColumnCount()];
        rowTable = rows;
        dataArrayList.add(rowTable);
    }

    public void removeIsAll() {
        dataArrayList = new ArrayList<String[]>();
    }

    public void removeIsRows(int rows) {
        dataArrayList.remove(rows);
    }
}
