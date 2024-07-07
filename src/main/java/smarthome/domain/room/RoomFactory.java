package smarthome.domain.room;

import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;

/**
 * This interface is used to create Room objects
 */
public interface RoomFactory {
    /**
     * Creates a Room object with the specified room name, house name, floor, and dimensions.
     *
     * @param roomName   the name of the room
     * @param houseName  the name of the house
     * @param floor      the floor of the room
     * @param dimensions the dimensions of the room
     * @return a Room object with the specified room name, house name, floor, and dimensions
     */
    Room createRoom(RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions);

    /**
     * Creates a Room object with the specified room id, room name, house name, floor, and dimensions.
     *
     * @param roomId     the id of the room
     * @param roomName   the name of the room
     * @param houseName  the name of the house
     * @param floor      the floor of the room
     * @param dimensions the dimensions of the room
     * @return a Room object with the specified room id, room name, house name, floor, and dimensions
     */
    Room createRoom(RoomId roomId, RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions);
}
