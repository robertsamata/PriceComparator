package com.example.PriceComparatorBackend.util;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;


import com.example.PriceComparatorBackend.model.Discount;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.example.PriceComparatorBackend.model.Product;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;

@Component
public class CsvReader {
    public List<Product> loadProducts(String filename) {
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("data/" + filename))))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<Product> products = new ArrayList<>();
            String[] line;
            reader.readNext(); // skip header
            while ((line = reader.readNext()) != null) {
                if (line.length < 7) {
                    System.out.println("Linie invalidă (nu are 7 coloane): " + Arrays.toString(line));
                    continue;
                }

                String id = line[0];
                String name = line[1];
                String category = line[2];
                String brand = line[3];
                double quantity = Double.parseDouble(line[4].replace(',', '.'));
                String unit = line[5];
                double price = Double.parseDouble(line[6].replace(',', '.'));

                products.add(new Product(price, unit, quantity, brand, category, name, id));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<Discount> loadDiscounts(String filename) {
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("data/" + filename)))
        )
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {

            List<Discount> discounts = new ArrayList<>();
            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                if (line.length < 9) {
                    System.out.println("Linie invalidă (nu are 9 coloane): " + Arrays.toString(line));
                    continue;
                }

                String productId = line[0];
                LocalDate fromDate = LocalDate.parse(line[6]);
                LocalDate toDate = LocalDate.parse(line[7]);
                int percentage = Integer.parseInt(line[8]);

                discounts.add(new Discount(productId, fromDate, toDate, percentage));
            }

            return discounts;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}

