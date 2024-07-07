package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;
import smarthome.persistence.datamodel.RoomDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between RoomDataModel and Room domain model.
 */
@Component
public class RoomDataModelMapper {

    private final RoomFactory roomFactory;

    /**
     * RoomDataModelMapper constructor.
     *
     * @param roomFactory The RoomFactory to be used in the mapping.
     */
    public RoomDataModelMapper(RoomFactory roomFactory) {
        this.roomFactory = roomFactory;
    }

    /**
     * This method converts a RoomDataModel to a Room domain model.
     *
     * @param roomDataModel The RoomDataModel to be converted.
     * @return The converted Room domain model.
     */
    public Room toRoomDomain(RoomDataModel roomDataModel) {
        RoomId roomId = new RoomId(roomDataModel.getRoomId());
        RoomName roomName = new RoomName(roomDataModel.getRoomName());
        HouseName houseName = new HouseName(roomDataModel.getHouseName());
        Floor floor = new Floor(roomDataModel.getFloor());
        Dimensions dimensions = new Dimensions(new Width(roomDataModel.getWidth()),
                new Height(roomDataModel.getHeight()), new Length(roomDataModel.getLength()));
        return roomFactory.createRoom(roomId, roomName, houseName, floor, dimensions);
    }

    /**
     * This method converts a list of RoomDataModel to a list of Room domain model.
     *
     * @param roomsDataModel The list of RoomDataModel to be converted.
     * @return The converted list of Room domain model.
     */
    public Iterable<Room> toRoomsDomain(Iterable<RoomDataModel> roomsDataModel) {
        List<Room> rooms = new ArrayList<>();

        for (smarthome.persistence.datamodel.RoomDataModel roomDataModel : roomsDataModel) {
            rooms.add(toRoomDomain(roomDataModel));
        }
        return rooms;
    }

}
