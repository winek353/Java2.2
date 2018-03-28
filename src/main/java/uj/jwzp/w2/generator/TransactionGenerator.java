package uj.jwzp.w2.generator;

import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.service.RandomService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class TransactionGenerator {
    private RandomService randomService;
    private ItemGenerator itemGenerator;

    public TransactionGenerator(RandomService randomService, ItemGenerator itemGenerator) {
        this.randomService = randomService;
        this.itemGenerator = itemGenerator;
    }

    public  List<Transaction> generateTrasactions (ProgramParameters programParameters) throws ParseException {
        List<Transaction> transactionList = new ArrayList<>();

        for (int i=1;i<=programParameters.getEventsCount();i++){
            transactionList.add(new Transaction(i,randomService.getRandomTimeTimestamp(programParameters.getDateRange()),
                    randomService.getRandomInt(programParameters.getCustomerIdsRange()),
                    itemGenerator.generateItems(programParameters) ));
            transactionList.get(i-1).setSum(computeSum(transactionList.get(i-1)));
        }
        return transactionList;
    }

    public BigDecimal computeSum(Transaction transaction){
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Item item: transaction.getItems() ) {
            sum = sum.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return sum;
    }

//    private List<Item> generateItems(ProgramParameters programParameters){
//        String csvFileName = "src\\main\\resources\\"+programParameters.itemsFileName;
//        String line;
//        String cvsSplitBy = ",";
//        String [] lineElements;
//        List<Item> items = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
//            int i=0;
//            for(;i<programParameters.itemsCountRange.getValue0() && br.ready();i++){
//                br.readLine();
//            }
//            for (;i<=programParameters.itemsCountRange.getValue1() && br.ready();i++){
//                line = br.readLine();
//                lineElements = line.split(cvsSplitBy);
//                items.add(new Item(lineElements[0].replaceAll("\"", ""), randomService.getRandomInt(programParameters.itemsQuantityRange),
//                        new BigDecimal(lineElements[1])) );
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return items;
//    }
}
