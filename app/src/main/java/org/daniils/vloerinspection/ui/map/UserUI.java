package org.daniils.vloerinspection.ui.map;

import org.daniils.vloerinspection.data.model.User;
import org.daniils.vloerinspection.data.view.UserView;

public class UserUI {
    private UserView userView;

    UserUI(User user) {
        this.userView = new UserView(user);
    }

    public UserView getUserView() {
        return userView;
    }
}
