package waterdistribution;

public class Main {
    public static void main(String[] args) {
        WaterNetwork network = new WaterNetwork();

        network.addResource(new WaterResource("TANK-1", 1000));
        network.addHouse(new House("H1", 4, 50));
        network.addHouse(new House("H2", 3, 50));
        network.addHouse(new House("H3", 5, 50));
        network.addHouse(new House("H4", 2, 50));
        network.addHouse(new House("H5", 6, 50));

        network.addPipe(new Pipe("TANK-1", "H1", 500));
        network.addPipe(new Pipe("H1", "H2", 300));
        network.addPipe(new Pipe("H2", "H3", 300));
        network.addPipe(new Pipe("H3", "H4", 300));
        network.addPipe(new Pipe("H1", "H5", 300));
        network.addPipe(new Pipe("H5", "H4", 300));

        System.out.println("INITIAL DISTRIBUTION");
        new WaterDistributionService(network).distributeWater();

        System.out.println();
        System.out.println("PIPE FAILURE: H2 -> H3");
        network.breakPipe("H2", "H3");
        new WaterDistributionService(network).distributeWater();
    }
}
