package service;

import model.Train;
import structure.TrainAVLTree;
import structure.TrainBST;
import structure.TrainIdSet;
import structure.TrainLinkedList;

public class TrainService {
    private final TrainLinkedList trains;
    private final TrainIdSet trainIds;
    private final TrainBST trainBST;
    private final TrainAVLTree trainAVLTree;

    public TrainService(TrainLinkedList trains) {
        this(trains, null, null, null);
    }

    public TrainService(TrainLinkedList trains, TrainIdSet trainIds) {
        this(trains, trainIds, null, null);
    }

    public TrainService(TrainLinkedList trains, TrainIdSet trainIds,
                        TrainBST trainBST, TrainAVLTree trainAVLTree) {
        this.trains = trains;
        this.trainIds = trainIds;
        this.trainBST = trainBST;
        this.trainAVLTree = trainAVLTree;
    }

    public String add(String id, String name, String start, String destination, int seats) {
        id = clean(id);
        name = clean(name);
        start = clean(start);
        destination = clean(destination);

        if (id.isEmpty() || name.isEmpty() || start.isEmpty() || destination.isEmpty()) {
            return "All train fields are required.";
        }
        if ((trainIds != null && trainIds.contains(id))
                || trains.searchTrainById(id) != null) {
            return "A train with that ID already exists.";
        }
        if (start.equalsIgnoreCase(destination)) {
            return "Start station and destination cannot be the same.";
        }
        if (seats <= 0) {
            return "Total seats must be greater than zero.";
        }

        if (trainIds != null && !trainIds.add(id)) {
            return "A train with that ID already exists.";
        }
        Train train = new Train(id, name, start, destination, seats, seats);
        trains.addTrain(train);
        if (trainBST != null) {
            trainBST.insert(train);
        }
        if (trainAVLTree != null) {
            trainAVLTree.insert(train);
        }
        return null;
    }

    public Train find(String id) {
        return trains.searchTrainById(clean(id));
    }

    public boolean delete(String id) {
        String cleanedId = clean(id);
        if (!trains.deleteTrainById(cleanedId)) {
            return false;
        }
        if (trainIds != null) {
            trainIds.remove(cleanedId);
        }
        if (trainBST != null) {
            trainBST.delete(cleanedId);
        }
        if (trainAVLTree != null) {
            trainAVLTree.delete(cleanedId);
        }
        return true;
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
