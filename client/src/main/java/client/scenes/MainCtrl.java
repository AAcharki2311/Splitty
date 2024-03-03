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
    private QuoteOverviewCtrl overviewCtrl;

    // private StartScreen overviewCtrl;
    private Scene overview;
    private AddQuoteCtrl addCtrl;
    private Scene add;
    private Scene startScene;
    private Scene eventOverviewScene;
    private Scene inviteScene;



    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
            Pair<AddQuoteCtrl, Parent> add, Pair <StartScreenCtrl, Parent> start,
                           Pair <EventOverviewCtrl, Parent> eventO, Pair <InviteCtrl, Parent> invite) {
        this.primaryStage = primaryStage;
        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());
        this.startScene = new Scene(start.getValue());
        this.eventOverviewScene = new Scene(eventO.getValue());
        this.inviteScene = new Scene(invite.getValue());
        showStart();
        primaryStage.show();
    }

    public void showOverview() {
        primaryStage.setTitle("Splitty23");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Splitty23");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
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
}