package com.company.sample.web.detail;

import com.company.sample.entity.Detail;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.Datasource;
import org.apache.commons.lang.BooleanUtils;

import javax.inject.Inject;

public class DetailEdit extends AbstractEditor<Detail> {
    @Inject
    private Datasource<Detail> detailDs;

    // Inject passed window param for convenient usage
    @WindowParam(name = "commit")
    private Boolean commit;

    @Override
    public void commitAndClose() {
        // if the 'commit' param is set to false explicitly, use custom logic
        if (BooleanUtils.isFalse(commit)) {
            // call standard validation mechanism
            if (!validateAll()) {
                return;
            }
            // to prevent message about unsaved changes
            detailDs.setAllowCommit(false);
            // close screen with default com.haulmont.cuba.gui.components.Window.COMMIT_ACTION_ID
            // to trigger com.haulmont.cuba.gui.components.Window.CloseWithCommitListener
            close(COMMIT_ACTION_ID);
        } else {
            // use default logic otherwise
            super.commitAndClose();
        }
    }
}