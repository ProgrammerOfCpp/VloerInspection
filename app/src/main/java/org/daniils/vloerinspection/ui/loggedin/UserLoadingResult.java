package org.daniils.vloerinspection.ui.loggedin;

import org.daniils.vloerinspection.data.model.User;
import org.daniils.vloerinspection.data.view.UserView;

public class UserLoadingResult {
    private UserView userView;
    private User user;
    private Integer errorStringId = null;

    UserLoadingResult(User user) {
        this.userView = new UserView(user);
        this.user = user;
    }

    UserLoadingResult(int errorStringId) {
        this.errorStringId = errorStringId;
    }

    public UserView getUserView() {
        return userView;
    }

    public User getUser() {
        return user;
    }

    public Integer getErrorStringId() {
        return errorStringId;
    }
}
