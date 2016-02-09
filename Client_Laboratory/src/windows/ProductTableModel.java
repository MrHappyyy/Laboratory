package windows;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private int columnCount = 6;
    private ArrayList<String[]> dataArrayList;

    public ProductTableModel() {
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
            case 2: return "Ім’я групи";
            case 3: return "Ім’я підгрупи";
            case 4: return "Кількість шт/кг";
            case 5: return "Ціна за шт/кг";
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void addDate(String[] rows) {
        String[] rowTable = new String[getColumnCount()];
        rowTable = rows;
        dataArrayList.add(rowTable);
    }

    public void remove() {
        dataArrayList = new ArrayList<String[]>();
    }
}
