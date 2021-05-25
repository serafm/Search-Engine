package covid.lucene;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


public class UI extends JFrame{

    private JTextField inputSearch;
    private JPanel panel1;
    private JButton buttonSearch;
    private JCheckBox checkfortitle;
    private JLabel docresults;
    private JLabel hyperlink;
    private JButton history;
    private JLabel covidtitle;
    private JLabel historypreview;
    private JButton deletehistory;
    private JButton hidehistory;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel pagenum;
    private JLabel numofdocs;
    private JButton SortByDateButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private int numOfpages;
    private int currentPage=1;
    private int v=0;
    private String str;
    private String txt;
    private boolean titlecheck=false;
    private String html = "<html>";
    private String newLine = "<br/>";

    public UI(String title) throws IOException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        this.getRootPane().setDefaultButton(buttonSearch);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        SortByDateButton.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        button5.setVisible(false);
        button6.setVisible(false);
        button7.setVisible(false);
        button8.setVisible(false);
        button9.setVisible(false);
        button10.setVisible(false);
        nextButton.setVisible(false);
        previousButton.setVisible(false);
        MainLucene test = new MainLucene();

        hyperlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               inputSearch.setText(test.getCorrect());
               buttonSearch.doClick();
               hyperlink.setText("");
               test.setCorrect("");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // the mouse has entered the label
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // the mouse has exited the label
            }
        });

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    test.setSortByDate(false);
                    test.setCorrect("");
                    hyperlink.setText("");
                    if(checkfortitle.isSelected()){
                        titlecheck = true;
                        test.setTitleCheck(titlecheck);
                    }

                    docresults.setText(test.search(inputSearch.getText()));
                    if(test.getBool()==true){
                        hyperlink.setText("Do you mean: " + test.getCorrect());
                        hyperlink.setForeground(Color.BLUE.darker());
                        hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    numofdocs.setText(test.getS2());
                    historypreview.setText("");

                    if(test.getDataSize() % 10 == 0) {
                        numOfpages = test.getDataSize() / 10;
                    }else if (test.getDataSize()<=10){
                        numOfpages = 1;
                    }else{
                        numOfpages = (test.getDataSize() / 10) + 1;
                    }
                    currentPage=1;

                    if(numOfpages>0){
                        SortByDateButton.setVisible(true);
                        button1.setVisible(true);
                        button2.setVisible(true);
                        button3.setVisible(true);
                        button4.setVisible(true);
                        button5.setVisible(true);
                        button6.setVisible(true);
                        button7.setVisible(true);
                        button8.setVisible(true);
                        button9.setVisible(true);
                        button10.setVisible(true);
                        nextButton.setVisible(true);
                        previousButton.setVisible(true);
                        pagenum.setVisible(true);
                        pagenum.setText("Page " + currentPage + " of " + numOfpages);
                    }

                    if(!test.getErr().equals("")){
                        SortByDateButton.setVisible(false);
                        button1.setVisible(false);
                        button2.setVisible(false);
                        button3.setVisible(false);
                        button4.setVisible(false);
                        button5.setVisible(false);
                        button6.setVisible(false);
                        button7.setVisible(false);
                        button8.setVisible(false);
                        button9.setVisible(false);
                        button10.setVisible(false);
                        nextButton.setVisible(false);
                        previousButton.setVisible(false);
                        pagenum.setVisible(false);
                    }

                }catch (IOException | ParseException | InvalidTokenOffsetsException ioException){
                    ioException.printStackTrace();
                }
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent y) {
                    historypreview.setText(test.getHistory());
            }
        });

        this.setVisible(true);
        deletehistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historypreview.setText("");
                test.deleteHistory();
            }
        });

        hidehistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historypreview.setText("");
            }
        });



        AutoSuggestion autoSuggest = new AutoSuggestion( inputSearch , null, null, Color.WHITE.brighter(), Color.BLACK, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {

                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();
                words.add("covid-19 vaccine");
                words.add("covid-19 vaccine side effects");
                words.add("covid-19 astrazeneca vaccine");
                words.add("covid-19 pfizer vaccine");
                words.add("covid-19 pandemic");
                words.add("covid-19 disease");
                words.add("coronavirus vaccine");
                words.add("coronavirus pandemic");
                words.add("coronavirus disease");
                words.add("coronavirus cases");
                words.add("coronavirus vaccine research");
                words.add("astrazeneca vaccine");
                words.add("pfizer vaccine");
                words.add("pfizer vaccine");
                words.add("pandemic coronavirus");
                setDictionary(words);

                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage = currentPage + 1;
                str="";
                if(test.getSortByDate()){
                    if (currentPage==2){
                        for (int i=10; i < 20; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==3){
                        for (int i=20; i < 30; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==4){
                        for (int i=30; i < 40; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage>=6 || currentPage==5) {
                        for (int i = 40; i < 50; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                            if (i == test.getDataSize() - 1) {
                                break;
                            }
                        }
                        currentPage=5;
                    }
                    docresults.setText(html + str);
                    pagenum.setText("Page " + currentPage + " of " + numOfpages);
                }else{
                    if (currentPage==2){
                        for (int i=10; i < 20; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==3){
                        for (int i=20; i < 30; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==4){
                        for (int i=30; i < 40; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage>=6 || currentPage==5) {
                        for (int i = 40; i < 50; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                            if (i == test.getDataSize() - 1) {
                                break;
                            }
                        }
                        currentPage=5;
                    }
                    docresults.setText(html + str);
                    pagenum.setText("Page " + currentPage + " of " + numOfpages);
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage=currentPage-1;
                str="";
                if(test.getSortByDate()){
                    if (currentPage==4){
                        for (int i=30; i < 40; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==3){
                        for (int i=20; i < 30; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==2){
                        for (int i=10; i < 20; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if(currentPage==1){
                        for (int i=0; i < 10; i++) {
                            str = str + test.getSortedDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if(currentPage<1){
                        currentPage=1;
                        for (int i=0; i < 10; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    docresults.setText(html + str);
                    pagenum.setText("Page " + currentPage + " of " + numOfpages);
                }else{
                    if (currentPage==4){
                        for (int i=30; i < 40; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==3){
                        for (int i=20; i < 30; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if (currentPage==2){
                        for (int i=10; i < 20; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if(currentPage==1){
                        for (int i=0; i < 10; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    if(currentPage<1){
                        currentPage=1;
                        for (int i=0; i < 10; i++) {
                            str = str + test.getDataFileName().get(i) + newLine + newLine;
                        }
                    }
                    docresults.setText(html + str);
                    pagenum.setText("Page " + currentPage + " of " + numOfpages);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=0;
                }
                if (currentPage==2){
                    v=10;
                }
                if (currentPage==3){
                    v=20;
                }
                if (currentPage==4){
                    v=30;
                }
                if (currentPage==5){
                    v=40;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=1;
                }
                if (currentPage==2){
                    v=11;
                }
                if (currentPage==3){
                    v=21;
                }
                if (currentPage==4){
                    v=31;
                }
                if (currentPage==5){
                    v=41;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=2;
                }
                if (currentPage==2){
                    v=12;
                }
                if (currentPage==3){
                    v=22;
                }
                if (currentPage==4){
                    v=32;
                }
                if (currentPage==5){
                    v=42;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=3;
                }
                if (currentPage==2){
                    v=13;
                }
                if (currentPage==3){
                    v=23;
                }
                if (currentPage==4){
                    v=33;
                }
                if (currentPage==5){
                    v=43;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=4;
                }
                if (currentPage==2){
                    v=14;
                }
                if (currentPage==3){
                    v=24;
                }
                if (currentPage==4){
                    v=34;
                }
                if (currentPage==5){
                    v=44;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=5;
                }
                if (currentPage==2){
                    v=15;
                }
                if (currentPage==3){
                    v=25;
                }
                if (currentPage==4){
                    v=35;
                }
                if (currentPage==5){
                    v=45;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=6;
                }
                if (currentPage==2){
                    v=16;
                }
                if (currentPage==3){
                    v=26;
                }
                if (currentPage==4){
                    v=36;
                }
                if (currentPage==5){
                    v=46;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=7;
                }
                if (currentPage==2){
                    v=17;
                }
                if (currentPage==3){
                    v=27;
                }
                if (currentPage==4){
                    v=37;
                }
                if (currentPage==5){
                    v=47;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=8;
                }
                if (currentPage==2){
                    v=18;
                }
                if (currentPage==3){
                    v=28;
                }
                if (currentPage==4){
                    v=38;
                }
                if (currentPage==5){
                    v=48;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        button10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage==1){
                    v=9;
                }
                if (currentPage==2){
                    v=19;
                }
                if (currentPage==3){
                    v=29;
                }
                if (currentPage==4){
                    v=39;
                }
                if (currentPage==5){
                    v=49;
                }
                if(test.getSortByDate()){
                    try {
                        test.OpenFile(test.ToHTML(test.getSortedData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        test.OpenFile(test.ToHTML(test.getData().get(v)));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        SortByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test.setSortByDate(true);
                try {
                    docresults.setText(test.search(inputSearch.getText()));

                    numofdocs.setText(test.getS2());
                    historypreview.setText("");

                    if(test.getDataSize() % 10 == 0) {
                        numOfpages = test.getDataSize() / 10;
                    }else if (test.getDataSize()<=10){
                        numOfpages = 1;
                    }else{
                        numOfpages = (test.getDataSize() / 10) + 1;
                    }
                    currentPage=1;

                    if(numOfpages>0){
                        SortByDateButton.setVisible(true);
                        button1.setVisible(true);
                        button2.setVisible(true);
                        button3.setVisible(true);
                        button4.setVisible(true);
                        button5.setVisible(true);
                        button6.setVisible(true);
                        button7.setVisible(true);
                        button8.setVisible(true);
                        button9.setVisible(true);
                        button10.setVisible(true);
                        nextButton.setVisible(true);
                        previousButton.setVisible(true);
                        pagenum.setVisible(true);
                        pagenum.setText("Page " + currentPage + " of " + numOfpages);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                } catch (InvalidTokenOffsetsException invalidTokenOffsetsException) {
                    invalidTokenOffsetsException.printStackTrace();
                }
            }
        });
    }
}
