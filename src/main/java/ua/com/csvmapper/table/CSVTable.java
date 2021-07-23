package ua.com.csvmapper.table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class CSVTable {
    private static final String SPLIT = ",";
    private LinkedHashMap<String, Integer> headers;
    private List<String[]> values;

    public List<String[]> getValues() {
        return values;
    }

    public CSVTable(String URI) {
        try (BufferedReader reader = new BufferedReader(new FileReader(URI))) {
            String header;
            if ((header = reader.readLine()) != null) {
                String[] headersArray = header.split(SPLIT);
                headers = new LinkedHashMap<>();
                for (int i = 0; i < headersArray.length; i++) {
                    headers.put(headersArray[i], i);
                }
                values = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    values.add(line.split(SPLIT));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getElementByIndexes(int row, int col) {
        if (row - 1 < values.size() && col - 1 < headers.size()) {
            return values.get(row - 1)[col - 1];
        }
        throw new IllegalArgumentException("Row index more than " + values.size() + 1 +
                " or row index more than " + headers.size() + 1);
    }

    public String getElementByName(int row, String colName) {
        Integer col = getIndexByName(colName);
        if(col == null){
            throw new NoSuchElementException("Column " + colName + " doesn`t exist");
        }
        else if (row - 1 < values.size() & col < headers.size()) {
            return values.get(row - 1)[col];
        }
        throw new IllegalArgumentException("Row index more than " + values.size() + 1);
    }

    private Integer getIndexByName(String colName) {
        return headers.get(colName);
    }

    public List<String> getHeaders() {
        return new ArrayList<>(headers.keySet());
    }
}
