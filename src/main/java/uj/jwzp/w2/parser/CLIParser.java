package uj.jwzp.w2.parser;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.time.DateUtils;
import org.javatuples.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CLIParser {

    public ProgramParameters getProgramParameters(String[] args) throws Exception {
        Map<String, String> map = readCmdLine(args);

        Pair<Integer, Integer> customerIdsRange= getPair(map.get("customerIds"));
        Pair<String, String> dateRange= getDatePair(map.get("dateRange"));
        String itemsFileName= map.get("itemsFile");
        Pair<Integer, Integer> itemsCountRange= getPair(map.get("itemsCount"));
        Pair<Integer, Integer> itemsQuantityRange=  getPair(map.get("itemsQuantity"));
        Integer eventsCount= Integer.valueOf(map.get("eventsCount"));
        String outDir= map.get("outDir");

        return new ProgramParameters(customerIdsRange, dateRange, itemsFileName, itemsCountRange,
                itemsQuantityRange, eventsCount, outDir);
    }


    public boolean isDateValid( String dateFromat, String dateToValidate){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {
            sdf.parse(dateToValidate);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Pair<String, String> getDatePair(String dates) throws Exception {
        String firstDate = dates.substring(0, dates.length()/2);
        String secondDate = dates.substring( (dates.length()/2) +1, dates.length());
        if(!isDateValid("yyyy-MM-dd'T'HH:mm:ss.SSSZ", firstDate) ||
                !isDateValid("yyyy-MM-dd'T'HH:mm:ss.SSSZ", secondDate)){
            throw new Exception("wrong date interval in program parameter");
        }
        return new Pair<>(firstDate, secondDate);
    }

    public Pair<Integer, Integer> getPair(String s) throws Exception {
        String[] range = s.split(":");
        if(Integer.valueOf(range[0]) > Integer.valueOf(range[1]) || Integer.valueOf(range[0]) <= 0 ||
                Integer.valueOf(range[1]) <= 0){
            throw new Exception("wrong interval in program parameter");
        }
        return new Pair<>(Integer.valueOf(range[0]), Integer.valueOf(range[1]));
    }

    public Map<String, String> readCmdLine(String[] args) throws Exception {
        Options options = new Options();
        // add t option
        options.addOption("customerIds", true, "Zakres generowania wartości do pola \"customer_id\"");
        options.addOption("dateRange", true, "Zakres dat");
        options.addOption("itemsFile", true, "nazwa pliku csv zawierającego spis produktów");
        options.addOption("itemsCount", true, "zakres ilości elementów generowanych w tablicy \"items\"");
        options.addOption("itemsQuantity", true, " zakres z jakiego będzie generowana ilość kupionych produktów danego typu (pole \"quantity\")");
        options.addOption("eventsCount", true, " ilość transakcji (pojedynczych plików) do wygenerowania");
        options.addOption("outDir", true, "  katalog, do którego mają być zapisane pliki");

        CommandLineParser parser = new DefaultParser();
        Map<String, String> map = new HashMap<>();

            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("customerIds")) {
                map.put("customerIds",(String) cmd.getParsedOptionValue("customerIds"));
            }
            else {
                map.put("customerIds", "1:20");
            }

            if(cmd.hasOption("dateRange")) {
                map.put("dateRange",(String) cmd.getParsedOptionValue("dateRange"));
            }
            else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Date date = new Date();
                Date startOfDay = DateUtils.truncate(date, Calendar.DATE);
                Date endOfDay = DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);

                map.put("dateRange", dateFormat.format(startOfDay) +
                        ":" + dateFormat.format(endOfDay));//).replaceAll("\\+","-")
            }
            if(cmd.hasOption("itemsFile")) {
                map.put("itemsFile", (String) cmd.getParsedOptionValue("itemsFile"));
            }
            else {
                throw new Exception("there is no csv file");
            }
            if(cmd.hasOption("itemsCount")) {
                map.put("itemsCount", (String) cmd.getParsedOptionValue("itemsCount"));
            }
            else {
                map.put("itemsCount", "1:5");
            }
            if(cmd.hasOption("itemsQuantity")) {
                map.put("itemsQuantity",  (String) cmd.getParsedOptionValue("itemsQuantity"));
            }
            else {
                map.put("itemsQuantity", "1:5");
            }
            if(cmd.hasOption("eventsCount")) {
                map.put("eventsCount",  (String) cmd.getParsedOptionValue("eventsCount"));
            }
            else {
                map.put("eventsCount", "100");
            }
            if(cmd.hasOption("outDir")) {
                map.put("outDir",  (String) cmd.getParsedOptionValue("outDir"));
            }
            else {
                map.put("outDir", System.getProperty("user.dir"));
            }

        return map;
    }
}
