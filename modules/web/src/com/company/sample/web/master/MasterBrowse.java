package com.company.sample.web.master;

import com.company.sample.entity.Detail;
import com.company.sample.entity.Master;
import com.company.sample.web.detail.DetailEdit;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UuidSource;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MasterBrowse extends AbstractLookup {
    @Inject
    private Table<Master> mastersTable;
    @Inject
    private Metadata metadata;
    @Inject
    private DataManager dataManager;
    @Inject
    private UuidSource uuidSource;

    @Override
    public void init(Map<String, Object> params) {
        // Add custom action to the table
        Action addDetailsAction = new AddDetailsAction(mastersTable);
        mastersTable.addAction(addDetailsAction);
    }

    /**
     * Custom action which provides logic for adding the 'same' Detail entities to selected Master entities.
     */
    public class AddDetailsAction extends ItemTrackingAction {
        public static final String NAME = "addDetails";

        public AddDetailsAction(ListComponent target) {
            this(target, NAME);
        }

        public AddDetailsAction(ListComponent target, String id) {
            super(target, id);
            this.caption = "Add Details";
        }

        @Override
        public void actionPerform(Component component) {
            // Create a detail entity for passing to the editing screen
            Detail detail = metadata.create(Detail.class);

            // Open DetailEdit with special parameter which will be handled by DetailEdit screen
            DetailEdit editor = (DetailEdit) openEditor(detail, OpenType.DIALOG, ParamsMap.of("commit", false));
            // Add a listener that will be notified when this screen
            // is closed with com.haulmont.cuba.gui.components.Window.COMMIT_ACTION_ID.
            editor.addCloseWithCommitListener(() -> {
                // The edited detail entity
                Detail createdDetail = editor.getItem();
                // Iterate through all selected master entities
                //noinspection unchecked
                List<Detail> commitInstances = (List<Detail>) target.getSelected().stream()
                        .map(o -> {
                            Master master = (Master) o;
                            // Make a copy of Detail entity received from editor screen
                            // to associate with master entity
                            // Note: coping logic may vary depends on your needs and entity complexity
                            // In this sample the simple MetadataTools.copy is used.
                            Detail detailToCommit = metadata.getTools().copy(createdDetail);
                            // as id also copied, we need to set a new one
                            detailToCommit.setId(uuidSource.createUuid());
                            detailToCommit.setMaster(master);
                            return detailToCommit;
                        }).collect(Collectors.toList());
                CommitContext commitContext = new CommitContext(commitInstances);
                dataManager.commit(commitContext);
                // Optional. Use only if your interface depends on added details.
                target.getDatasource().refresh();
            });
        }
    }
}