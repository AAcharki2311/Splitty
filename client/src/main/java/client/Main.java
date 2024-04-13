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
package client;


import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = Guice.createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main method
     * @param args used for main method
     * @throws URISyntaxException if something is wrong with the application
     * @throws IOException if something is wrong with the application
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Loads all the different scenes
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if something is wrong with the application
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        var start = FXML.load(StartScreenCtrl.class, "client", "scenes", "StartScreen.fxml");
        var eventOver = FXML.load(EventOverviewCtrl.class, "client", "scenes", "EventOverview.fxml");
        var expense = FXML.load(AddExpenseCtrl.class, "client", "scenes", "AddExpenseScreen.fxml");
        var participant = FXML.load(ParticipantCtrl.class, "client", "scenes", "AddParticipantScreen.fxml");
        var editParticipant = FXML.load(EditParticipantCtrl.class, "client", "scenes", "EditParticipantScreen.fxml");
        var editExpense = FXML.load(EditExpenseCtrl.class, "client", "scenes", "EditExpenseScreen.fxml");
        var alogin = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");
        var adash = FXML.load(AdminDashboardCtrl.class, "client", "scenes", "AdminDashboard.fxml");
        var aevent = FXML.load(EventOverviewAdminCtrl.class, "client", "scenes", "EventOverviewAdmin.fxml");
        var settle = FXML.load(SettleDebtsCtrl.class, "client", "scenes", "SettleDebts.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, start, eventOver, expense, participant, editParticipant, editExpense, alogin, adash, aevent, settle);

        primaryStage.setOnCloseRequest(event -> {
            adash.getKey().stop();
        });

    }
}
