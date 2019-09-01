package com.example.websocketdemo.dao;

import com.example.websocketdemo.model.RoomModel;
import com.example.websocketdemo.repository.RoomRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("RoomService")
public class RoomDao implements IDao<RoomModel> {
    private final RoomRepository roomRepository;

    public RoomDao(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomModel> getAll() {
        return null;
    }

    @Override
    public Optional<RoomModel> get(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public void update(RoomModel record) {
        record.setUpdateAtNow();
        roomRepository.save(record);
    }

    @Override
    public void delete(RoomModel record) {

    }

    public List<RoomModel> getAllMemberRooms(ObjectId id) {
        return roomRepository.getAllMemberRooms(id);
    }

    public List<RoomModel> getFromUserName(String username) {
        return null;
    }

    public RoomModel findByIdAndUserId(ObjectId id, ObjectId userId) {
        return roomRepository.findByIdAndUserId(id, userId);
    }

    public RoomModel findRoomWithTwoMembers(ObjectId one, ObjectId two) {
        return roomRepository.findRoomWithMembers(one, two);
    }

    public RoomModel findOrCreateGroup(ObjectId userId) {
        List<ObjectId> members = new ArrayList<>();
        members.add(userId);
        RoomModel roomModel = findExistGroup(userId);
        if (roomModel != null) return roomModel;
        return createGroup(members, userId);
    }

    public RoomModel findExistGroup(ObjectId userId) {
        return roomRepository.findExistGroup(userId);
    }

    public RoomModel createInbox(List<ObjectId> members, ObjectId creator) {
        RoomModel room = new RoomModel();
        room.setMembers(members);
        room.setType("inbox");
        room.setCreator(creator);
        return roomRepository.insert(room);
    }

    public RoomModel createGroup(List<ObjectId> members, ObjectId creator) {
        RoomModel room = new RoomModel();
        room.setMembers(members);
        room.setType("group");
        room.setCreator(creator);
        return roomRepository.insert(room);
    }

    public RoomModel insertMemberToRoom(ObjectId roomId, ObjectId userId, ObjectId memberId) {
        RoomModel roomModel = roomRepository.findGroupWithMember(roomId, userId);
        if (roomModel == null) return null;
        List<ObjectId> members = roomModel.getMembers();
        boolean f = false;
        for (ObjectId i : members) {
            if (i.equals(memberId)) {
                f = true;
                break;
            }
        }
        if (!f) {
            members.add(memberId);
            roomRepository.save(roomModel);
        }
        return roomModel;
    }

    public RoomModel removeMemberFromRoom(ObjectId roomId, ObjectId userId, ObjectId memberId) {
        RoomModel roomModel = roomRepository.findGroupWithMember(roomId, userId);
        if (roomModel == null) return null;
        List<ObjectId> members = roomModel.getMembers();
        boolean f = false;
        int index = -1;
        for (ObjectId i : members) {
            if (i.equals(memberId)) {
                f = true;
                index = members.indexOf(i);
                break;
            }
        }
        if (f) {
            members.remove(index);
            roomRepository.save(roomModel);
        }
        return roomModel;
    }
}
