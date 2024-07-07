package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.room.Room;

/**
 * This class represents the Room entity.
 * It maps the Room domain model to a format that can be stored in the database.
 */
@Entity
@Table(name = "ROOM")
public class RoomDataModel {

    @Id
    private String roomId;
    private String houseName;
    private String roomName;
    private Integer floor;
    private Double height;
    private Double width;
    private Double length;

    /**
     * Default constructor for the RoomDataModel class.
     * This is required by JPA.
     */
    public RoomDataModel() {
    }

    /**
     * Constructor for the RoomDataModel class.
     *
     * @param room The Room domain model.
     */
    public RoomDataModel(Room room) {
        if (room == null){
            throw new IllegalArgumentException();
        }
        this.roomId = room.getIdentity().getRoomId();
        this.houseName = room.getHouseName().getName();
        this.roomName = room.getRoomName().getRoomName();
        this.floor = room.getFloor().getFloor();
        this.height = room.getDimensions().getHeight().getHeight();
        this.width = room.getDimensions().getWidth().getWidth();
        this.length = room.getDimensions().getLength().getLength();
    }

    /**
     * Getter for the roomId attribute.
     *
     * @return The roomId attribute.
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Getter for the houseName attribute.
     *
     * @return The houseName attribute.
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * Getter for the roomName attribute.
     *
     * @return The roomName attribute.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Getter for the floor attribute.
     *
     * @return The floor attribute.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Getter for the height attribute.
     *
     * @return The height attribute.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter for the width attribute.
     *
     * @return The width attribute.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for the length attribute.
     *
     * @return The length attribute.
     */
    public double getLength() {
        return length;
    }
}
