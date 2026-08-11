package railway;

import model.Station;
import model.Train;
import structure.ActionStack;
import structure.RailwayGraph;
import structure.StationArray;
import structure.TrainAVLTree;
import structure.TrainBST;
import structure.TrainLinkedList;
import structure.TrainIdSet;
import structure.UserHashTable;
import structure.WaitingListQueue;

public class RailwaySystem {
    private final UserHashTable users = new UserHashTable(10);
    private final TrainLinkedList trains = new TrainLinkedList();
    private final TrainIdSet trainIds = new TrainIdSet(10);
    private final TrainBST trainBST = new TrainBST();
    private final TrainAVLTree trainAVLTree = new TrainAVLTree();
    private final ActionStack actions = new ActionStack();
    private final WaitingListQueue waitingList = new WaitingListQueue();
    private final RailwayGraph graph = new RailwayGraph();
    private final StationArray stations = new StationArray(10);

    public void initialize() {
        seedTrains();
        seedStations();
        seedGraph();
    }

    private void seedTrains() {
        addSeedTrain(new Train("T101", "Podi Menike", "Colombo Fort", "Badulla", 100, 100));
        addSeedTrain(new Train("T102", "Udarata Menike", "Colombo Fort", "Kandy", 80, 80));
        addSeedTrain(new Train("T103", "Ruhunu Kumari", "Colombo Fort", "Matara", 120, 120));
        addSeedTrain(new Train("T104", "Yal Devi", "Colombo Fort", "Jaffna", 1, 1));
        addSeedTrain(new Train("T105", "Kandy Intercity", "Kandy", "Colombo Fort", 90, 90));
        addSeedTrain(new Train("T106", "Udarata Express", "Kandy", "Badulla", 75, 75));
        addSeedTrain(new Train("T107", "Ruhunu Express", "Matara", "Colombo Fort", 110, 110));
        addSeedTrain(new Train("T108", "Northern Express", "Jaffna", "Colombo Fort", 95, 95));
    }

    private void addSeedTrain(Train train) {
        if (trainIds.add(train.getTrainId())) {
            trains.addTrain(train);
            trainBST.insert(train);
            trainAVLTree.insert(train);
        }
    }

    private void seedStations() {
        stations.addStation("Colombo Fort");
        stations.addStation("Kandy");
        stations.addStation("Badulla");
        stations.addStation("Matara");
        stations.addStation("Jaffna");
    }

    private void seedGraph() {
        graph.addStation(new Station("CMB", "Colombo Fort"));
        graph.addStation(new Station("KDY", "Kandy"));
        graph.addStation(new Station("BDL", "Badulla"));
        graph.addStation(new Station("MTR", "Matara"));
        graph.addStation(new Station("JFN", "Jaffna"));
        //graph.addStation(new Station("GLE", "Galle"));
        graph.addRoute("CMB", "KDY");
        graph.addRoute("KDY", "BDL");
        graph.addRoute("CMB", "MTR");
        graph.addRoute("CMB", "JFN");

    }

    public UserHashTable getUsers() {
        return users;
    }

    public TrainLinkedList getTrains() {
        return trains;
    }

    public TrainIdSet getTrainIds() {
        return trainIds;
    }

    public TrainBST getTrainBST() {
        return trainBST;
    }

    public TrainAVLTree getTrainAVLTree() {
        return trainAVLTree;
    }

    public ActionStack getActions() {
        return actions;
    }

    public WaitingListQueue getWaitingList() {
        return waitingList;
    }

    public RailwayGraph getGraph() {
        return graph;
    }

    public StationArray getStations() {
        return stations;
    }
}
