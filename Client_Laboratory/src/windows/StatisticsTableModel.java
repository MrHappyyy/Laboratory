package windows;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class StatisticsTableModel extends AbstractTableModel  {
    private int columnCount = 8;
    private ArrayList<String[]> dataArrayList;

    public StatisticsTableModel() {
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
            case 4: return "Ціна за шт/кг";
            case 5: return "Продана кількість";
            case 6: return "Отримано грошей";
            case 7: return "Дата продажу";
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
