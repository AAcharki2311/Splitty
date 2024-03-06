/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MainCtrl {

    private Stage primaryStage;
    private Scene startScene;
    private Scene eventOverviewScene;
    private Scene inviteScene;
    private Scene expenseScene;
    private Scene participantScene;
    private Scene editParticipantScene;
    private Scene editExpenseScene;
    private Scene aloginScene;
    private Scene adashScene;

    private StartScreenCtrl startCtrl;
    private EventOverviewCtrl eventOCtrl;
    private InviteCtrl inviteCtrl;
    private ParticipantCtrl participantCtrl;
    private EditParticipantCtrl editParticipantCtrl;
    private EditExpenseCtrl editExpenseCtrl;
    private AddExpenseCtrl addExpenseCtrl;


    public void initialize(Stage primaryStage,
                           Pair <StartScreenCtrl, Parent> start,
                           Pair <EventOverviewCtrl, Parent> eventO, Pair <InviteCtrl, Parent> invite,
                           Pair <AddExpenseCtrl, Parent> addExpense, Pair <ParticipantCtrl, Parent> participant,
                           Pair <EditParticipantCtrl, Parent> editParticipant, Pair <EditExpenseCtrl, Parent> editExpense,
                           Pair <AdminLoginCtrl, Parent> alogin, Pair <AdminDashboardCtrl, Parent> adash) throws IOException {
        this.primaryStage = primaryStage;
        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.inviteScene = new Scene(invite.getValue());
        this.expenseScene = new Scene(addExpense.getValue());
        this.participantScene = new Scene(participant.getValue());
        this.editParticipantScene = new Scene(editParticipant.getValue());
        this.editExpenseScene = new Scene(editExpense.getValue());

        this.startCtrl = start.getKey();
        this.eventOCtrl = eventO.getKey();
        this.inviteCtrl = invite.getKey();
        this.participantCtrl = participant.getKey();
        this.editParticipantCtrl = editParticipant.getKey();
        this.editExpenseCtrl = editExpense.getKey();
        this.addExpenseCtrl = addExpense.getKey();

        ltest();

//__________________ADMIN PAGES____________________________________________________________________________

        this.aloginScene = new Scene(alogin.getValue());
        this.adashScene = new Scene(adash.getValue());

        showStart();
        primaryStage.show();
    }

    public String setLanguage() throws IOException {
        Properties appProps = new Properties();
        String configFilePath = "C:\\Users\\ayoub\\oopp-ayoubacharki\\TEAM\\oopp-team-23\\commons\\src\\config\\configfile.properties";

        FileInputStream inputStream = new FileInputStream(configFilePath);
        appProps.load(inputStream);
        inputStream.close();

        String configtaal = appProps.getProperty("language");
        String languageJSON;
        switch(configtaal){
            case "dutch":
                languageJSON = "NL";
                return languageJSON;
            case "french":
                languageJSON = "FR";
                return languageJSON;
            default:
                languageJSON = "EN";
                return languageJSON;
        }
    }

    public void ltest() throws IOException {
        String languageToTranslate = setLanguage();

        this.startCtrl.langueageswitch(languageToTranslate);
        this.eventOCtrl.langueageswitch(languageToTranslate);
        this.inviteCtrl.langueageswitch(languageToTranslate);
        this.participantCtrl.langueageswitch(languageToTranslate);
        this.editParticipantCtrl.langueageswitch(languageToTranslate);
        this.editExpenseCtrl.langueageswitch(languageToTranslate);
        this.addExpenseCtrl.langueageswitch(languageToTranslate);

    }

    public void showStart() {
        primaryStage.setTitle("StartScreen");
        primaryStage.setScene(startScene);
    }

    public void showEventOverview() {
        primaryStage.setTitle("EventOverview");
        primaryStage.setScene(eventOverviewScene);
    }

    public void showInvite() {
        primaryStage.setTitle("Invite");
        primaryStage.setScene(inviteScene);
    }

    public void showExpense() {
        primaryStage.setTitle("Add Expense");
        primaryStage.setScene(expenseScene);
    }

    public void showParticipant() {
        primaryStage.setTitle("Add Participant");
        primaryStage.setScene(participantScene);
    }

    public void showEditParticipant() {
        primaryStage.setTitle("Edit Participant");
        primaryStage.setScene(editParticipantScene);
    }

    public void showEditExpense() {
        primaryStage.setTitle("Edit Expense");
        primaryStage.setScene(editExpenseScene);
    }

    public void showAdminLogin () {
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(aloginScene);
    }

    public void showAdminDashboard() {
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adashScene);
    }

}