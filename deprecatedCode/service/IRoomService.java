package smarthome.service;

import smarthome.mapper.RoomDTO;

import java.util.List;

/**
 * The RoomService interface provides methods for adding a room and getting the list of rooms in a house.
 */
public interface IRoomService {

    /**
     * Adds a room.
     *
     * @param roomDTO the room data to add
     * @return the added room data
     */
    RoomDTO addRoom(RoomDTO roomDTO);

   /**
    * * Gets the list of rooms in the house.
    *
    * @return the list of rooms in the house
    */
   List<RoomDTO> getRooms();
}
