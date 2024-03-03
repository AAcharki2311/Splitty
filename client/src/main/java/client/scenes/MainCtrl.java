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

public class MainCtrl {

    private Stage primaryStage;
    private Scene startScene;
    private Scene eventOverviewScene;
    private Scene inviteScene;
    private Scene expenseScene;
    private Scene participantScene;
    private Scene editParticipantScene;
    private Scene aloginScene;
    private Scene adashScene;

    public void initialize(Stage primaryStage,
                           Pair <StartScreenCtrl, Parent> start,
                           Pair <EventOverviewCtrl, Parent> eventO, Pair <InviteCtrl, Parent> invite,
                           Pair <ExpenseCtrl, Parent> expense, Pair <ParticipantCtrl, Parent> participant,
                           Pair <EditParticipantCtrl, Parent> editParticipant,
                           Pair <AdminLoginCtrl, Parent> alogin, Pair <AdminDashboardCtrl, Parent> adash){
        this.primaryStage = primaryStage;

        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.inviteScene = new Scene(invite.getValue());
        this.expenseScene = new Scene(expense.getValue());
        this.participantScene = new Scene(participant.getValue());
        this.editParticipantScene = new Scene(editParticipant.getValue());

        this.aloginScene = new Scene(alogin.getValue());
        this.adashScene = new Scene(adash.getValue());

        showStart();
        primaryStage.show();
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
        primaryStage.setTitle("Expense");
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


    public void showAdminLogin () {
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(aloginScene);
    }

    public void showAdminDashboard() {
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(adashScene);
    }
}