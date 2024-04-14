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

import client.scenes.*;
import client.utils.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    /**
     * Configures the binding for each controller
     * @param binder the binder
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(StartScreenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EventOverviewCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddExpenseCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditExpenseCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ParticipantCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EditParticipantCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminLoginCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AdminDashboardCtrl.class).in(Scopes.SINGLETON);
        binder.bind(EventOverviewAdminCtrl.class).in(Scopes.SINGLETON);
        binder.bind(SettleDebtsCtrl.class).in(Scopes.SINGLETON);
        binder.bind(LanguageSwitchInterface.class).to(LanguageSwitch.class);
        binder.bind(WriteEventNamesInterface.class).to(WriteEventNames.class);
        binder.bind(ReadURLInterface.class).to(ReadURL.class);
        binder.bind(ReadJSONInterface.class).to(ReadJSON.class);
    }
}