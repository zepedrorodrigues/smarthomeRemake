package smarthome.domain.room;

import org.springframework.stereotype.Component;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;

/**
 * This class is an implementation of the RoomFactory interface and is used to create Room objects
 */
@Component
public class RoomFactoryImpl implements RoomFactory {
    /**
     * Creates a Room object with the specified room name, house name, floor, and dimensions.
     *
     * @param roomName   the name of the room
     * @param houseName  the name of the house
     * @param floor      the floor of the room
     * @param dimensions the dimensions of the room
     * @return a Room object with the specified room name, house name, floor, and dimensions
     */
    @Override
    public Room createRoom(RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions) {
        return new Room(roomName, houseName, floor, dimensions);
    }

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
    @Override
    public Room createRoom(RoomId roomId, RoomName roomName, HouseName houseName, Floor floor, Dimensions dimensions) {
        return new Room(roomId, roomName, houseName, floor, dimensions);
    }
}
