package application;

import entities.Sale;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
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

       
        List<Sale> topSales = sales.stream()
                .filter(sale -> sale.getYear() == 2016)
                .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                .limit(5)
                .collect(Collectors.toList());

        System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
        topSales.forEach(System.out::println);

        
        double totalLogan = sales.stream()
                .filter(sale -> sale.getSeller().equals("Logan") && (sale.getMonth() == 1 || sale.getMonth() == 7))
                .mapToDouble(Sale::getTotal)
                .sum();

        System.out.println("\nValor total vendido pelo vendedor Logan nos meses 1 e 7: R$ " + totalLogan);
    }

}
