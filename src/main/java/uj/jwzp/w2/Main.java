package uj.jwzp.w2;

import uj.jwzp.w2.generator.ItemGenerator;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.CLIParser;
import uj.jwzp.w2.parser.ProgramParameters;
import uj.jwzp.w2.service.BufferedReaderService;
import uj.jwzp.w2.service.RandomService;
import uj.jwzp.w2.service.TransactionToJSONService;

public class Main {

    public static void main(String[] args) throws Exception {
        CLIParser cliParser = new CLIParser();

        ProgramParameters programParameters = cliParser.getProgramParameters(args);

        TransactionGenerator transactionGenerator = new TransactionGenerator(new RandomService(),
                new ItemGenerator(new RandomService(), new BufferedReaderService()));

        TransactionToJSONService transactionToJsonService = new TransactionToJSONService();

        transactionToJsonService.createJSON(transactionGenerator.generateTrasactions(programParameters),
                programParameters);
    }
}
