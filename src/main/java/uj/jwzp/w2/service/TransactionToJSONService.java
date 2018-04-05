package uj.jwzp.w2.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uj.jwzp.w2.entity.Transaction;
import uj.jwzp.w2.generator.TransactionGenerator;
import uj.jwzp.w2.parser.ProgramParameters;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class TransactionToJSONService {

    public void createJSON( List<Transaction> transactionList,
                                      ProgramParameters programParameters) throws ParseException {
        String outDirectory;
        if(programParameters.getOutDir().equals(""))
            outDirectory = "transaction.json";
        else
            outDirectory = programParameters.getOutDir() + "\\transaction.json";

        try (Writer writer = new FileWriter(new File(outDirectory) )) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(transactionList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}