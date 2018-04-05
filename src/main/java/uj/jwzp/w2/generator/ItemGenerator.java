package uj.jwzp.w2.generator;

import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.entity.Item;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemGenerator {
    private RandomService randomService;
    BufferedReaderService bufferedReaderService;

    public ItemGenerator(RandomService randomService, BufferedReaderService bufferedReaderService) {
        this.randomService = randomService;
        this.bufferedReaderService = bufferedReaderService;
    }


    public List<Item> generateItems(ProgramParameters programParameters){
        String csvFileName = "src\\main\\resources\\"+programParameters.getItemsFileName();
        String line;
        String cvsSplitBy = ",";
        String [] lineElements;
        List<Item> items = new ArrayList<>();

        try (BufferedReader br = bufferedReaderService.getBufferedReader(csvFileName)) {//new BufferedReader(new FileReader(csvFileName))) {
            int i=0;
            for(;i<programParameters.getItemsCountRange().getValue0() && br.ready();i++){
                br.readLine();
            }
            for (;i<=programParameters.getItemsCountRange().getValue1() && br.ready();i++){
                line = br.readLine();
                lineElements = line.split(cvsSplitBy);
                items.add(new Item(lineElements[0].replaceAll("\"", ""),
                        randomService.getRandomInt(programParameters.getItemsQuantityRange()),
                        new BigDecimal(lineElements[1])) );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
