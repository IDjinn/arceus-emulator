package org.emulator.wireds.boxes;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.AdvancedFloorItem;
import org.emulator.wireds.roomWiredComponent.WiredExecutionPipeline;
import org.emulator.wireds.roomWiredComponent.WiredManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("PublicMethodNotExposedInInterface")
public abstract class WiredItem extends AdvancedFloorItem {
    private final WiredItemSettings settings;
    private final Map<Integer, IRoomItem> selectedItems;
    protected boolean isFlashing;
    protected WiredExecutionPipeline executionPipeline;

    public WiredItem(final IRoomItemData itemData, final IRoom room, final IFurniture furniture) {
        super(itemData, room, furniture);
        this.settings = new WiredItemSettings();
        this.selectedItems = new HashMap<>();
        this.executionPipeline = Objects.requireNonNull(room.getCustomComponent(WiredManager.class)).getExecutionPipeline();
    }

    @Override
    public void onRoomLoaded() {
        final var iterator = this.settings.getSelectedItems().iterator();
        while (iterator.hasNext()) {
            final var selectedItemId = iterator.next();
            final var item = this.getRoom().getObjectManager().getItem(selectedItemId);
            if (item == null)
                iterator.remove();
            else
                this.selectItem(item);
        }
    }

    public Map<Integer, Integer> getParameters() {
        return this.settings.getParameters();
    }

    public Map<Integer, IRoomItem> getSelectedItems() {
        return this.selectedItems;
    }

    public void selectItem(@NotNull IRoomItem roomItem) {
        this.settings.getSelectedItems().add(roomItem.getId());
        this.selectedItems.put(roomItem.getId(), roomItem);
    }

    public void deselectItem(@NotNull IRoomItem roomItem) {
        this.settings.getSelectedItems().remove(roomItem.getId());
        this.selectedItems.remove(roomItem.getId());
    }

    public @Nullable String getData() {
        return this.settings.getData();
    }

    public void setData(final @Nullable String data) {
        this.settings.setData(data);
    }

    public WiredSelectionType getSelectionType() {
        return this.settings.getSelectionType();
    }

    public void setSelectionType(final WiredSelectionType selectionType) {
        this.settings.setSelectionType(selectionType);
    }
}
