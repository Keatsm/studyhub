package backend.studyhub.services;

import backend.studyhub.data.DataAccess;
import backend.studyhub.data.DataStore;
import backend.studyhub.entities.Workspace;
import backend.studyhub.exceptions.ForbiddenException;
import backend.studyhub.exceptions.HttpBadRequestException;

public class WorkspaceService {
    public static long createWorkspace(long callerId, String name, String description) {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        return dataStore.createWorkspace(name, description, dataStore.getUser(callerId)).getId();
    }

    public static void deleteWorkspace(long callerId, long workspaceId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to delete this workspace");
        }
        dataStore.deleteWorkspace(workspaceId);
    }

    public static void updateWorkspace(long callerId, long workspaceId, String name, String description) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to update this workspace");
        }
        workspace.setName(name);
        workspace.setDescription(description);
        dataStore.updateWorkspace(workspace);
    }

    public static void addUser(long callerId, long workspaceId, long userId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add users to this workspace");
        } else if (workspace.getUsers().contains(dataStore.getUser(userId))) {
            throw new HttpBadRequestException("User is already in this workspace");
        }
        workspace.addUser(dataStore.getUser(userId));
        dataStore.updateWorkspace(workspace);
    }

    public static void removeUser(long callerId, long workspaceId, long userId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getUsers().contains(dataStore.getUser(userId))) {
            throw new HttpBadRequestException("User is not in this workspace");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to remove users from this workspace");
        }
        workspace.removeUser(dataStore.getUser(userId));
        dataStore.updateWorkspace(workspace);
    }

    public static void addAdmin(long callerId, long workspaceId, long userId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add admins to this workspace");
        } else if (workspace.getAdmins().contains(dataStore.getUser(userId))) {
            throw new HttpBadRequestException("User is already an admin of this workspace");
        } else if (!workspace.getUsers().contains(dataStore.getUser(userId))) {
            throw new HttpBadRequestException("User is not in this workspace");
        }
        workspace.addAdmin(dataStore.getUser(userId));
        dataStore.updateWorkspace(workspace);
    }

    public static void removeAdmin(long callerId, long workspaceId, long userId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(userId))) {
            throw new HttpBadRequestException("User is not an admin of this workspace");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to remove admins from this workspace");
        }
        workspace.removeAdmin(dataStore.getUser(userId));
        dataStore.updateWorkspace(workspace);
    }

}
