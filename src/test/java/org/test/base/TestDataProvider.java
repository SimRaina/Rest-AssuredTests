package org.test.base;

import org.test.ExcelReader.ExcelReaderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized test data provider to handle Excel data reading and management
 */
public class TestDataProvider {

    private static ExcelReaderTest excelReader = new ExcelReaderTest();

    /**
     * Read data from Excel by sheet and row
     */
    public static ArrayList<String> getExcelData(String sheetName, int rowNumber) {
        return excelReader.getData(APIConfig.TEST_FILE_PATH, APIConfig.TEST_DATA_FILE, sheetName, rowNumber);
    }

    /**
     * Create employee data map
     */
    public static Map<String, String> createEmployeeData(String name, String salary, String age) {
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("salary", salary);
        data.put("age", age);
        return data;
    }

    /**
     * Create default employee data for testing
     */
    public static Map<String, String> getDefaultEmployeeData() {
        return createEmployeeData("Simran", "140000", "30");
    }

    /**
     * Create user data for Reqres API
     */
    public static Map<String, String> createUserData(String name, String job) {
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("job", job);
        return data;
    }
}

