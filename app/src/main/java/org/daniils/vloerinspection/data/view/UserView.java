package org.daniils.vloerinspection.data.view;

import org.daniils.vloerinspection.data.model.User;

public class UserView {
    private String displayName;

    public UserView(User user) {
        this.displayName = user.getFirstName() + " " + user.getLastname();
    }

    public String getDisplayName() { return this.displayName; }
}
