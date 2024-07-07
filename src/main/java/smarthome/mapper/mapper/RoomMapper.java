package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.room.Room;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;
import smarthome.mapper.RoomDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for RoomDTO
 */
@Component
public class RoomMapper {

    /**
     * Converts the room ID data in RoomDTO to a RoomId value object
     *
     * @param roomDTO RoomDTO
     * @return the RoomId value object
     * @throws IllegalArgumentException if the data in RoomDTO is invalid
     */
    public RoomId toRoomId(RoomDTO roomDTO) throws IllegalArgumentException {
        return new RoomId(roomDTO.getRoomId());
    }

    /**
     * Converts the house name data in RoomDTO to a HouseName value object
     *
     * @param roomDTO RoomDTO
     * @return the HouseName value object
     * @throws IllegalArgumentException if the data in RoomDTO is invalid
     */
    public HouseName toHouseName(RoomDTO roomDTO) throws IllegalArgumentException {
        return new HouseName(roomDTO.getHouseName());
    }

    /**
     * Converts the room name data in RoomDTO to a RoomName value object
     *
     * @param roomDTO RoomDTO
     * @return the RoomName value object
     * @throws IllegalArgumentException if the data in RoomDTO is invalid
     */
    public RoomName toRoomName(RoomDTO roomDTO) throws IllegalArgumentException {
        return new RoomName(roomDTO.getRoomName());
    }

    /**
     * Converts the floor data in RoomDTO to a Floor value object
     *
     * @param roomDTO RoomDTO
     * @return the Floor value object
     * @throws IllegalArgumentException if the data in RoomDTO is invalid
     */
    public Floor toFloor(RoomDTO roomDTO) throws IllegalArgumentException {
        return new Floor(roomDTO.getFloor());
    }

    /**
     * Converts the height, width and length data in RoomDTO to a Dimensions value object
     *
     * @param roomDTO RoomDTO
     * @return the Dimensions value object
     * @throws IllegalArgumentException if the data in RoomDTO is invalid
     */
    public Dimensions toDimensions(RoomDTO roomDTO) throws IllegalArgumentException {
        Width width = new Width(roomDTO.getWidth());
        Height height = new Height(roomDTO.getHeight());
        Length length = new Length(roomDTO.getLength());
        return new Dimensions(width, height, length);
    }

    /**
     * Converts a Room entity to a RoomDTO
     *
     * @param room Room
     * @return the RoomDTO
     */
    public RoomDTO toRoomDTO(Room room) {
        String roomId = room.getIdentity().getRoomId();
        String houseName = room.getHouseName().getName();
        String roomName = room.getRoomName().getRoomName();
        int floor = room.getFloor().getFloor();
        double height = room.getDimensions().getHeight().getHeight();
        double width = room.getDimensions().getWidth().getWidth();
        double length = room.getDimensions().getLength().getLength();
        return new RoomDTO(roomId, houseName, roomName, floor, height, width, length);
    }

    /**
     * Converts an Iterable of Room entities to a list of RoomDTOs
     *
     * @param rooms Iterable of Room entities
     * @return the list of RoomDTOs
     */
    public List<RoomDTO> toRoomsDTO(Iterable<Room> rooms) {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOList.add(toRoomDTO(room));
        }

        return roomDTOList;
    }

    /**
     * Converts an Iterable of RoomId value objects to a list of RoomDTOs
     *
     * @param roomIds Iterable of RoomId value objects
     * @return the list of RoomDTOs
     */
    public List<RoomDTO> toRoomIdsDTO(Iterable<RoomId> roomIds) {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (RoomId roomId : roomIds) {
            roomDTOList.add(new RoomDTO(roomId.getRoomId()));
        }

        return roomDTOList;
    }
}
