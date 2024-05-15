package backend.studyhub.services;

import backend.studyhub.data.DataAccess;
import backend.studyhub.data.DataStore;
import backend.studyhub.entities.Workspace;
import backend.studyhub.entities.items.Folder;
import backend.studyhub.entities.items.Item;
import backend.studyhub.entities.items.Link;
import backend.studyhub.entities.items.Note;
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

    private static long newItem(long callerId, long workspaceId, long folderId, Item item) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add items to this workspace");
        }
        Item targetFolder = workspace.findItem(folderId);
        if (targetFolder instanceof Folder) {
            ((Folder) targetFolder).addItem(item);
        } else {
            throw new HttpBadRequestException("Folder does not exist");
        }
        dataStore.updateWorkspace(workspace);
        return item.getId();
    }

    public static long newFolder(long callerId, long workspaceId, long folderId, String name) throws HttpBadRequestException, ForbiddenException {
        return newItem(callerId, workspaceId, folderId, new Folder(name));
    }

    public static long newLink(long callerId, long workspaceId, long folderId, String name, String url) throws HttpBadRequestException, ForbiddenException {
        return newItem(callerId, workspaceId, folderId, new Link(name, url));
    }

    public static long newNote(long callerId, long workspaceId, long folderId, String name, String content) throws HttpBadRequestException, ForbiddenException {
        return newItem(callerId, workspaceId, folderId, new Note(name, content));
    }

    public static void updateFolder(long callerId, long workspaceId, long folderId, String name) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add items to this workspace");
        }
        Item targetFolder = workspace.findItem(folderId);
        if (targetFolder instanceof Folder) {
            ((Folder) targetFolder).setName(name);
        } else {
            throw new HttpBadRequestException("Folder does not exist");
        }
        dataStore.updateWorkspace(workspace);
    }

    public static void updateLink(long callerId, long workspaceId, long linkId, String name, String url) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add items to this workspace");
        }
        Item targetLink = workspace.findItem(linkId);
        if (targetLink instanceof Link) {
            ((Link) targetLink).setName(name);
            ((Link) targetLink).setUrl(url);
        } else {
            throw new HttpBadRequestException("Link does not exist");
        }
        dataStore.updateWorkspace(workspace);
    }

    public static void updateNote(long callerId, long workspaceId, long noteId, String name, String content) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add items to this workspace");
        }
        Item targetNote = workspace.findItem(noteId);
        if (targetNote instanceof Note) {
            ((Note) targetNote).setName(name);
            ((Note) targetNote).setContent(content);
        } else {
            throw new HttpBadRequestException("Note does not exist");
        }
        dataStore.updateWorkspace(workspace);
    }

    public static void deleteItem(long callerId, long workspaceId, long itemId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to delete items from this workspace");
        }
        Item item = workspace.findItem(itemId);
        if (item == null) {
            throw new HttpBadRequestException("Item does not exist");
        }
        Folder parentFolder = workspace.findFolder(itemId);
        parentFolder.removeItem(item);
        dataStore.updateWorkspace(workspace);
    }

    public static void moveItem(long callerId, long workspaceId, long itemId, long folderId) throws HttpBadRequestException, ForbiddenException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        } else if (!workspace.getAdmins().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to add items to this workspace");
        }
        Item item = workspace.findItem(itemId);
        if (item == null) {
            throw new HttpBadRequestException("Item does not exist");
        }
        Folder currentFolder = workspace.findFolder(itemId);
        currentFolder.removeItem(item);
        Item newFolder = workspace.findItem(folderId);
        if (newFolder instanceof Folder) {
            ((Folder) newFolder).addItem(item);
        } else {
            throw new HttpBadRequestException("Folder does not exist");
        }
        dataStore.updateWorkspace(workspace);
    }

    public static Workspace getWorkspace(long callerId, long workspaceId) throws ForbiddenException, HttpBadRequestException {
        DataStore dataStore = DataAccess.getInstance().getDataStore();
        Workspace workspace = dataStore.getWorkspace(workspaceId);
        if (workspace == null) {
            throw new HttpBadRequestException("Workspace does not exist");
        }
        if (!workspace.getUsers().contains(dataStore.getUser(callerId))) {
            throw new ForbiddenException("You do not have permission to view this workspace");
        }
        return workspace;
    }
}
