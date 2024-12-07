package application;

import entities.Sale;

import java.io.*;
import java.util.*;

public class Application {

    public static void main(String[] args) {

        
        try (Scanner sc = new Scanner(System.in)) {
            List<Sale> sales = new ArrayList<>();
            System.out.println("Entre o caminho do arquivo:");
            String path = sc.nextLine();

         
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("O sistema não pode encontrar o arquivo especificado");
                System.exit(1); 
            }

          
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;

                while ((line = br.readLine()) != null) {
                 
                    if (line.startsWith("Month")) {
                        continue;
                    }

                    String[] data = line.split(",");

                    Integer month = Integer.parseInt(data[0]);
                    Integer year = Integer.parseInt(data[1]);
                    String seller = data[2];
                    Integer items = Integer.parseInt(data[3]);
                    Double total = Double.parseDouble(data[4]);

                    Sale sale = new Sale(month, year, seller, items, total);
                    sales.add(sale);
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
                System.exit(1);  
            }

           
            Map<String, Double> totalVendasPorVendedor = new HashMap<>();
            
            
            for (Sale sale : sales) {
                totalVendasPorVendedor.merge(sale.getSeller(), sale.getTotal(), Double::sum);
            }

           
            System.out.println("Total de vendas por vendedor:");
            totalVendasPorVendedor.forEach((seller, total) -> 
                System.out.println(seller + " - R$ " + String.format("%.2f", total))
            );
        }
    }
}
