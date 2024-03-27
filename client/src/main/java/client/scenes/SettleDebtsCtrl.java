package client.scenes;

import client.utils.*;
import commons.Event;
import commons.Expense;
import commons.Participant;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SettleDebtsCtrl implements Initializable {
    /** INIT **/
    private final MainCtrl mc;
    private final ReadJSON jsonReader;
    private final EventServerUtils server;
    private final ParticipantsServerUtil partServer;
    private final ExpensesServerUtils expServer;
    private long eventid;
    private Participant selectedParticipant;
    /** PAGE FXML **/
    @FXML
    private ImageView imageview;
    @FXML
    private Label labelEventName;
    @FXML
    private Label sumLabel;
    @FXML
    private Label total;
    @FXML
    private Label shareLabel;
    @FXML
    private Label partName;
    @FXML
    private Label payedLabel;
    @FXML
    private Label payedAmount;
    @FXML
    private Label owedLabel;
    @FXML
    private Label owedAmount;
    @FXML
    private Button cancelBtn;
    @FXML
    private Text message;
    /** Combobox with Participant Info **/
    @FXML
    private ComboBox<String> comboBoxPart;
    private ArrayList<String> names = new ArrayList<>();
    /** TABLE EXPENSES **/
    @FXML
    private TableView<Expense> tableExp;
    @FXML
    private TableColumn<Expense, String> colDate;
    @FXML
    private TableColumn<Expense, String> colAm;
    @FXML
    private TableColumn<Expense, String> colTitle;
    private ObservableList<Expense> expdata;
    /** PIECHART **/
    @FXML
    private PieChart pieChart;
    private ObservableList<PieChart.Data> pieData;

    /**
     * Constructor of the InviteCtrl
     * @param server represent the EventServerUtils
     * @param mc represent the MainCtrl
     * @param jsonReader represent the ReadJSON
     * @param partServer represent the ParticipantsServerUtil
     * @param expServer represent the ExpensesServerUtils
     */
    @Inject
    public SettleDebtsCtrl(EventServerUtils server, MainCtrl mc, ReadJSON jsonReader, ParticipantsServerUtil partServer, ExpensesServerUtils expServer) {
        this.mc = mc;
        this.jsonReader = jsonReader;
        this.server = server;
        this.partServer = partServer;
        this.expServer = expServer;
    }

    /**
     * This method sets the images for the imageviews and adds the items to the comboboxes
     * @param url represent the URL
     * @param resourceBundle represent the ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("images/logo-no-background.png");
        imageview.setImage(image);

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        colDate.setCellValueFactory(q -> new SimpleStringProperty(formatter.format(q.getValue().getDate())));
        colAm.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getAmount())));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));

        comboBoxPart.setOnAction(event -> {
            String nameParticipant = comboBoxPart.getValue();
            if(nameParticipant == null){
                message.setText("Please select a Participant");
                return;
            }

            List<Participant> listAllParticipants = partServer.getAllParticipants()
                    .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());

            selectedParticipant = listAllParticipants.stream().filter(participant -> participant.getName().equals(nameParticipant)).findAny().get();
            partName.setText(selectedParticipant.getName());

            Event x = server.getEventByID(eventid);
            var allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().equals(x)).collect(Collectors.toList());
            var expensesOnlyParticipant = allExpenses.stream().filter(e -> e.getCreditor().equals(selectedParticipant)).collect(Collectors.toList());
            expdata = FXCollections.observableList(expensesOnlyParticipant);
            tableExp.setItems(expdata);

            double payed = getPayedValue(selectedParticipant);
            payedAmount.setText(String.valueOf(payed));

            double owed = getOwedValue();
            owedAmount.setText(String.valueOf(owed));
        });
    }

    /**
     * This method translates each label. It changes the text to the corresponding key with the translated text
     * @param taal the language that the user wants to switch to
     */
    public void langueageswitch(String taal) {
        String langfile = "language" + taal + ".json";
        HashMap<String, Object> h = jsonReader.readJsonToMap("src/main/resources/languageJSONS/"+langfile);
        sumLabel.setText(h.get("key47").toString());
        shareLabel.setText(h.get("key48").toString());
        payedLabel.setText(h.get("key49").toString());
        owedLabel.setText(h.get("key50").toString());
        cancelBtn.setText(h.get("key26").toString());
        colDate.setText(h.get("key41").toString());
        colAm.setText(h.get("key42").toString());
        colTitle.setText(h.get("key44").toString());
    }

    /**
     * Updates the page with the right information
     * @param id the id of the event
     */
    public void update(String id){
        long eid = Long.parseLong(id);
        this.eventid = eid;
        labelEventName.setText(server.getEventByID(eid).getName());

        List<Participant> listAllParticipants = partServer.getAllParticipants()
                .stream().filter(participant -> participant.getEvent().getId() == eventid).collect(Collectors.toList());
        names = (ArrayList<String>) listAllParticipants.stream().map(Participant::getName).collect(Collectors.toList());
        comboBoxPart.getItems().clear();
        comboBoxPart.getItems().addAll(names);

        double totalValue = getTotalExpenses();
        total.setText(String.valueOf(totalValue));

        pieData = FXCollections.observableArrayList();
        for(Participant p : listAllParticipants){
            double value = getPayedValue(p);
            pieData.add(new PieChart.Data(p.getName(), value));
        }

        pieChart.setData(pieData);
    }

    /**
     * This method calculates the total expenses of the event
     * @return the total expenses of the event
     */
    private Double getTotalExpenses() {
        List<Expense> allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().getId() == eventid).collect(Collectors.toList());
        double tot = 0;
        for(Expense e : allExpenses){
            tot = tot + e.getAmount();
        }
        return tot;
    }

    /**
     * This method calculates the amount the participant payed
     * @param participant the participant
     * @return the amount payed by the participant
     */
    private double getPayedValue(Participant participant) {
        List<Expense> allExpenses = expServer.getExpenses().stream().filter(e -> e.getEvent().getId() == eventid).collect(Collectors.toList());
        List<Expense> payedExpenses = allExpenses.stream().filter(e -> e.getCreditor().equals(participant)).collect(Collectors.toList());
        double tot = 0;
        for(Expense e : payedExpenses){
            tot = tot + e.getAmount();
        }
        return tot;
    }

    /**
     * This method calculates the amount the participant owes the group
     * @return the amount the participant owes
     */
    private double getOwedValue() {
        double total = getTotalExpenses();
        double payed = getPayedValue(selectedParticipant);
        int amountOfParticipants = names.size();
        double hasToPay = total / amountOfParticipants;
        double owed = hasToPay - payed;
        if(owed <= 0){
            message.setText("You don't owe anything!");
            return 0;
        } else{
            message.setText("You owe " + owed + " to the group!");
            return owed;
        }
    }

    /**
     * Method of the cancel button, when pressed, it shows the eventoverview screen
     */
    public void clickBack() throws IOException {
        mc.showEventOverview(String.valueOf(eventid));
    }
}