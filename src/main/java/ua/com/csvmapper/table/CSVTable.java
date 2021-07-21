package ua.com.csvmapper.table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVTable {
    private static final String SPLIT = ",";
    private String[] headers;
    private List<String[]> values;

    public CSVTable(String URI) {
        try (BufferedReader reader = new BufferedReader(new FileReader(URI))) {
            String header;
            if ((header = reader.readLine()) != null) {
                headers = header.split(SPLIT);
                values = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    values.add(line.split(SPLIT));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getElementByIndexes(int row, int col) {
        if (row-1 < values.size() && col-1 < headers.length) {
            return values.get(row - 1)[col - 1];
        }
        throw new IllegalArgumentException("Row index more than " + values.size() + 1 +
                " or row index more than " + headers.length + 1);
    }

    public String getElementByName(int row, String colName) {
        int col = getIndexByName(colName);
        if (row -1 < values.size()) {
            return values.get(row-1)[col];
        }
        throw new IndexOutOfBoundsException("Row index more than " + values.size() + 1);
    }

    private int getIndexByName(String colName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equals(colName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Doesn`t exist column " + colName);
    }

    public List<String> getHeaders() {
        return Arrays.asList(headers);
    }
}
